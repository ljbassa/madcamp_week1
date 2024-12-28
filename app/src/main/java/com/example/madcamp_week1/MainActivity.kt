package com.example.madcamp_week1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.madcamp_week1.ui.theme.Madcampweek1Theme
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RecyclerView 연결
        val recyclerView: RecyclerView = findViewById(R.id.rv_list)

        // 데이터 리스트 생성
        val itemList = mutableListOf(
            Item("첫 번째 텍스트"),
            Item("두 번째 텍스트"),
            Item("세 번째 텍스트"),
            Item("네 번째 텍스트"),
            Item("다섯 번째 텍스트")
        )

        // RecyclerView 설정
        recyclerView.layoutManager = LinearLayoutManager(this) // 세로 방향으로 설정
        recyclerView.adapter = RecyclerViewAdapter(itemList)   // 어댑터 연결
    }
}

