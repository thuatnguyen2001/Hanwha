package com.thuatnguyen.hanwhalife.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.thuatnguyen.hanwhalife.R
import java.util.concurrent.TimeUnit

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var edtOTP: EditText
    lateinit var edtSDT: EditText
    lateinit var edtPassword: EditText
    lateinit var edtConfirmPassword: EditText
    lateinit var btnOK: Button
    lateinit var btnClose: Button
    lateinit var btnOTP: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)

        auth = FirebaseAuth.getInstance()

        edtOTP= findViewById(R.id.edtOTP)
        edtSDT= findViewById(R.id.edtSDT)
        edtPassword= findViewById(R.id.edtPassword)
        edtConfirmPassword= findViewById(R.id.edtConfirmPassword)
        btnOK= findViewById(R.id.btnOK)
        btnClose= findViewById(R.id.btnClose)
        btnOTP= findViewById(R.id.btnOTP)

        btnOTP.setOnClickListener {
            val phoneNumber = edtSDT.text.toString()
            if (phoneNumber.isNotEmpty()) {
                sendVerificationCode(phoneNumber)
            } else {
                Toast.makeText(this, "Please enter a phone number.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun sendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    val code = credential.smsCode
                    if (code != null) {
                        edtOTP.setText(code)
                        verifyCode(code)
                    }
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Log.e("MainActivity", "Verification failed", e)
                    Toast.makeText(this@ForgotPasswordActivity, "Verification failed", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(verificationId, token)
                    this@ForgotPasswordActivity.verificationId = verificationId
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Verification successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Verification failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
