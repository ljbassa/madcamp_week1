package com.example.madcamp_week1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RecyclerView 초기화
        val recyclerView: RecyclerView = findViewById(R.id.rv_list)

        // 리스트 데이터 생성
        val itemList = mutableListOf(
            Item("몰입캠프에서의 추억"),
            Item("가족 여행"),
            Item("2024 크리스마스"),
            Item("고등학교 졸업식"),
            Item("강릉 우정 여행")
        )

        // RecyclerView 설정
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerViewAdapter(itemList)

        // Divider 추가
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
    }
}
