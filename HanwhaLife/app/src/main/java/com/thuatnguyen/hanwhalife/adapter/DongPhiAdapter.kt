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
import com.thuatnguyen.hanwhalife.model.DongPhi
import com.thuatnguyen.hanwhalife.model.HopDong
import com.thuatnguyen.hanwhalife.model.NDBH

class DongPhiAdapter (context: Context, resource: Int, objects: List<DongPhi>):
    ArrayAdapter<DongPhi>(context, resource,objects){
    @SuppressLint("ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.dong_dong_phi, null)
        }

        val item = getItem(position)
        val txtNgay = view?.findViewById<TextView>(R.id.txtNgay)
        val txtHinhThuc = view?.findViewById<TextView>(R.id.txtHinhThuc)
        val txtPhi = view?.findViewById<TextView>(R.id.txtPhi)

        txtNgay?.text = item?.ngay
        txtHinhThuc?.text = item?.hinhThuc
        txtPhi?.text = formatMoney(item?.phi)

        val databaseReference = FirebaseDatabase.getInstance().reference

        return view!!
    }
    fun formatMoney(amount: Long?): String {
        val amountStr = amount.toString()
        val reversedStr = amountStr.reversed()
        val formattedStr = reversedStr.chunked(3).joinToString(",").reversed()
        return formattedStr
    }
}

