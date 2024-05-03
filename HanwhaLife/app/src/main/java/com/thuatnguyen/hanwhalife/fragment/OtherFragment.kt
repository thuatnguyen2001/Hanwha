package com.thuatnguyen.hanwhalife.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.activity.HomeActivity
import com.thuatnguyen.hanwhalife.activity.ThongTinCaNhanActivity
import com.thuatnguyen.hanwhalife.activity.ThongTinLienHeActivity
import com.thuatnguyen.hanwhalife.model.Account
import com.thuatnguyen.hanwhalife.model.BMBH
import com.thuatnguyen.hanwhalife.model.NDBH

class OtherFragment : Fragment() {

    lateinit var databaseReference: DatabaseReference
    lateinit var txtHoTen:TextView
    lateinit var btnThongTinCaNhan:LinearLayout
    lateinit var btnThongTinLienHe:LinearLayout
    lateinit var btnDoiMatKhau:LinearLayout
    lateinit var btnDangXuat:LinearLayout
    lateinit var bmbh: BMBH
    lateinit var ndbh: NDBH
    lateinit var account: Account

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_other, container, false)

        anhXa(view)
        loadDuLieu()
        addEvent()

        return view
    }

    private fun loadDuLieu() {
        bmbh = (activity as HomeActivity).bmbh
        account = (activity as HomeActivity).account
        txtHoTen.text= bmbh.hoTen
    }

    private fun addEvent() {
       xuLythongTinCaNhan()
        xuLythongTinLienHe()
        xuLyDoiMatKhau()
        xuLyDangXuat()
    }

    private fun xuLyDangXuat() {
        btnDangXuat.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun xuLyDoiMatKhau() {
        btnDoiMatKhau.setOnClickListener {

        }
    }

    private fun xuLythongTinLienHe() {
        btnThongTinLienHe.setOnClickListener {
            val intent = Intent(activity, ThongTinLienHeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun xuLythongTinCaNhan() {
        btnThongTinCaNhan.setOnClickListener {
            val intent = Intent(activity, ThongTinCaNhanActivity::class.java)
            intent.putExtra("BMBH",bmbh)
            intent.putExtra("ACCOUNT",account)
            startActivity(intent)
        }
    }

    private fun anhXa(view: View) {
        databaseReference = FirebaseDatabase.getInstance().reference
        txtHoTen = view.findViewById(R.id.txtHoTen)
        btnThongTinCaNhan = view.findViewById(R.id.btnThongTinCaNhan)
        btnThongTinLienHe = view.findViewById(R.id.btnThongTinLienHe)
        btnDangXuat = view.findViewById(R.id.btnDangXuat)
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau)
    }

}