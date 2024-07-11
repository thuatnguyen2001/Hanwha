package com.thuatnguyen.hanwhalife.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.adapter.HopDongAdapter
import com.thuatnguyen.hanwhalife.adapter.SanPhamBoSungAdapter
import com.thuatnguyen.hanwhalife.model.BMBH
import com.thuatnguyen.hanwhalife.model.HopDong
import com.thuatnguyen.hanwhalife.model.NDBH
import com.thuatnguyen.hanwhalife.model.SanPhamBoSung
import com.thuatnguyen.hanwhalife.model.SanPhamChinh

class ChiTietHopDongActivity : AppCompatActivity() {
    lateinit var txtTenSP :TextView
    lateinit var txtSoHD :TextView
    lateinit var txtTinhTrang :TextView
    lateinit var txtTenBMBH :TextView
    lateinit var txtPhiDinhKy :TextView
    lateinit var txtNDBH :TextView
    lateinit var txtSoTienBH :TextView
    lateinit var txtTenSPChinh :TextView
    lateinit var txtPhiDinhKyChinh :TextView
    lateinit var txtSPBS :TextView
    lateinit var lvSPBS :ListView
    lateinit var txtNgayKy :TextView
    lateinit var txtNgayDenHan :TextView
    lateinit var btnBack :ImageButton
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chi_tiet_hop_dong)

        anhXa()

        val bmbh = intent.getParcelableExtra("BMBH") as BMBH?
        val hopDong = intent.getParcelableExtra("HOPDONG") as HopDong?
        databaseReference = FirebaseDatabase.getInstance().reference

        val ndbhId = hopDong?.ndbhID

        databaseReference.child("NDBH").orderByChild("ndbhID").equalTo(ndbhId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        val ndbh = data.getValue(NDBH::class.java)!!
                        txtNDBH.text=ndbh.hoTen
                        return
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Xử lý lỗi
                    Toast.makeText(this@ChiTietHopDongActivity, "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })


        txtTenSP.text = "Hanwha Life - "+ hopDong?.sanPhamChinh?.tenSP
        txtSoHD.text = hopDong?.hopDongID
        txtNgayKy.text = hopDong?.ngayKy
        txtNgayDenHan.text = hopDong?.ngayDenHan
        txtTinhTrang.text = hopDong?.sanPhamChinh?.status
        txtTenBMBH.text = bmbh?.hoTen
        txtTenSPChinh.text = hopDong?.sanPhamChinh?.tenSP
        txtPhiDinhKyChinh.text = formatMoney(hopDong?.sanPhamChinh?.phiDinhKy)

        txtSoTienBH.text = formatMoney(hopDong?.sanPhamChinh?.soTienBH)
        val listSPBS = ArrayList(hopDong?.listSPBS)

        var phiSPBS : Long =0
        if (listSPBS?.size == 0)
        {
            txtSPBS.visibility = View.GONE
        }else
        {
            txtSPBS.visibility =View.VISIBLE

            listSPBS.forEach { item ->
                if(item.status.equals("Có hiệu lực"))
                    phiSPBS += item.phiDinhKy!!
            }
        }
        txtPhiDinhKy.text = formatMoney(hopDong?.sanPhamChinh?.phiDinhKy!!+phiSPBS)


        val adapter = SanPhamBoSungAdapter(this,R.layout.dong_hop_dong,listSPBS)
        lvSPBS.adapter = adapter

        btnBack.setOnClickListener {
            finish()
        }
    }

    fun formatMoney(amount: Long?): String {
        val amountStr = amount.toString()
        val reversedStr = amountStr.reversed()
        val formattedStr = reversedStr.chunked(3).joinToString(",").reversed()
        return formattedStr
    }

    private fun anhXa() {
        txtTenSP = findViewById(R.id.txtTenSP)
        txtSoHD = findViewById(R.id.txtSoHD)
        txtTinhTrang = findViewById(R.id.txtTinhTrang)
        txtTenBMBH = findViewById(R.id.txtTenBMBH)
        txtPhiDinhKy = findViewById(R.id.txtPhiDinhKy)
        txtPhiDinhKyChinh = findViewById(R.id.txtPhiDinhKyChinh)
        txtNDBH = findViewById(R.id.txtTenNDBH)
        txtSoTienBH = findViewById(R.id.txtSoTienBH)
        txtTenSPChinh = findViewById(R.id.txtTenSPChinh)
        txtSPBS = findViewById(R.id.txtSPBoSung)
        lvSPBS =findViewById(R.id.lvSPBS)
        btnBack =findViewById(R.id.btnBack)
        txtNgayKy =findViewById(R.id.txtNgayKy)
        txtNgayDenHan =findViewById(R.id.txtNgayDenHan)

    }
}