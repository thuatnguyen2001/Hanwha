package com.thuatnguyen.hanwhalife.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.get
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.activity.DieuChinhCaNhanActivity
import com.thuatnguyen.hanwhalife.activity.HomeActivity
import com.thuatnguyen.hanwhalife.adapter.DoiTuongAdapter
import com.thuatnguyen.hanwhalife.adapter.ThongTinCaNhanApdapter
import com.thuatnguyen.hanwhalife.model.Account
import com.thuatnguyen.hanwhalife.model.Person

class DieuChinhCaNhan2Fragment : Fragment() {

    lateinit var lvDoiTuong:ListView
    lateinit var btnBack:LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dieu_chinh_ca_nhan2, container, false)
        btnBack = view.findViewById(R.id.btnBack)

        lvDoiTuong = view.findViewById(R.id.lvDoiTuong)
        val doiTuongList = ArrayList<Person>()
        val bmbh = (activity as DieuChinhCaNhanActivity).bmbh
        val ndbh = (activity as DieuChinhCaNhanActivity).ndbh
        val nth = (activity as DieuChinhCaNhanActivity).nth
        doiTuongList.add(Person(bmbh.bmbhID,bmbh.hoTen,bmbh.ngaySinh,bmbh.gioiTinh,bmbh.cccd,bmbh.ngayCap,bmbh.noiCap))
        if(!ndbh.cccd.equals(bmbh.cccd))
        {
            doiTuongList.add(Person(ndbh.ndbhID,ndbh.hoTen,ndbh.ngaySinh,ndbh.gioiTinh,ndbh.cccd,ndbh.ngayCap,ndbh.noiCap))
        }
        if(!nth.cccd.equals(bmbh.cccd) && !nth.cccd.equals(ndbh.cccd))
        {
            doiTuongList.add(Person(nth.nthID,nth.hoTen,nth.ngaySinh,nth.gioiTinh,nth.cccd,nth.ngayCap,nth.noiCap))
        }

        val adapter = DoiTuongAdapter(requireContext(), R.layout.dong_doi_tuong, doiTuongList)
        lvDoiTuong.adapter = adapter

        lvDoiTuong.setOnItemClickListener { parent, view, position, id ->
            val bundle = Bundle()
            bundle.putParcelable("person", doiTuongList.get(position))

            // Thay đổi Fragment từ Fragment hiện tại
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            val newFragment = DieuChinhCaNhan3Fragment() // Thay NewFragment bằng Fragment bạn muốn thay đổi
            newFragment.arguments = bundle
            fragmentTransaction.replace(R.id.frameDieuChinh, newFragment,"Buoc3")
            fragmentTransaction.addToBackStack("Buoc3") // Thêm Fragment vào Stack để có thể quay lại khi cần
            fragmentTransaction.commit()
        }

        btnBack.setOnClickListener {
            val parentFragmentManager = parentFragmentManager
            parentFragmentManager.popBackStack()
        }
        return view
    }

}