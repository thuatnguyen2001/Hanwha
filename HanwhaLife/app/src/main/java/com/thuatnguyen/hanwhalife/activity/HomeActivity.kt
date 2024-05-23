package com.thuatnguyen.hanwhalife.activity

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.fragment.DichVuFragment
import com.thuatnguyen.hanwhalife.fragment.HomeFragment
import com.thuatnguyen.hanwhalife.fragment.HopDongFragment
import com.thuatnguyen.hanwhalife.fragment.OtherFragment
import com.thuatnguyen.hanwhalife.fragment.TopHopDongFragment
import com.thuatnguyen.hanwhalife.fragment.TopLogoFragment
import com.thuatnguyen.hanwhalife.model.Account
import com.thuatnguyen.hanwhalife.model.BMBH
import com.thuatnguyen.hanwhalife.model.NDBH
import com.thuatnguyen.hanwhalife.model.NTH

@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {
    var account: Account?=null
    lateinit var bmbh: BMBH
    lateinit var ndbh: NDBH
    lateinit var nth: NTH
    lateinit var databaseReference: DatabaseReference
    lateinit var cccdCu:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        databaseReference = FirebaseDatabase.getInstance().reference
        account = intent.getParcelableExtra<Account>("ACCOUNT")!!
        cccdCu = intent.getStringExtra("CCCDCU").toString()
        loadDuLieu()

        Log.d("ddddd",cccdCu)
        Toast.makeText(this@HomeActivity, cccdCu, Toast.LENGTH_SHORT).show()

        val topFrame = TopLogoFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameTop,topFrame)
            commit()
        }

        val homeFrame = HomeFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameMain,homeFrame)
            commit()
        }

        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.itemHome ->{
                    val homeFrame = HomeFragment()
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameMain,homeFrame)
                        commit()
                    }
                    val topFrame = TopLogoFragment()
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameTop,topFrame)
                        commit()
                    }
                    true
                }
                R.id.itemDichVu ->{
                    val dichVuFrame = DichVuFragment()
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameMain,dichVuFrame)
                        commit()
                    }
                    val topFrame = TopLogoFragment()
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameTop,topFrame)
                        commit()
                    }
                    true
                }

                R.id.itemHopDong  ->{
                    val hopDongFrame = HopDongFragment()
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameMain,hopDongFrame)
                        commit()
                    }
                    val topFrame = TopHopDongFragment()
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameTop,topFrame)
                        commit()
                    }
                    true
                }

                R.id.itemKhac ->{
                    val otherFrame = OtherFragment()
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameMain,otherFrame)
                        commit()
                    }
                    val topFrame = TopLogoFragment()
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frameTop,topFrame)
                        commit()
                    }
                    true
                }
                else -> {true}
            }
        }
    }
    private fun loadDuLieu() {

        databaseReference.child("BMBH").orderByChild("accountID").equalTo(account!!.accountID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (snapshot in dataSnapshot.children) {
                            bmbh = snapshot.getValue(BMBH::class.java)!!
                            return
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Firebase", "Error: ${databaseError.message}")
                    // Hiển thị thông báo lỗi nếu có lỗi xảy ra khi truy vấn dữ liệu
                    Toast.makeText(this@HomeActivity, "An error occurred", Toast.LENGTH_SHORT).show()
                }
            })

        databaseReference.child("NDBH").orderByChild("accountID").equalTo(account!!.accountID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (snapshot in dataSnapshot.children) {
                            ndbh = snapshot.getValue(NDBH::class.java)!!
                            return
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Firebase", "Error: ${databaseError.message}")
                    // Hiển thị thông báo lỗi nếu có lỗi xảy ra khi truy vấn dữ liệu
                    Toast.makeText(this@HomeActivity, "An error occurred", Toast.LENGTH_SHORT).show()
                }
            })

        databaseReference.child("NTH").orderByChild("accountID").equalTo(account!!.accountID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (snapshot in dataSnapshot.children) {
                            nth = snapshot.getValue(NTH::class.java)!!
                            return
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Firebase", "Error: ${databaseError.message}")
                    // Hiển thị thông báo lỗi nếu có lỗi xảy ra khi truy vấn dữ liệu
                    Toast.makeText(this@HomeActivity, "An error occurred", Toast.LENGTH_SHORT).show()
                }
            })

//        databaseReference.child("THAYDOICCCD").child(cccdCu).orderByChild("status").equalTo("true")
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        for (snapshot in dataSnapshot.children) {
//                            databaseReference.child("BMBH").orderByChild("cccd").equalTo(cccdCu)
//                                .addListenerForSingleValueEvent(object : ValueEventListener {
//                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
//                                        if (dataSnapshot.exists()) {
//                                            for (snapshot in dataSnapshot.children) {
//                                                Toast.makeText(this@HomeActivity, dataSnapshot.key.toString(), Toast.LENGTH_SHORT).show()
//                                                return
//                                            }
//                                        }
//                                    }
//
//                                    override fun onCancelled(databaseError: DatabaseError) {
//                                        Log.e("Firebase", "Error: ${databaseError.message}")
//                                        // Hiển thị thông báo lỗi nếu có lỗi xảy ra khi truy vấn dữ liệu
//                                        Toast.makeText(this@HomeActivity, "An error occurred", Toast.LENGTH_SHORT).show()
//                                    }
//                                })
//                            return
//                        }
//                    }
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    Log.e("Firebase", "Error: ${databaseError.message}")
//                    // Hiển thị thông báo lỗi nếu có lỗi xảy ra khi truy vấn dữ liệu
//                    Toast.makeText(this@HomeActivity, "An error occurred", Toast.LENGTH_SHORT).show()
//                }
//            })
    }
    companion object {
        fun closeThisActivity(activity: Activity) {
            activity.finish() // Đóng Activity hiện tại
        }
    }
}