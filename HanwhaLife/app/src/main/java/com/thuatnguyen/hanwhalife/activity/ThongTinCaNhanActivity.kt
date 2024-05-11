package com.thuatnguyen.hanwhalife.activity

import android.os.Bundle
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.adapter.ThongTinCaNhanApdapter
import com.thuatnguyen.hanwhalife.model.Account
import com.thuatnguyen.hanwhalife.model.BMBH
import com.thuatnguyen.hanwhalife.model.NDBH
import com.thuatnguyen.hanwhalife.model.ThongTinCaNhanItem

class ThongTinCaNhanActivity : AppCompatActivity() {

    lateinit var btnBack:ImageButton
    lateinit var txtHoTen:TextView
    lateinit var lvThongTinCaNhan:ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_thong_tin_ca_nhan)

        anhXa()
        loadDuLieu()
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadDuLieu() {
        val bmbh = intent.getParcelableExtra<BMBH>("BMBH")!!
        val account = intent.getParcelableExtra<Account>("ACCOUNT")!!
        txtHoTen.text = bmbh.hoTen

        val items = listOf(
            ThongTinCaNhanItem("Tên đăng nhập", account.username),
            ThongTinCaNhanItem("Họ và tên", bmbh.hoTen),
            ThongTinCaNhanItem("Ngày sinh", bmbh.ngaySinh),
            ThongTinCaNhanItem("CMND/CCCD", bmbh.cccd),
            ThongTinCaNhanItem("Ngày cấp", bmbh.ngayCap),
            ThongTinCaNhanItem("Nơi cấp", bmbh.noiCap),
            ThongTinCaNhanItem("Email", bmbh.email),
            ThongTinCaNhanItem("Số điện thoại", bmbh.sdt),
        )

        val adapter = ThongTinCaNhanApdapter(this, R.layout.dong_thong_tin_ca_nhan, items)
        lvThongTinCaNhan.adapter = adapter
    }

    private fun anhXa() {
        btnBack = findViewById(R.id.btnBack)
        txtHoTen = findViewById(R.id.txtHoTen)
        lvThongTinCaNhan = findViewById(R.id.lvThongTinCaNhan)
    }
}