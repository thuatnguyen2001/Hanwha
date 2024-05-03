package com.thuatnguyen.hanwhalife.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.model.Account
import com.thuatnguyen.hanwhalife.model.BMBH

class QuenMatKhauActivity : AppCompatActivity() {
    lateinit var edtUsername:EditText
    lateinit var edtSDT:EditText
    lateinit var edtPassword:EditText
    lateinit var edtConfirmPassword:EditText
    lateinit var btnOK:Button
    lateinit var btnClose:Button
    lateinit var databaseReference: DatabaseReference
    lateinit var accountID1:String
    lateinit var accountID2:String
    lateinit var accKey:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quen_mat_khau)

        databaseReference = FirebaseDatabase.getInstance().reference
        anhXa()
        addEvent()
    }

    private fun addEvent() {
        xuLyQuenmatKhau()
        xuLyThoat()
    }

    private fun xuLyThoat() {
        btnClose.setOnClickListener {
            finish()
        }
    }

    private fun xuLyQuenmatKhau() {
        btnOK.setOnClickListener {
            val username = edtUsername.text.toString().trim()
            val sdt = edtSDT.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            val confirmPassword = edtConfirmPassword.text.toString().trim()
            if (username.isNotEmpty() && password.isNotEmpty() && sdt.isNotEmpty() && confirmPassword.isNotEmpty() && password.equals(confirmPassword)) {
                forget(username, password,sdt)
            }
            else if(password.equals(confirmPassword)==false){
                Toast.makeText(this, "Mật khẩu chưa giống nhau", Toast.LENGTH_SHORT).show()
            }
            else {
                // Hiển thị thông báo lỗi nếu trường username hoặc password rỗng
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun forget(username: String, password: String, sdt: String) {
        databaseReference.child("accounts").orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (snapshot in dataSnapshot.children) {
                            val account = snapshot.getValue(Account::class.java)!!
                            accountID1 = account?.accountID.toString()
                            accKey = snapshot.key.toString()
                            return
                        }
                    }
                    accountID1 = "1"
                    Toast.makeText(
                        this@QuenMatKhauActivity,
                        "Tài khoản không tồn tại",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Firebase", "Error: ${databaseError.message}")
                    // Hiển thị thông báo lỗi nếu có lỗi xảy ra khi truy vấn dữ liệu
                    Toast.makeText(
                        this@QuenMatKhauActivity,
                        "An error occurred",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        databaseReference.child("BMBH").orderByChild("sdt").equalTo(sdt)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (snapshot in dataSnapshot.children) {
                            val bmbh = snapshot.getValue(BMBH::class.java)
                            if (bmbh?.accountID.equals(accountID1)) {
                                val myref = databaseReference.child("accounts").child(accKey)
                                    .child("password")
                                myref.setValue(password)
                                Toast.makeText(
                                    this@QuenMatKhauActivity,
                                    "Cập nhật mật khẩu thành công",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else {
                                Toast.makeText(
                                    this@QuenMatKhauActivity,
                                    "Tài khoản hoặc số điện thoại không chính xác",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            accountID2 = bmbh?.accountID.toString()
                            return
                        }
                    }

                    Toast.makeText(
                        this@QuenMatKhauActivity,
                        "Số điện thoại không tồn tại",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Firebase", "Error: ${databaseError.message}")
                    // Hiển thị thông báo lỗi nếu có lỗi xảy ra khi truy vấn dữ liệu
                    Toast.makeText(
                        this@QuenMatKhauActivity,
                        "An error occurred",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

    }

    private fun anhXa() {
        edtUsername= findViewById(R.id.edtUserName)
        edtSDT= findViewById(R.id.edtSDT)
        edtPassword= findViewById(R.id.edtPassword)
        edtConfirmPassword= findViewById(R.id.edtConfirmPassword)
        btnOK= findViewById(R.id.btnOK)
        btnClose= findViewById(R.id.btnClose)
        accountID1="1"
        accountID2="2"
    }
}