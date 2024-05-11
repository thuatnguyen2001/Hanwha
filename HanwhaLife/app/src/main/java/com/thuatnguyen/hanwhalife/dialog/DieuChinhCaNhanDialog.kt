package com.thuatnguyen.hanwhalife.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.activity.DieuChinhCaNhanActivity
import com.thuatnguyen.hanwhalife.activity.HomeActivity

class DieuChinhCaNhanDialog  : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_dieu_chinh_ca_nhan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnClose = view.findViewById<ImageButton>(R.id.btnClose)
        val btnListYeuCau = view.findViewById<Button>(R.id.btnListYeuCau)
        val btnNewYeuCau = view.findViewById<Button>(R.id.btnNewYeuCau)

        btnClose.setOnClickListener {
            this.dismiss()
        }

        btnNewYeuCau.setOnClickListener {
            val bmbh = (activity as HomeActivity).bmbh
            val ndbh = (activity as HomeActivity).ndbh
            val nth = (activity as HomeActivity).nth
            val intent = Intent(activity, DieuChinhCaNhanActivity::class.java)
            intent.putExtra("BMBH",bmbh)
            intent.putExtra("NDBH",ndbh)
            intent.putExtra("NTH",nth)
            startActivity(intent)
            this.dismiss()
        }

        btnListYeuCau.setOnClickListener {
            Toast.makeText(this.context,"List", Toast.LENGTH_SHORT).show()
        }
    }
}