package com.thuatnguyen.hanwhalife.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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
import com.thuatnguyen.hanwhalife.model.BMBH

class QuenMatKhauActivity : AppCompatActivity() {
    lateinit var edtEmail:EditText
    lateinit var btnLaylaiMK:Button
    lateinit var btnClose:Button
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quen_mat_khau)


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
        btnLaylaiMK.setOnClickListener {
            val email = edtEmail.text.toString().trim()

            // Kiểm tra xem trường email có trống hay không
            if (email.isEmpty()) {
                edtEmail.error = "Email không được để trống"
                edtEmail.requestFocus()
                return@setOnClickListener
            }

            // Gửi email đặt lại mật khẩu
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Email đặt lại mật khẩu đã được gửi thành công
                        Toast.makeText(this, "Email đặt lại mật khẩu đã được gửi tới: $email", Toast.LENGTH_SHORT).show()
                    } else {
                        // Xử lý lỗi nếu không thể gửi email
                        task.exception?.let { exception ->
                            Toast.makeText(this, "Lỗi: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }

    private fun anhXa() {
        edtEmail= findViewById(R.id.edtEmail)
        btnLaylaiMK= findViewById(R.id.btnLayLaiMK)
        btnClose= findViewById(R.id.btnClose)
        auth = FirebaseAuth.getInstance()
    }
}