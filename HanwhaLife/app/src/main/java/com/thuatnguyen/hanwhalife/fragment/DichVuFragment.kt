package com.thuatnguyen.hanwhalife.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.thuatnguyen.hanwhalife.R

class DichVuFragment : Fragment() {

    lateinit var itemGQQL: LinearLayout
    lateinit var itemThanhToan: LinearLayout
    lateinit var itemDieuChinhTTCN: LinearLayout
    lateinit var itemDieuChinhHD: LinearLayout
    lateinit var itemChamSoc: LinearLayout
    lateinit var itemThuVien: LinearLayout

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
        itemThuVien.setOnClickListener{
            Toast.makeText(this.context,"Chức năng này đang cập nhật", Toast.LENGTH_SHORT).show();
        }
    }

    private fun xuLyChucNangChamSoc() {
        itemChamSoc.setOnClickListener{
//            val intent = Intent(activity, ContactActivity::class.java)
//            startActivity(intent)
        }
    }

    private fun xuLyChucNangDieuChinhHD() {
        itemDieuChinhHD.setOnClickListener{
            Toast.makeText(this.context,"Chức năng này đang cập nhật", Toast.LENGTH_SHORT).show();
        }
    }

    private fun xuLyChucNangDieuChinhTTCN() {
        itemDieuChinhTTCN.setOnClickListener{
//            val bottomSheetDialogFragment = DialogCaNhan()
//            bottomSheetDialogFragment.show(parentFragmentManager, bottomSheetDialogFragment.tag)
        }
    }

    private fun xuLyChucNangThanhToan() {
        itemThanhToan.setOnClickListener{

        }
    }

    private fun xuLyChucNangGQQL() {
        itemGQQL.setOnClickListener{
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
    }

}