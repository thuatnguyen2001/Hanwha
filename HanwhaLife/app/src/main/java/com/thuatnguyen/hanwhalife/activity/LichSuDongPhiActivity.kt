package com.thuatnguyen.hanwhalife.activity

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.adapter.DongPhiAdapter
import com.thuatnguyen.hanwhalife.adapter.HopDongAdapter
import com.thuatnguyen.hanwhalife.model.DongPhi
import com.thuatnguyen.hanwhalife.model.HopDong

class LichSuDongPhiActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    lateinit var lvLichSuDongPhi: ListView
    lateinit var txtSoHD: TextView
    lateinit var btnQuayLai: LinearLayout
    lateinit var lichSuDongPhi:MutableList<DongPhi>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lich_su_dong_phi)

        anhXa()
        loadDuLieu()

        btnQuayLai.setOnClickListener {
            finish()
        }
    }

    private fun loadDuLieu() {
        val hopDong = intent.getParcelableExtra<HopDong>("HopDong")
        txtSoHD.text=hopDong?.hopDongID
        val lichSuDongPhi = ArrayList(hopDong?.lichSuDongPhi)
        val adapter = DongPhiAdapter(this,R.layout.dong_dong_phi,lichSuDongPhi)
        lvLichSuDongPhi.adapter = adapter
    }

    private fun anhXa() {
        databaseReference = FirebaseDatabase.getInstance().reference
        lvLichSuDongPhi = findViewById(R.id.lvLichSuDongPhi)
        txtSoHD = findViewById(R.id.txtSoHD)
        btnQuayLai = findViewById(R.id.btnBack)
        lichSuDongPhi = mutableListOf()
    }

}