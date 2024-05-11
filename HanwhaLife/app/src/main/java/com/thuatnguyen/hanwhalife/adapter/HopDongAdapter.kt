package com.thuatnguyen.hanwhalife.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.model.HopDong
import com.thuatnguyen.hanwhalife.model.NDBH
import com.thuatnguyen.hanwhalife.model.Person
import com.thuatnguyen.hanwhalife.model.SanPhamBoSung

class HopDongAdapter( context: Context, resource: Int, objects: List<HopDong>):
    ArrayAdapter<HopDong>(context, resource,objects) {
    @SuppressLint("ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.dong_hop_dong, null)
        }

        val item = getItem(position)
        val txtTenSP = view?.findViewById<TextView>(R.id.txtTenSP)
        val txtSoHD = view?.findViewById<TextView>(R.id.txtSoHD)
        val txtSoTienBH = view?.findViewById<TextView>(R.id.txtSoTienBH)
        val txtNDBH = view?.findViewById<TextView>(R.id.txtNDBH)

        txtTenSP?.text = item?.sanPhamChinh?.tenSP
        txtSoHD?.text = item?.hopDongID+" - "+item?.sanPhamChinh?.status
        txtSoTienBH?.text = formatMoney(item?.sanPhamChinh?.soTienBH)

        val databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child("NDBH").orderByChild("ndbhID").equalTo(item?.ndbhID).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val ndbh = snapshot.getValue(NDBH::class.java)
                        txtNDBH?.text=ndbh?.hoTen
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Xử lý lỗi nếu có
            }
        })

        return view!!
    }
    fun formatMoney(amount: Long?): String {
        val amountStr = amount.toString()
        val reversedStr = amountStr.reversed()
        val formattedStr = reversedStr.chunked(3).joinToString(",").reversed()
        return formattedStr
    }
}

