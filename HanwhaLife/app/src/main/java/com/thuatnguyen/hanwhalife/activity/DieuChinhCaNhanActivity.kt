package com.thuatnguyen.hanwhalife.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.fragment.ChonLoaiDieuChinhFragment
import com.thuatnguyen.hanwhalife.model.BMBH
import com.thuatnguyen.hanwhalife.model.NDBH
import com.thuatnguyen.hanwhalife.model.NTH
@Suppress("DEPRECATION")
class DieuChinhCaNhanActivity : AppCompatActivity() {
    lateinit var bmbh: BMBH
    lateinit var ndbh: NDBH
    lateinit var nth: NTH
    lateinit var userID: String
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dieu_chinh_ca_nhan)

        database = FirebaseDatabase.getInstance().reference
        userID = intent.getStringExtra("userID")!!

        loadDuLieu()

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val chonLoaiDieuChinhFragment = ChonLoaiDieuChinhFragment()
        // Thay NewFragment bằng Fragment bạn muốn thay đổi
        fragmentTransaction.replace(R.id.frameDieuChinh, chonLoaiDieuChinhFragment, "Buoc1")
        fragmentTransaction.addToBackStack("buoc1") // Thêm Fragment vào Stack để có thể quay lại khi cần
        fragmentTransaction.commit()

    }

    private fun loadDuLieu() {
        database.child("BMBH").orderByChild("userID").equalTo(userID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        bmbh = data.getValue(BMBH::class.java)!!
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Xử lý lỗi
                    Toast.makeText(this@DieuChinhCaNhanActivity, "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })

        database.child("NDBH").orderByChild("userID").equalTo(userID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        ndbh = data.getValue(NDBH::class.java)!!
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Xử lý lỗi
                    Toast.makeText(this@DieuChinhCaNhanActivity, "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })

        database.child("NTH").orderByChild("userID").equalTo(userID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        nth = data.getValue(NTH::class.java)!!
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Xử lý lỗi
                    Toast.makeText(this@DieuChinhCaNhanActivity, "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}