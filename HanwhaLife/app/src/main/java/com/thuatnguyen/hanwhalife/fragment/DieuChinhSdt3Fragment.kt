package com.thuatnguyen.hanwhalife.fragment

import android.os.Bundle
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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.model.Person

class DieuChinhSdt3Fragment : Fragment() {

    private lateinit var database: FirebaseDatabase
    lateinit var txtSDT:TextView
    lateinit var edtSDT:EditText
    lateinit var person:Person
    lateinit var btnBack:LinearLayout
    lateinit var btnNext:LinearLayout
    var flag =0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view =inflater.inflate(R.layout.fragment_dieu_chinh_sdt3, container, false)

        database = FirebaseDatabase.getInstance()
        txtSDT = view.findViewById(R.id.txtSDT)
        edtSDT = view.findViewById(R.id.edtSDT)
        btnBack = view.findViewById(R.id.btnBack)
        btnNext = view.findViewById(R.id.btnNext)
        person = arguments?.getParcelable<Person>("person")!!
        txtSDT.text = person.sdt

        btnBack.setOnClickListener {
            val parentFragmentManager = parentFragmentManager
            parentFragmentManager.popBackStack()
        }

        btnNext.setOnClickListener {
            val sdt = edtSDT.text.toString()
            if (isValidPhoneNumber(sdt)) {
                val sdtCu=txtSDT.text.toString().trim()
                val updatedData = mapOf(
                    "sdt" to sdt
                )
                findAndUpdateRecords(sdtCu,updatedData)
            } else {
                edtSDT.error = "Số điện thoại không hợp lệ"
            }
        }
        return view
    }

    private fun findAndUpdateRecords(sdt: String, updates: Map<String, Any>) {
        val tables = listOf("BMBH", "NDBH", "NTH")

        tables.forEach { table ->
            val reference = database.getReference(table)
            reference.orderByChild("sdt").equalTo(sdt).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (dataSnapshot in snapshot.children) {
                            val key = dataSnapshot.key
                            if (key != null) {
                                reference.child(key).updateChildren(updates)
                                    .addOnSuccessListener {
                                        flag++;
                                        if (flag==1)
                                            Toast.makeText(requireContext(), "Thay đổi số điện thoại thành công", Toast.LENGTH_SHORT).show()
                                        activity?.finish()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(requireContext(), "Failed to update $table: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "No records found in $table for CCCD $sdt", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val regex = Regex("^0[35789]\\d{8}$")
        return phoneNumber.matches(regex)
    }
}