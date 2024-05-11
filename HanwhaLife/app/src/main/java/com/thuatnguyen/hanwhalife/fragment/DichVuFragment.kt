package com.thuatnguyen.hanwhalife.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.dialog.DieuChinhCaNhanDialog

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
        itemThuVien.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Khi người dùng bắt đầu chạm vào LinearLayout
                    view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.xam))
                    Toast.makeText(this.context,"Chức năng này đang cập nhật", Toast.LENGTH_SHORT).show();
                }
                MotionEvent.ACTION_UP -> {
                    // Khi người dùng nhả ra LinearLayout
                    view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
            }
            true
        }
    }

    private fun xuLyChucNangChamSoc() {
        itemChamSoc.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Khi người dùng bắt đầu chạm vào LinearLayout
                    view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.xam))
                    Toast.makeText(this.context,"Chức năng này đang cập nhật", Toast.LENGTH_SHORT).show();
                }
                MotionEvent.ACTION_UP -> {
                    // Khi người dùng nhả ra LinearLayout
                    view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
            }
            true
        }
    }

    private fun xuLyChucNangDieuChinhHD() {
        itemDieuChinhHD.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Khi người dùng bắt đầu chạm vào LinearLayout
                    view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.xam))
                    Toast.makeText(this.context,"Chức năng này đang cập nhật", Toast.LENGTH_SHORT).show();
                }
                MotionEvent.ACTION_UP -> {
                    // Khi người dùng nhả ra LinearLayout
                    view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
            }
            true
        }
    }

    private fun xuLyChucNangDieuChinhTTCN() {

        itemDieuChinhTTCN.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Khi người dùng bắt đầu chạm vào LinearLayout
                    view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.xam))
                    val bottomSheetDialogFragment = DieuChinhCaNhanDialog()
                    bottomSheetDialogFragment.show(parentFragmentManager, bottomSheetDialogFragment.tag)
                }
                MotionEvent.ACTION_UP -> {
                    // Khi người dùng nhả ra LinearLayout
                    view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
            }
            true
        }
    }

    private fun xuLyChucNangThanhToan() {
        itemThanhToan.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Khi người dùng bắt đầu chạm vào LinearLayout
                    view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.xam))

                }
                MotionEvent.ACTION_UP -> {
                    // Khi người dùng nhả ra LinearLayout
                    view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
            }
            true
        }
    }

    private fun xuLyChucNangGQQL() {
        itemGQQL.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Khi người dùng bắt đầu chạm vào LinearLayout
                    view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.xam))
                    Toast.makeText(this.context,"Chức năng này đang cập nhật", Toast.LENGTH_SHORT).show();
                }
                MotionEvent.ACTION_UP -> {
                    // Khi người dùng nhả ra LinearLayout
                    view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
            }
            true
        }
    }

    private fun anhXa(view: View) {
        itemGQQL = view.findViewById(R.id.itemGQQL)
        itemThanhToan = view.findViewById(R.id.itemThanhToan)
        itemDieuChinhTTCN = view.findViewById(R.id.itemDieuChinhTTCN)
        itemDieuChinhHD = view.findViewById(R.id.itemDieuChinhHD)
        itemChamSoc = view.findViewById(R.id.itemChamSoc)
        itemThuVien = view.findViewById(R.id.itemThuVien)



        xuLyClick(itemThanhToan)
        xuLyClick(itemGQQL)
        xuLyClick(itemDieuChinhHD)
        xuLyClick(itemChamSoc)
        xuLyClick(itemThuVien)
    }

    private fun xuLyClick(itemDieuChinhTTCN: LinearLayout?) {
        itemDieuChinhTTCN?.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    // Khi người dùng nhả ra LinearLayout
                    view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
            }
            true
        }
    }

}