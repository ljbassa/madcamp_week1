package com.example.madcamp_week1

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week1.databinding.ListItemBinding

class RecyclerViewAdapter(private var itemList: MutableList<Item>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    // ViewHolder 클래스 정의
    class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // ListItemBinding 사용
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 현재 아이템 가져오기
        val currentItem = itemList[position]
        holder.binding.itemText.text = currentItem.text // list_item.xml의 TextView ID
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
