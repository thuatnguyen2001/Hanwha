package com.thuatnguyen.hanwhalife.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.activity.ChiTietHopDongActivity
import com.thuatnguyen.hanwhalife.activity.HomeActivity
import com.thuatnguyen.hanwhalife.adapter.HopDongAdapter
import com.thuatnguyen.hanwhalife.model.BMBH
import com.thuatnguyen.hanwhalife.model.HopDong
import com.thuatnguyen.hanwhalife.model.NDBH
import com.thuatnguyen.hanwhalife.model.SanPhamBoSung
import com.thuatnguyen.hanwhalife.model.SanPhamChinh


class HopDongFragment : Fragment() {
    lateinit var listNDBHID :MutableList<NDBH>
    lateinit var listSPBS :ArrayList<SanPhamBoSung>
    lateinit var lvHopDong:ListView
    lateinit var databaseReference: DatabaseReference
    lateinit var bmbh: BMBH
    //lateinit var ndbh: NDBH
    lateinit var listHD:MutableList<HopDong>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_hop_dong, container, false)

        databaseReference = FirebaseDatabase.getInstance().reference

        //ndbh = (activity as HomeActivity).ndbh
        bmbh = (activity as HomeActivity).bmbh
        lvHopDong = view.findViewById(R.id.lvHopDong)
        listHD = mutableListOf()

        listNDBHID = mutableListOf()
        //listNDBHID.add(ndbh)
        listSPBS = ArrayList()


        val query = databaseReference.child("hopDong").orderByChild("bmbhID").equalTo(bmbh.bmbhID)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    // Lấy dữ liệu từ mỗi snapshot tương ứng và xử lý
                    val hopDong = snapshot.getValue(HopDong::class.java)
                    val sanPhamChinh = snapshot.child("sanPhamChinh").getValue(SanPhamChinh::class.java)
                    val sanPhamBoSung = snapshot.child("sanPhamBoSung").getValue(object : GenericTypeIndicator<List<SanPhamBoSung>>() {})

                    //listNDBHID.add(hopDong?.ndbhID.toString())
                    listHD.add(HopDong(hopDong?.hopDongID,hopDong?.bmbhID,hopDong?.ndbhID,hopDong?.nthID,hopDong?.ngayKy,hopDong?.ngayDenHan,hopDong?.phiBaoHiem,sanPhamChinh,
                        ArrayList(sanPhamBoSung)))
                    listSPBS = ArrayList(sanPhamBoSung)

                }
                val adapter = HopDongAdapter(requireContext(),R.layout.dong_hop_dong,listHD)
                lvHopDong.adapter = adapter
                lvHopDong.setOnItemClickListener { parent, view, position, id ->
                    val intent = Intent(activity, ChiTietHopDongActivity::class.java)
                    intent.putExtra("BMBH",bmbh)
                    //intent.putExtra("NDBH",ndbh)
                    intent.putExtra("HOPDONG",listHD.get(position))
                    startActivity(intent)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Xử lý lỗi nếu có
            }
        })



        return view
    }

}