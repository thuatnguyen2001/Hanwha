package com.thuatnguyen.hanwhalife.fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.activity.DieuChinhCaNhanActivity
import com.thuatnguyen.hanwhalife.activity.HomeActivity
import com.thuatnguyen.hanwhalife.adapter.ImageAdapter
import com.thuatnguyen.hanwhalife.model.Person
import java.util.UUID

class DieuChinhCaNhan4Fragment : Fragment() {
    lateinit var btnUpload : LinearLayout
    lateinit var btnBack : LinearLayout
    lateinit var btnNext : LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var storage: FirebaseStorage
    private val imageUris: MutableList<Uri> = mutableListOf()
    lateinit var cccd:String
    lateinit var ngayCap:String
    lateinit var noiCap:String
    lateinit var cccdCu:String
    lateinit var databaseReference: DatabaseReference
    private var homeActivity: DieuChinhCaNhanActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DieuChinhCaNhanActivity) {
            homeActivity = context
        } else {
            throw RuntimeException("$context must be MainActivity")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dieu_chinh_ca_nhan4, container, false)

        btnUpload = view.findViewById(R.id.btnUpload)
        btnBack = view.findViewById(R.id.btnBack)
        btnNext = view.findViewById(R.id.btnNext)
        storage = Firebase.storage
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        databaseReference = FirebaseDatabase.getInstance().reference
        imageAdapter = ImageAdapter(imageUris)
        recyclerView.adapter = imageAdapter
        cccd = arguments?.getString("CMND")!!
        ngayCap = arguments?.getString("NGAYCAP")!!
        noiCap = arguments?.getString("NOICAP")!!
        cccdCu = arguments?.getString("CMNDCU")!!
        btnUpload.setOnClickListener {
            openFileChooser()
        }
        btnNext.setOnClickListener {
            uploadImages()
            val dataMap = hashMapOf(
                "CCCD: " to cccd,
                "Ngày cấp: " to ngayCap,
                "Nơi cấp: " to noiCap,
                "status: " to "false"
            )
            //homeActivity?.updateMyString(cccdCu)
            databaseReference.child("ThayDoiCCCD").child(cccdCu).setValue(dataMap)
            val intent = Intent(activity, HomeActivity::class.java)
            intent.putExtra("CCCDCU",cccdCu)
            startActivity(intent)
            closeAllFragmentsAndActivityFromFragment(this)
        }

        return view
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Pictures"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            if (data.clipData != null) {
                val clipData = data.clipData
                for (i in 0 until clipData!!.itemCount) {
                    val imageUri = clipData.getItemAt(i).uri
                    imageUris.add(imageUri)
                }
            } else if (data.data != null) {
                val imageUri = data.data!!
                imageUris.add(imageUri)
            }
            imageAdapter.notifyDataSetChanged()
        }
    }


    private fun uploadImages() {
        for (uri in imageUris) {
            val storageRef = storage.reference
            val imageRef = storageRef.child("${cccd}/${UUID.randomUUID()}.jpg")

            imageRef.putFile(uri)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Upload successful", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Upload failed: $e", Toast.LENGTH_SHORT).show()
                }
        }

    }


    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    fun closeAllFragmentsAndActivityFromFragment(fragment: Fragment) {
        val activity = fragment.activity
        if (activity is AppCompatActivity) {
            val fragmentManager = activity.supportFragmentManager
            for (i in 0 until fragmentManager.backStackEntryCount) {
                fragmentManager.popBackStack()
            }
            activity.finish()
        }
    }
}
