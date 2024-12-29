package com.example.madcamp_week1

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class PhotoAdapter(private val photos: List<Pair<Long, ByteArray>>) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (_, imageBlob) = photos[position]

        // BLOB 데이터를 Bitmap으로 변환하여 ImageView에 설정
        val bitmap = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.size)
        holder.imageView.setImageBitmap(bitmap)
    }

    override fun getItemCount(): Int = photos.size
}