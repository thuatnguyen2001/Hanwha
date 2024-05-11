package com.thuatnguyen.hanwhalife.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.activity.DieuChinhCaNhanActivity
import com.thuatnguyen.hanwhalife.activity.HomeActivity
import com.thuatnguyen.hanwhalife.adapter.DoiTuongAdapter
import com.thuatnguyen.hanwhalife.adapter.ThongTinCaNhanApdapter
import com.thuatnguyen.hanwhalife.model.Account
import com.thuatnguyen.hanwhalife.model.Person

class DieuChinhCaNhan2Fragment : Fragment() {

    lateinit var lvDoiTuong:ListView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dieu_chinh_ca_nhan2, container, false)


        lvDoiTuong = view.findViewById(R.id.lvDoiTuong)
        val doiTuongList = ArrayList<Person>()
        val bmbh = (activity as DieuChinhCaNhanActivity).bmbh
        val ndbh = (activity as DieuChinhCaNhanActivity).ndbh
        val nth = (activity as DieuChinhCaNhanActivity).nth
        doiTuongList.add(Person(bmbh.hoTen,bmbh.ngaySinh,bmbh.gioiTinh,bmbh.cccd))
        if(!ndbh.cccd.equals(bmbh.cccd))
        {
            doiTuongList.add(Person(ndbh.hoTen,ndbh.ngaySinh,ndbh.gioiTinh,ndbh.cccd))
        }
        if(!nth.cccd.equals(bmbh.cccd))
        {
            doiTuongList.add(Person(nth.hoTen,nth.ngaySinh,nth.gioiTinh,nth.cccd))
        }

        val adapter = DoiTuongAdapter(requireContext(), R.layout.dong_doi_tuong, doiTuongList)
        lvDoiTuong.adapter = adapter

        lvDoiTuong.setOnItemClickListener { parent, view, position, id ->
            // Thay đổi Fragment từ Fragment hiện tại
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            val newFragment = DieuChinhCaNhan3Fragment() // Thay NewFragment bằng Fragment bạn muốn thay đổi
            fragmentTransaction.replace(R.id.frameDieuChinh, newFragment)
            fragmentTransaction.addToBackStack(null) // Thêm Fragment vào Stack để có thể quay lại khi cần
            fragmentTransaction.commit()
        }

        return view
    }

}