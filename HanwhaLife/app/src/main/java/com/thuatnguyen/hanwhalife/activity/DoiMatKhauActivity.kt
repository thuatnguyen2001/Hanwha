package com.thuatnguyen.hanwhalife.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.model.User

class DoiMatKhauActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var edtUsername: EditText
    lateinit var edtMatKhauCu: EditText
    lateinit var edtPassword: EditText
    lateinit var edtConfirmPassword: EditText
    lateinit var btnOK: Button
    lateinit var btnClose: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doi_mat_khau)
        auth = FirebaseAuth.getInstance()

        anhXa()
        loadDuLieu()
        addEvent()

    }

    private fun loadDuLieu() {
        val user = auth.currentUser
        edtUsername.setText(user!!.email)
    }

    private fun addEvent() {
        btnOK.setOnClickListener {
            val currentPassword = edtMatKhauCu.text.toString().trim()
            val newPassword = edtPassword.text.toString().trim()
            val cofirmPassword = edtConfirmPassword.text.toString().trim()

            // Kiểm tra xem các trường nhập mật khẩu có trống hay không
            if (currentPassword.isEmpty()) {
                edtMatKhauCu.error = "Mật khẩu cũ không được để trống"
                edtMatKhauCu.requestFocus()
                return@setOnClickListener
            }

            if (newPassword.isEmpty()) {
                edtPassword.error = "Mật khẩu mới không được để trống"
                edtPassword.requestFocus()
                return@setOnClickListener
            }
            if (cofirmPassword.isEmpty()) {
                edtConfirmPassword.error = "Xác nhận mật khẩu không được để trống"
                edtConfirmPassword.requestFocus()
                return@setOnClickListener
            }

            if (!newPassword.equals(cofirmPassword)) {
                edtConfirmPassword.error = "Mật khẩu chưa giống nhau"
                edtConfirmPassword.requestFocus()
                return@setOnClickListener
            }

            val user = auth.currentUser
            if (user != null) {
                val email = user.email
                if (email != null) {
                    val credential = EmailAuthProvider.getCredential(email, currentPassword)

                    user.reauthenticate(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Xác thực thành công, thay đổi mật khẩu
                                user.updatePassword(newPassword)
                                    .addOnCompleteListener { updateTask ->
                                        if (updateTask.isSuccessful) {
                                            Toast.makeText(
                                                this,
                                                "Thay đổi mật khẩu thành công",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            // Xử lý sau khi đổi mật khẩu thành công, ví dụ: quay lại màn hình trước
                                            finish()
                                        } else {
                                            // Xử lý lỗi khi đổi mật khẩu không thành công
                                            Toast.makeText(
                                                this,
                                                "Thay đổi mật khẩu thất bại",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            } else {
                                // Xử lý lỗi khi xác thực không thành công
                                Toast.makeText(
                                    this,
                                    "Mật khẩu cũ không đúng",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }

        btnClose.setOnClickListener {
            finish()
        }
    }


    private fun anhXa() {
        edtUsername= findViewById(R.id.edtUserName)
        edtMatKhauCu= findViewById(R.id.edtMatKhauCu)
        edtPassword= findViewById(R.id.edtPassword)
        edtConfirmPassword= findViewById(R.id.edtConfirmPassword)
        btnOK= findViewById(R.id.btnOK)
        btnClose= findViewById(R.id.btnClose)
    }
}