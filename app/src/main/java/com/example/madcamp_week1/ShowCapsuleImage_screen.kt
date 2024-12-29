package com.example.madcamp_week1

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ShowCapsuleImage_screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.capsule_image)

        // ImageView 연결 시키기
        val capsuleImage : ImageView = findViewById(R.id.CapsuleImage)
        val notificationsImage : ImageView = findViewById(R.id.NotificationsImage)
        val cogwheelImage : ImageView = findViewById(R.id.CogwheelImage)

        // 이미지 누르면 capsule_open 으로 전환됨
        capsuleImage.setOnClickListener{
            capsuleImage.setImageResource(R.drawable.capsule_open)
        }

    }
}