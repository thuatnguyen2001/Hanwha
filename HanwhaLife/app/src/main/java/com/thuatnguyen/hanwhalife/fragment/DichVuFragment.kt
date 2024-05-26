package com.thuatnguyen.hanwhalife.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.activity.DieuChinhCaNhanActivity
import com.thuatnguyen.hanwhalife.activity.HomeActivity
import com.thuatnguyen.hanwhalife.activity.PayActivity
import com.thuatnguyen.hanwhalife.activity.ThongTinLienHeActivity
import com.thuatnguyen.hanwhalife.dialog.DieuChinhCaNhanDialog

class DichVuFragment : Fragment() {

    lateinit var itemGQQL: LinearLayout
    lateinit var itemThanhToan: LinearLayout
    lateinit var itemDieuChinhTTCN: LinearLayout
    lateinit var itemDieuChinhHD: LinearLayout
    lateinit var itemChamSoc: LinearLayout
    lateinit var itemThuVien: LinearLayout
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       var view = inflater.inflate(R.layout.fragment_dich_vu, container, false)

        anhXa(view)
        addEvent()

        return view
    }

    private fun addEvent() {
        xuLyChucNangGQQL();
        xuLyChucNangThanhToan();
        xuLyChucNangDieuChinhTTCN();
        xuLyChucNangDieuChinhHD();
        xuLyChucNangChamSoc();
        xuLyChucNangThuVien();
    }

    private fun xuLyChucNangThuVien() {
        itemThuVien.setOnClickListener {
            Toast.makeText(this.context,"Chức năng này đang cập nhật", Toast.LENGTH_SHORT).show();
        }
    }

    private fun xuLyChucNangChamSoc() {
        itemChamSoc.setOnClickListener {
            val intent = Intent(activity, ThongTinLienHeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun xuLyChucNangDieuChinhHD() {
        itemDieuChinhHD.setOnClickListener {
            Toast.makeText(this.context,"Chức năng này đang cập nhật", Toast.LENGTH_SHORT).show();
        }
    }

    private fun xuLyChucNangDieuChinhTTCN() {

        itemDieuChinhTTCN.setOnClickListener {
            val bmbh = (activity as HomeActivity).bmbh
            val ndbh = (activity as HomeActivity).ndbh
            val nth = (activity as HomeActivity).nth
            val userID = (activity as HomeActivity).userID
            val intent = Intent(activity, DieuChinhCaNhanActivity::class.java)
            intent.putExtra("BMBH",bmbh)
            intent.putExtra("NDBH",ndbh)
            intent.putExtra("NTH",nth)
            intent.putExtra("userID",userID)
            startActivity(intent)
        }
    }

    private fun xuLyChucNangThanhToan() {
        itemThanhToan.setOnClickListener {
            val bmbhID = (activity as HomeActivity).bmbh.bmbhID
            if (bmbhID != null) {

                    val intent = Intent(activity, PayActivity::class.java)
                    intent.putExtra("bmbhID",bmbhID)
                    startActivity(intent)

            }


        }
    }

    private fun xuLyChucNangGQQL() {
        itemGQQL.setOnClickListener {
            Toast.makeText(this.context,"Chức năng này đang cập nhật", Toast.LENGTH_SHORT).show();
        }
    }

    private fun anhXa(view: View) {
        itemGQQL = view.findViewById(R.id.itemGQQL)
        itemThanhToan = view.findViewById(R.id.itemThanhToan)
        itemDieuChinhTTCN = view.findViewById(R.id.itemDieuChinhTTCN)
        itemDieuChinhHD = view.findViewById(R.id.itemDieuChinhHD)
        itemChamSoc = view.findViewById(R.id.itemChamSoc)
        itemThuVien = view.findViewById(R.id.itemThuVien)
        auth = FirebaseAuth.getInstance()
    }

}