package com.thuatnguyen.hanwhalife.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.model.BMBH
import com.thuatnguyen.hanwhalife.model.Person

class DieuChinhCaNhan3Fragment : Fragment() {
    lateinit var databaseReference: DatabaseReference
    lateinit var txtHoTen :TextView
    lateinit var txtCMND :TextView
    lateinit var txtNgayCap :TextView
    lateinit var txtNoiCap :TextView
    lateinit var edtCMND :EditText
    lateinit var edtNgayCap :EditText
    lateinit var edtNoiCap :EditText
    lateinit var person: Person
    lateinit var btnNext: LinearLayout
    lateinit var btnBack: LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dieu_chinh_ca_nhan3, container, false)

        anhXa(view)
        person = arguments?.getParcelable<Person>("person")!!
        loadDuLieu()
        databaseReference = FirebaseDatabase.getInstance().reference

        btnNext.setOnClickListener {
            val cmnd = edtCMND.text.toString().trim()
            val ngaycap = edtNgayCap.text.toString().trim()
            val noiCap = edtNoiCap.text.toString().trim()
            if (cmnd.isNotEmpty() && ngaycap.isNotEmpty() && noiCap.isNotEmpty()) {
                xuLyNext(cmnd,ngaycap,noiCap)
            } else {
                // Hiển thị thông báo lỗi nếu trường username hoặc password rỗng
                Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            val parentFragmentManager = parentFragmentManager
            parentFragmentManager.popBackStack()
        }

        return view
    }

    private fun xuLyNext(cmnd: String, ngaycap: String, noiCap: String) {
        val bundle = Bundle()
        bundle.putString("CMNDCU", person.cccd)
        bundle.putString("CMND", cmnd)
        bundle.putString("NGAYCAP", ngaycap)
        bundle.putString("NOICAP", noiCap)
        // Thay đổi Fragment từ Fragment hiện tại
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val newFragment = DieuChinhCaNhan4Fragment() // Thay NewFragment bằng Fragment bạn muốn thay đổi
        newFragment.arguments = bundle
        fragmentTransaction.replace(R.id.frameDieuChinh, newFragment,"Buoc4")
        fragmentTransaction.addToBackStack("Buoc4") // Thêm Fragment vào Stack để có thể quay lại khi cần
        fragmentTransaction.commit()
    }

    private fun loadDuLieu() {
        txtHoTen.text = person.hoTen
        txtCMND.text = person.cccd
        txtNgayCap.text = person.ngayCap
        txtNoiCap.text = person.noiCap
    }

    private fun anhXa(view: View?) {
        if (view != null) {
            txtHoTen = view?.findViewById(R.id.txtHoTen)!!
            txtCMND = view?.findViewById(R.id.txtCMND)!!
            txtNgayCap = view?.findViewById(R.id.txtNgayCap)!!
            txtNoiCap = view?.findViewById(R.id.txtNoiCap)!!
            edtCMND = view?.findViewById(R.id.edtCMND)!!
            edtNgayCap = view?.findViewById(R.id.edtNgayCap)!!
            edtNoiCap = view?.findViewById(R.id.edtNoiCap)!!
            btnNext = view?.findViewById(R.id.btnNext)!!
            btnBack = view?.findViewById(R.id.btnBack)!!

        }
    }

}