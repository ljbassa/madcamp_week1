package com.example.madcamp_week1

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GalleryScreen : ComponentActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhotoAdapter
    private val photoList = mutableListOf<Pair<Long, ByteArray>>() // Long은 GALLERY_ID, ByteArray는 이미지 데이터

    private val requestGalleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri>? ->
        uris?.forEach { uri ->
            val imageBlob = getImageBlobFromUri(uri)
            if (imageBlob != null) {
                val galleryId = databaseHelper.insertImageToGallery(imageBlob) // DatabaseHelper에 삽입 메서드 추가 필요
                if (galleryId != -1L) {
                    Log.d("GalleryScreen", "저장된 Gallery ID: $galleryId")
                    photoList.add(Pair(galleryId, imageBlob))
                    adapter.notifyItemInserted(photoList.size - 1)
                } else {
                    Log.e("GalleryScreen", "Gallery 테이블에 삽입 실패")
                }
            } else {
                Log.e("GalleryScreen", "URI 변환 실패: $uri")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_list) // gallery_list.xml 연결

        databaseHelper = DatabaseHelper(this)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = PhotoAdapter(photoList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        checkStoragePermission()
        loadPhotosFromDatabase()

        val selectPhotosButton = findViewById<Button>(R.id.selectPhotosButton)
        selectPhotosButton.setOnClickListener {
            requestGalleryLauncher.launch("image/*")
        }
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }
    }

    private fun loadPhotosFromDatabase() {
        val photos = databaseHelper.getAllImagesFromGallery() // DatabaseHelper에 데이터 읽기 메서드 추가 필요
        if (photos.isNotEmpty()) {
            photoList.clear()
            photoList.addAll(photos)
            adapter.notifyDataSetChanged()
        }
    }

    private fun getImageBlobFromUri(uri: Uri): ByteArray? {
        return try {
            contentResolver.openInputStream(uri)?.use { it.readBytes() }
        } catch (e: Exception) {
            Log.e("GalleryScreen", "Error: ${e.message}")
            null
        }
    }
}
