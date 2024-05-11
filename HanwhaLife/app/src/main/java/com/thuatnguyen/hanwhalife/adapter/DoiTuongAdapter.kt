package com.thuatnguyen.hanwhalife.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.model.Person
import com.thuatnguyen.hanwhalife.model.ThongTinCaNhanItem

class DoiTuongAdapter (context: Context, resource: Int, objects: List<Person>):
    ArrayAdapter<Person>(context, resource, objects) {
    @SuppressLint("ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.dong_doi_tuong, null)
        }

        val item = getItem(position)
        val txtHoTen = view?.findViewById<TextView>(R.id.txtHoTen)
        val txtCCCD = view?.findViewById<TextView>(R.id.txtCCCD)
        val txtGioiTinh = view?.findViewById<TextView>(R.id.txtGioiTinh)
        val txtNgaySinh = view?.findViewById<TextView>(R.id.txtNgaySinh)

        txtHoTen?.text = item?.hoTen
        txtCCCD?.text = item?.cccd
        txtGioiTinh?.text = item?.gioiTinh
        txtNgaySinh?.text = item?.ngaySinh

        return view!!
    }
}