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

class DoiMatKhauActivity : AppCompatActivity() {

    lateinit var edtUsername: EditText
    lateinit var edtMatKhauCu: EditText
    lateinit var edtPassword: EditText
    lateinit var edtConfirmPassword: EditText
    lateinit var btnOK: Button
    lateinit var btnClose: Button
    lateinit var databaseReference: DatabaseReference
    lateinit var account: Account
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doi_mat_khau)

        databaseReference = FirebaseDatabase.getInstance().reference
        anhXa()
        loadDuLieu()
        addEvent()

    }

    private fun loadDuLieu() {
        account = intent.getParcelableExtra<Account>("ACCOUNT")!!
        edtUsername.setText(account.username)
    }

    private fun addEvent() {
        btnOK.setOnClickListener {
            if(account.password.equals(edtMatKhauCu.text.toString()))
            {
                if(edtPassword.text.toString().equals(edtConfirmPassword.text.toString()))
                {
                    val query = databaseReference.child("accounts").orderByChild("username").equalTo(account.username)
                    query.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            for (snapshot in dataSnapshot.children) {
                                // Lấy ra đối tượng tương ứng với username "abc"
                                val account = snapshot.getValue(Account::class.java)
                                // Xử lý đối tượng account ở đây
                                val accountKey = snapshot.key
                                // Cập nhật password của đối tượng tài khoản
                                val newPassword = edtPassword.text.toString()
                                val childUpdates = hashMapOf<String, Any>("password" to newPassword)
                                databaseReference.child("accounts").child(accountKey!!).updateChildren(childUpdates)
                                    .addOnSuccessListener {
                                        Toast.makeText(this@DoiMatKhauActivity,"Đổi mật khẩu rồi",Toast.LENGTH_SHORT).show()
                                        HomeActivity.closeThisActivity(this@DoiMatKhauActivity)
                                        finish()
                                        val intent = Intent(this@DoiMatKhauActivity, LoginActivity::class.java)
                                        startActivity(intent)

                                    }
                                    .addOnFailureListener { e ->
                                        // Xử lý khi cập nhật thất bại
                                    }

                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // Xử lý khi truy vấn thất bại
                        }
                    })
                    //Toast.makeText(this,query.toString(),Toast.LENGTH_SHORT).show()

                    // Cập nhật giá trị của nút đó

                }else{
                    Toast.makeText(this,"Mật khẩu không giống nhau",Toast.LENGTH_SHORT).show()
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