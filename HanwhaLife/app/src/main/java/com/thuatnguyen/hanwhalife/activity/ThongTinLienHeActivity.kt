package com.thuatnguyen.hanwhalife.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.thuatnguyen.hanwhalife.R
import android.widget.ImageButton

class ThongTinLienHeActivity : AppCompatActivity() {

    lateinit var btnBack:ImageButton
    lateinit var btnCall:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_thong_tin_lien_he)

        anhXa()
        addEvent()
    }

    private fun addEvent() {
        btnBack.setOnClickListener {
            finish()
        }

        val phoneNumber = "0987654321"
        btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        }
    }

    private fun anhXa() {
        btnBack = findViewById(R.id.btnBack)
        btnCall = findViewById(R.id.btnCall)
    }
}