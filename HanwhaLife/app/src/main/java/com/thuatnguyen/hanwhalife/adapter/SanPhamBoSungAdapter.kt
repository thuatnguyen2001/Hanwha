package com.thuatnguyen.hanwhalife.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.model.Person
import com.thuatnguyen.hanwhalife.model.SanPhamBoSung

class SanPhamBoSungAdapter (context: Context, resource: Int, objects: List<SanPhamBoSung>):
    ArrayAdapter<SanPhamBoSung>(context, resource, objects) {
    @SuppressLint("ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.dong_san_pham_bo_sung, null)
        }

        val item = getItem(position)
        val txtTenSp = view?.findViewById<TextView>(R.id.txtTenSP)
        val txtSoTienBH = view?.findViewById<TextView>(R.id.txtSoTienBH)
        val txtPhiDinhKy = view?.findViewById<TextView>(R.id.txtPhiDinhKy)
        val txtTinhTrang = view?.findViewById<TextView>(R.id.txtTinhTrang)

        txtTenSp?.text = item?.tenSP
        txtSoTienBH?.text = formatMoney(item?.soTienBH)
        txtPhiDinhKy?.text = formatMoney(item?.phiDinhKy)
        txtTinhTrang?.text = item?.status

        return view!!
    }

    fun formatMoney(amount: Long?): String {
        val amountStr = amount.toString()
        val reversedStr = amountStr.reversed()
        val formattedStr = reversedStr.chunked(3).joinToString(",").reversed()
        return formattedStr
    }
}