package com.thuatnguyen.hanwhalife.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.fragment.ChonLoaiDieuChinhFragment
import com.thuatnguyen.hanwhalife.fragment.DieuChinhCaNhan2Fragment
import com.thuatnguyen.hanwhalife.model.Account
import com.thuatnguyen.hanwhalife.model.BMBH
import com.thuatnguyen.hanwhalife.model.NDBH
import com.thuatnguyen.hanwhalife.model.NTH
@Suppress("DEPRECATION")
class DieuChinhCaNhanActivity : AppCompatActivity() {
    lateinit var bmbh: BMBH
    lateinit var ndbh: NDBH
    lateinit var nth: NTH
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dieu_chinh_ca_nhan)

        bmbh = intent.getParcelableExtra<BMBH>("BMBH")!!
        ndbh = intent.getParcelableExtra<NDBH>("NDBH")!!
        nth = intent.getParcelableExtra<NTH>("NTH")!!
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val newFragment = ChonLoaiDieuChinhFragment() // Thay NewFragment bằng Fragment bạn muốn thay đổi
        fragmentTransaction.replace(R.id.frameDieuChinh, newFragment)
        fragmentTransaction.addToBackStack(null) // Thêm Fragment vào Stack để có thể quay lại khi cần
        fragmentTransaction.commit()

//        val value = intent.getStringExtra("DieuChinh")
//        if(value==null)
//        {
//
//            Toast.makeText(this,bmbh.hoTen,Toast.LENGTH_SHORT).show()
//            val buoc1Frame = ChonLoaiDieuChinhFragment()
//            supportFragmentManager.beginTransaction().apply {
//                replace(R.id.frameDieuChinh, buoc1Frame)
//                commit()
//            }
//        }else
//        {
//            val buoc2Frame = DieuChinhCaNhan2Fragment()
//            supportFragmentManager.beginTransaction().apply {
//                replace(R.id.frameDieuChinh,buoc2Frame)
//                commit()
//            }
//        }
    }
}