package com.thuatnguyen.hanwhalife.activity

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.adapter.HopDongAdapter
import com.thuatnguyen.hanwhalife.model.BMBH
import com.thuatnguyen.hanwhalife.model.DongPhi
import com.thuatnguyen.hanwhalife.model.HopDong
import com.thuatnguyen.hanwhalife.model.SanPhamBoSung
import com.thuatnguyen.hanwhalife.model.SanPhamChinh

class ChonHopDongThanhToanActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    lateinit var lvHopDong: ListView
    lateinit var btnClose: LinearLayout
    lateinit var bmbh: BMBH
    lateinit var listHD:MutableList<HopDong>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chon_hop_dong_thanh_toan)


        anhXa()
        loadDuLieu()

        btnClose.setOnClickListener {
            finish()
        }
    }

    private fun loadDuLieu() {
        val bmbhID = intent.getStringExtra("bmbhID")

        val query = databaseReference.child("hopDong").orderByChild("bmbhID").equalTo(bmbhID)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    // Lấy dữ liệu từ mỗi snapshot tương ứng và xử lý
                    val hopDong = snapshot.getValue(HopDong::class.java)
                    val sanPhamChinh = snapshot.child("sanPhamChinh").getValue(SanPhamChinh::class.java)
                    val sanPhamBoSung = snapshot.child("sanPhamBoSung").getValue(object : GenericTypeIndicator<List<SanPhamBoSung>>() {})
                    val lichSuDongPhi = snapshot.child("lichSuDongPhi").getValue(object : GenericTypeIndicator<List<DongPhi>>() {})
                    listHD.add(HopDong(hopDong?.hopDongID,hopDong?.bmbhID,hopDong?.ndbhID,hopDong?.nthID,hopDong?.ngayKy,hopDong?.ngayDenHan,hopDong?.phiBaoHiem,sanPhamChinh,
                        ArrayList(sanPhamBoSung),ArrayList(lichSuDongPhi)))

                }
                val adapter = HopDongAdapter(this@ChonHopDongThanhToanActivity,R.layout.dong_hop_dong,listHD)
                lvHopDong.adapter = adapter
                lvHopDong.setOnItemClickListener { parent, view, position, id ->
                    val intent = Intent(this@ChonHopDongThanhToanActivity, LichSuDongPhiActivity::class.java)
                    //Toast.makeText(this@ChonHopDongThanhToanActivity,listHD.get(position).lichSuDongPhi?.get(0)?.hinhThuc,Toast.LENGTH_SHORT).show()
                    intent.putExtra("HopDong",listHD.get(position))
                    startActivity(intent)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Xử lý lỗi nếu có
            }
        })
    }

    private fun anhXa() {
        databaseReference = FirebaseDatabase.getInstance().reference
        lvHopDong = findViewById(R.id.lvHopDong)
        btnClose = findViewById(R.id.btnClose)
        listHD = mutableListOf()
    }

}