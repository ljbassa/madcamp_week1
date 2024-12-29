package com.example.madcamp_week1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp_week1.DatabaseHelper.Companion.CAPSULE_TITLE

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RecyclerView 초기화
        val recyclerView: RecyclerView = findViewById(R.id.rv_list)

        // 리스트 데이터 생성
        val itemList = getCapsuleTitles()

        // RecyclerView 설정
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerViewAdapter(itemList)

        // Divider 추가
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
    }

    private fun getCapsuleTitles(): MutableList<Item> {
        val itemList = mutableListOf<Item>()
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase
        val query = "SELECT $CAPSULE_TITLE AS 캡슐_이름 FROM ${DatabaseHelper.TABLE_NAME1} GROUP BY $CAPSULE_TITLE"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CAPSULE_TITLE))
                itemList.add(Item(title))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return itemList
    }
}
