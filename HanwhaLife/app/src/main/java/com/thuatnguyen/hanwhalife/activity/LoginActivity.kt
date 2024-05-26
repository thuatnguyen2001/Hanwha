package com.thuatnguyen.hanwhalife.activity

import android.content.Context
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.model.User

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
        getPassword()
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
            val email = edtUserName.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isEmpty()) {
                edtUserName.error = "Email không được để trống"
                edtUserName.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                edtPassword.error = "Mật khẩu không được để trống"
                edtPassword.requestFocus()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Đăng nhập thành công
                        val user = FirebaseAuth.getInstance().currentUser
                        Toast.makeText(this@LoginActivity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                        user?.let {
                            val uid = it.uid
                            savePassword(email,password)
                            // Lấy dữ liệu từ Realtime Database và chuyển sang Activity khác
                            fetchUserDataAndNavigate(uid)
                        }

                    } else {
                        Toast.makeText(this@LoginActivity, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

    fun fetchUserDataAndNavigate(uid: String) {
        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference("users").child(uid)

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Lấy thông tin người dùng từ snapshot
                val name = dataSnapshot.child("name").getValue(String::class.java)
                val email = dataSnapshot.child("email").getValue(String::class.java)
                val userId = dataSnapshot.child("userID").getValue(String::class.java)

                // Chuyển dữ liệu sang Activity khác
                val intent = Intent(this@LoginActivity, HomeActivity::class.java).apply {
                    putExtra("name", name)
                    putExtra("email", email)
                    putExtra("userID", userId)
                }
                startActivity(intent)
                finish()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Xử lý lỗi...
                Log.e("DatabaseError", "Error: ${databaseError.message}")
            }
        })
    }

    // Lưu mật khẩu vào SharedPreferences
    fun savePassword(username: String, password: String) {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.putBoolean("checked", ckbLogin.isChecked)
        editor.apply()
    }

    // Xóa mật khẩu đã lưu từ SharedPreferences
    fun clearSavedPassword() {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("username")
        editor.remove("password")
        editor.remove("checked")
        editor.apply()
    }

    // Đọc mật khẩu từ SharedPreferences
    fun getPassword() {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val user =  sharedPreferences.getString("username", null)
        val pass =  sharedPreferences.getString("password", null)
        edtUserName.setText(user)
        edtPassword.setText(pass)
        val check =sharedPreferences.getBoolean("checked", false)
        ckbLogin.isChecked = check
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