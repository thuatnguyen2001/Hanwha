package com.thuatnguyen.hanwhalife.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.activity.DieuChinhCaNhanActivity

class ChonLoaiDieuChinhFragment : Fragment() {

    lateinit var btnBack: LinearLayout
    lateinit var btnNext: LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chon_loai_dieu_chinh, container, false)

        btnBack = view.findViewById(R.id.btnBack)
        btnNext = view.findViewById(R.id.btnNext)
        val spinner: Spinner = view.findViewById(R.id.spDieuChinh)
        val items = arrayOf("Chọn loại thông tin điều chỉnh","Điều chỉnh thông tin cá nhân", "Điều chỉnh tình trạng hôn nhân", "Điều chỉnh số điện thoại")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()

                btnNext.setOnClickListener {
                    if(selectedItem.equals("Chọn loại thông tin điều chỉnh"))
                    {
                        Toast.makeText(requireContext(),"Vui lòng chọn loại điều chỉnh", Toast.LENGTH_SHORT).show()
                    }
                    else if (selectedItem.equals("Điều chỉnh thông tin cá nhân")){
                        // Thay đổi Fragment từ Fragment hiện tại
                        val fragmentManager = requireActivity().supportFragmentManager
                        val fragmentTransaction = fragmentManager.beginTransaction()

                        val newFragment = DieuChinhCaNhan2Fragment() // Thay NewFragment bằng Fragment bạn muốn thay đổi
                        fragmentTransaction.replace(R.id.frameDieuChinh, newFragment)
                        fragmentTransaction.addToBackStack(null) // Thêm Fragment vào Stack để có thể quay lại khi cần
                        fragmentTransaction.commit()

                    }
                    else if (selectedItem.equals("Điều chỉnh tình trạng hôn nhân")){
                        val intent = Intent(activity, DieuChinhCaNhanActivity::class.java)
                        intent.putExtra("DieuChinh", "HonNhanBuoc1")
                        startActivity(intent)
                    }
                    else if (selectedItem.equals("Điều chỉnh số điện thoại")){
                        val intent = Intent(activity, DieuChinhCaNhanActivity::class.java)
                        intent.putExtra("DieuChinh", "DienThoaiBuoc1")
                        startActivity(intent)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Xử lý khi không có mục nào được chọn
            }
        }

        return view
    }

}