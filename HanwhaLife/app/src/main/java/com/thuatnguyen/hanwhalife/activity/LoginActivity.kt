package com.thuatnguyen.hanwhalife.activity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.model.Account
import com.thuatnguyen.hanwhalife.model.BMBH

class LoginActivity : AppCompatActivity() {

    lateinit var edtUserName: EditText;
    lateinit var edtPassword: EditText;
    lateinit var btnView: ImageButton;
    lateinit var ckbLogin: CheckBox;
    lateinit var btnLogin: Button;
    lateinit var txtQuenMK: TextView;
    var viewPassword =true;
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        databaseReference = FirebaseDatabase.getInstance().reference

        anhXa();
        addEvent();
    }

    private fun addEvent() {
        hienThiMatKhau()
        xuLyDangNhap()
        xuLyQuenMatKhau()
    }

    private fun xuLyQuenMatKhau() {
        txtQuenMK.setOnClickListener {
            val intent = Intent(this@LoginActivity, QuenMatKhauActivity::class.java)
            startActivity(intent)
        }
    }

    private fun xuLyDangNhap() {
        btnLogin.setOnClickListener {
            val username = edtUserName.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                signIn(username, password)
            } else {
                // Hiển thị thông báo lỗi nếu trường username hoặc password rỗng
                Toast.makeText(this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signIn(username: String, password: String) {
        databaseReference.child("accounts").orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (snapshot in dataSnapshot.children) {
                            val account = snapshot.getValue(Account::class.java)
                            if (account != null && account.password == password) {
                                Toast.makeText(this@LoginActivity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                intent.putExtra("ACCOUNT",account)
                                startActivity(intent)
                                return
                            }
                        }
                    }

                    // Hiển thị thông báo lỗi nếu thông tin đăng nhập không chính xác
                    Toast.makeText(this@LoginActivity, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Firebase", "Error: ${databaseError.message}")
                    // Hiển thị thông báo lỗi nếu có lỗi xảy ra khi truy vấn dữ liệu
                    Toast.makeText(this@LoginActivity, "An error occurred", Toast.LENGTH_SHORT).show()
                }
            })
    }


    private fun hienThiMatKhau() {
        btnView.setOnClickListener {
            if(viewPassword){
                edtPassword.inputType = 1
                btnView.setImageResource(R.drawable.invisible)
                viewPassword = false
            }else{
                edtPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                btnView.setImageResource(R.drawable.visible)
                viewPassword = true
            }
        }
    }

    private fun anhXa() {
        edtUserName = findViewById(R.id.edtUserName)
        edtPassword = findViewById(R.id.edtPassword)
        btnView = findViewById(R.id.btnView)
        ckbLogin = findViewById(R.id.ckbLogin)
        btnLogin = findViewById(R.id.btnLogin)
        txtQuenMK = findViewById(R.id.txtQuenMK)
    }
}