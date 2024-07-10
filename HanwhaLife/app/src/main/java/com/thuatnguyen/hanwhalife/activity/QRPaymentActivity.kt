package com.thuatnguyen.hanwhalife.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import com.thuatnguyen.hanwhalife.R
import com.thuatnguyen.hanwhalife.model.HopDong
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class QRPaymentActivity : AppCompatActivity() {
    lateinit var imgQR : ImageView
    lateinit var btnDownload : Button
    lateinit var btnClose : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_qrpayment)

        imgQR = findViewById(R.id.imgQR)
        val hopDong = intent.getParcelableExtra<HopDong>("HopDong")
        btnDownload = findViewById(R.id.btnDownload)
        btnClose = findViewById(R.id.btnClose)

        // Kiểm tra và yêu cầu quyền WRITE_EXTERNAL_STORAGE nếu cần thiết
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_STORAGE_PERMISSION
            )
        }

        val amount = hopDong?.phiBaoHiem
        val shd = hopDong?.hopDongID
        val qrCodeUrl = "https://api.vietqr.io/image/970422-30707688-MXUXzZm.jpg?accountName=NGUYEN%20VU%20THUAT&amount="+amount+"&addInfo="+shd+"";

        // Sử dụng Picasso để tải ảnh từ URL và hiển thị trong ImageView
        Picasso.get().load(qrCodeUrl).into(imgQR)

        btnDownload.setOnClickListener {
            saveImageToStorage()
        }

        btnClose.setOnClickListener {
            finish()
        }
    }

    private fun saveImageToStorage() {
        // Kiểm tra nếu ImageView không có ảnh
        if (imgQR.drawable == null) {
            Toast.makeText(this, "Không có ảnh để tải xuống", Toast.LENGTH_SHORT).show()
            return
        }

        // Lấy ảnh từ ImageView
        val bitmap = (imgQR.drawable as BitmapDrawable).bitmap

        // Lưu ảnh vào bộ nhớ của thiết bị
        val currentTimeMillis = System.currentTimeMillis()
        val filename = currentTimeMillis.toString()+".jpg"
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), filename)
        try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            Toast.makeText(this, "Ảnh đã được lưu vào thư viện ảnh", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Lỗi khi lưu ảnh", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val REQUEST_STORAGE_PERMISSION = 101
    }
}