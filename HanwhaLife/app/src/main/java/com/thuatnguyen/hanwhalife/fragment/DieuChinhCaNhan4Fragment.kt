package com.thuatnguyen.hanwhalife.fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import com.google.android.gms.cast.framework.media.ImagePicker
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
            selectImages()
        }
        btnNext.setOnClickListener {
            uploadImages()
        }

        return view
    }

    private fun selectImages() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Pictures"), PICK_IMAGES_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGES_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.let {
                val clipData = it.clipData
                if (clipData != null) {
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        imageUris.add(uri)
                    }
                } else {
                    it.data?.let { uri ->
                        imageUris.add(uri)
                    }
                }
                imageAdapter.notifyDataSetChanged()
            }
        }
    }


    private fun uploadImages() {
        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.reference

        val tasks = mutableListOf<com.google.android.gms.tasks.Task<Uri>>()

        for (uri in imageUris) {
            val fileName = UUID.randomUUID().toString()
            val fileReference = storageReference.child("$cccdCu/$fileName")

            val uploadTask = fileReference.putFile(uri)
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    fileReference.downloadUrl
                }

            tasks.add(uploadTask)
        }

        com.google.android.gms.tasks.Tasks.whenAllComplete(tasks)
            .addOnCompleteListener {
                Toast.makeText(requireContext(), "All images uploaded", Toast.LENGTH_SHORT).show()
                val dataMap = hashMapOf(
                    "CCCD: " to cccd,
                    "Ngày cấp: " to ngayCap,
                    "Nơi cấp: " to noiCap,
                    "status: " to "false"
                )
                //homeActivity?.updateMyString(cccdCu)
                databaseReference.child("ThayDoiCCCD").child(cccdCu).setValue(dataMap)
                updateActivityString("New String from Fragment")
                closeAllFragmentsAndActivityFromFragment(this)
                // Xử lý logic khi tất cả ảnh đã tải lên thành công
                 // Ví dụ: Đóng activity hiện tại
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to upload some images", Toast.LENGTH_SHORT).show()
                // Xử lý logic khi một hoặc nhiều ảnh tải lên thất bại
            }
    }


    companion object {
        private const val PICK_IMAGES_REQUEST = 1
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

    private fun updateActivityString(newString: String) {
        val intent = Intent("com.example.UPDATE_STRING")
        intent.putExtra("new_string", cccdCu)
        context?.sendBroadcast(intent)
    }

}
