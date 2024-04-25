package com.thuatnguyen.hanwhalife.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.fragment.DichVuFragment
import com.thuatnguyen.hanwhalife.fragment.HomeFragment
import com.thuatnguyen.hanwhalife.fragment.HopDongFragment
import com.thuatnguyen.hanwhalife.fragment.OtherFragment
import com.thuatnguyen.hanwhalife.fragment.TopHopDongFragment
import com.thuatnguyen.hanwhalife.fragment.TopLogoFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

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
}