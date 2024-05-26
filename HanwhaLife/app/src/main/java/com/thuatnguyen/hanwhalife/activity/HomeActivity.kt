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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.fragment.DichVuFragment
import com.thuatnguyen.hanwhalife.fragment.HopDongFragment
import com.thuatnguyen.hanwhalife.fragment.OtherFragment
import com.thuatnguyen.hanwhalife.fragment.TopHopDongFragment
import com.thuatnguyen.hanwhalife.fragment.TopLogoFragment
import com.thuatnguyen.hanwhalife.model.User
import com.thuatnguyen.hanwhalife.model.BMBH
import com.thuatnguyen.hanwhalife.model.NDBH
import com.thuatnguyen.hanwhalife.model.NTH

@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {
    lateinit var bmbh: BMBH
    lateinit var ndbh: NDBH
    lateinit var nth: NTH
    lateinit var databaseReference: DatabaseReference
    lateinit var cccdCu:String
    lateinit var userID:String

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "com.example.UPDATE_STRING") {
                val newString = intent.getStringExtra("new_string")
                newString?.let {
                    cccdCu = it
                    Log.d("eeee",cccdCu)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        databaseReference = FirebaseDatabase.getInstance().reference
        userID = intent.getStringExtra("userID").toString()
//        cccdCu=""
//        Log.d("eeee",cccdCu)
//
//        val filter = IntentFilter("com.example.UPDATE_STRING")
//        registerReceiver(broadcastReceiver, filter)
        loadDuLieu()



        val topFrame = TopLogoFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameTop,topFrame)
            commit()
        }

        val dvFrame = DichVuFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameMain,dvFrame)
            commit()
        }

        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
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
    override fun onDestroy() {
        super.onDestroy()
        // Hủy đăng ký BroadcastReceiver khi Activity bị hủy
        unregisterReceiver(broadcastReceiver)
    }
    private fun loadDuLieu() {

        databaseReference.child("BMBH").orderByChild("userID").equalTo(userID)
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

        databaseReference.child("NDBH").orderByChild("userID").equalTo(userID)
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

        databaseReference.child("NTH").orderByChild("userID").equalTo(userID)
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

    }
    companion object {
        fun closeThisActivity(activity: Activity) {
            activity.finish() // Đóng Activity hiện tại
        }
    }
}