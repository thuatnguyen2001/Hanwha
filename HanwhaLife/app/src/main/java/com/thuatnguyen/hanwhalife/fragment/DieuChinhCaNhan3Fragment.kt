package com.thuatnguyen.hanwhalife.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.model.ApiService
import com.thuatnguyen.hanwhalife.model.BMBH

import com.thuatnguyen.hanwhalife.model.Person
import com.thuatnguyen.hanwhalife.model.ProvinceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.ImageButton
import com.thuatnguyen.hanwhalife.activity.DieuChinhCaNhanActivity
import com.thuatnguyen.hanwhalife.activity.HomeActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DieuChinhCaNhan3Fragment : Fragment() {
    private lateinit var database: FirebaseDatabase
    lateinit var txtHoTen :TextView
    lateinit var txtCMND :TextView
    lateinit var txtNgayCap :TextView
    lateinit var txtNoiCap :TextView
    lateinit var edtCMND :EditText
    lateinit var edtNgayCap :EditText
    lateinit var edtNoiCap :Spinner
    lateinit var person: Person
    lateinit var btnNext: LinearLayout
    lateinit var btnBack: LinearLayout
    lateinit var btnDate: ImageButton
    private lateinit var apiService: ApiService
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var flag:Int=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dieu_chinh_ca_nhan3, container, false)

        anhXa(view)
        person = arguments?.getParcelable<Person>("person")!!
        loadDuLieu()
        database = FirebaseDatabase.getInstance()

        btnNext.setOnClickListener {
           val cccd = edtCMND.text.toString()
            val ngayCap = edtNgayCap.text.toString()
            val noiCap =edtNoiCap.selectedItem.toString()
            if (isValidCCCD(cccd) && isValidDate(ngayCap) && isNgayCapValid(ngayCap)) {
                val cccdCu=txtCMND.text.toString().trim()
                val updatedData = mapOf(
                    "cccd" to cccd,
                    "ngayCap" to ngayCap,
                    "noiCap" to noiCap
                )

                findAndUpdateRecords(cccdCu,updatedData)
            } else {
                if (!isValidCCCD(cccd)) {
                    Toast.makeText(requireContext(), "CCCD phải đủ 12 số", Toast.LENGTH_SHORT).show()
                }
                if (!isValidDate(ngayCap)) {
                    Toast.makeText(requireContext(), "Ngày cấp không hợp lệ. Định dạng đúng: dd/MM/yyyy", Toast.LENGTH_SHORT).show()
                }
                if (!isNgayCapValid(ngayCap)) {
                    Toast.makeText(requireContext(), "Ngày cấp phải nhỏ hơn ngày hiện tại", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnBack.setOnClickListener {
            val parentFragmentManager = parentFragmentManager
            parentFragmentManager.popBackStack()
        }

        btnDate.setOnClickListener {
            showDatePickerDialog()
        }

        edtNgayCap.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val dateText = s.toString()
                if (dateText.isNotEmpty() && !isValidDate(dateText)) {
                    edtNgayCap.error = "Ngày tháng không hợp lệ. Định dạng đúng: dd/MM/yyyy"
                } else {
                    edtNgayCap.error = null
                }
            }
        })

        edtCMND.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.length != 12) {
                    edtCMND.error = "CCCD phải đủ 12 số"
                } else {
                    edtCMND.error = null
                }
            }
        })

        return view
    }

    private fun findAndUpdateRecords(cccd: String, updates: Map<String, Any>) {
        val tables = listOf("BMBH", "NDBH", "NTH")

        tables.forEach { table ->
            val reference = database.getReference(table)
            reference.orderByChild("cccd").equalTo(cccd).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (dataSnapshot in snapshot.children) {
                            val key = dataSnapshot.key
                            if (key != null) {
                                reference.child(key).updateChildren(updates)
                                    .addOnSuccessListener {
                                        flag++;
                                        if (flag==1)
                                            Toast.makeText(requireContext(), "Thay đổi CCCD thành công", Toast.LENGTH_SHORT).show()
                                        activity?.finish()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(requireContext(), "Failed to update $table: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "No records found in $table for CCCD $cccd", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun isValidCCCD(cccd: String): Boolean {
        return cccd.length == 12
    }

    private fun isValidDate(date: String): Boolean {
        return try {
            dateFormat.isLenient = false
            dateFormat.parse(date)
            true
        } catch (e: ParseException) {
            false
        }
    }

    private fun isNgayCapValid(date: String): Boolean {
        return try {
            val ngayCapDate = dateFormat.parse(date)
            val currentDate = Calendar.getInstance().time
            ngayCapDate.before(currentDate)
        } catch (e: ParseException) {
            false
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()

        val currentText = edtNgayCap.text.toString()
        if (currentText.isNotEmpty() && isValidDate(currentText)) {
            val date = dateFormat.parse(currentText)
            if (date != null) {
                calendar.time = date
            }
        }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear)
            edtNgayCap.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
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
        val retrofit = Retrofit.Builder()
            .baseUrl("https://esgoo.net/") // Thay thế bằng URL gốc của API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
        loadProvinces()
    }

    private fun loadProvinces() {
        apiService.getProvinces().enqueue(object : Callback<ProvinceResponse> {
            override fun onResponse(call: Call<ProvinceResponse>, response: Response<ProvinceResponse>) {
                if (response.isSuccessful) {
                    val provinces = response.body()?.data
                    if (provinces != null) {
                        val provinceNames = provinces.map { it.name }
                        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, provinceNames)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        edtNoiCap.adapter = adapter
                    } else {
                        Toast.makeText(requireContext(), "No data available", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load provinces", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProvinceResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun anhXa(view: View?) {
        if (view != null) {
            txtHoTen = view?.findViewById(R.id.txtHoTen)!!
            txtCMND = view?.findViewById(R.id.txtCMND)!!
            txtNgayCap = view?.findViewById(R.id.txtNgayCap)!!
            txtNoiCap = view?.findViewById(R.id.txtNoiCap)!!
            edtCMND = view?.findViewById(R.id.edtCMND)!!
            edtNgayCap = view?.findViewById(R.id.edtNgayCap)!!
            edtNoiCap = view?.findViewById(R.id.spNoiCap)!!
            btnNext = view?.findViewById(R.id.btnNext)!!
            btnBack = view?.findViewById(R.id.btnBack)!!
            btnDate = view?.findViewById(R.id.btnDate)!!

        }
    }

}