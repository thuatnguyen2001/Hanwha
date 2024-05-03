package com.thuatnguyen.hanwhalife.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.model.ThongTinCaNhanItem

class ThongTinCaNhanApdapter(context: Context, resource: Int, objects: List<ThongTinCaNhanItem>):ArrayAdapter<ThongTinCaNhanItem>(context, resource, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.dong_thong_tin_ca_nhan, null)
        }

        val item = getItem(position)
        val txtLabel = view?.findViewById<TextView>(R.id.txtLabel)
        val txtValue = view?.findViewById<TextView>(R.id.txtValue)

        txtLabel?.text = item?.label
        txtValue?.text = item?.value

        return view!!
    }
}