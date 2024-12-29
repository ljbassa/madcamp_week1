package com.example.madcamp_week1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// DatabaseHelper 클래스: SQLite 데이터베이스를 생성 및 관리하는 클래스
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Capsule.db"

        // 테이블 이름 정의
        const val TABLE_NAME1 = "Capsule"
        const val TABLE_NAME2 = "Gallery"

        // Capsule 테이블 칼럼
        const val CAPSULE_ID = "id"
        const val CAPSULE_TITLE = "CapsuleTitle"
        const val CAPSULE_TEXT = "CapsuleText"
        const val CAPSULE_DATE = "CapsuleDate"
        const val CAPSULE_LOCATION = "CapsuleLocation"
        const val CAPSULE_IMAGE = "CapsuleImage"

        // Gallery 테이블 칼럼
        const val GALLERY_ID = "id"
        const val IMAGE_BYTE = "ImageByte"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Capsule 테이블 생성
        val createCapsuleTable = """CREATE TABLE $TABLE_NAME1 (
            $CAPSULE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $CAPSULE_TITLE TEXT,
            $CAPSULE_TEXT TEXT,
            $CAPSULE_DATE DATE,
            $CAPSULE_LOCATION TEXT,
            $CAPSULE_IMAGE INTEGER,
            FOREIGN KEY($CAPSULE_IMAGE) REFERENCES $TABLE_NAME2($GALLERY_ID)
        )""".trimIndent()

        // Gallery 테이블 생성
        val createGalleryTable = """CREATE TABLE $TABLE_NAME2 (
            $GALLERY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $IMAGE_BYTE BLOB
        )""".trimIndent()

        // 테이블 생성 쿼리 실행
        db.execSQL(createCapsuleTable)
        db.execSQL(createGalleryTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 기존 테이블 삭제 및 다시 생성
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME1")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME2")
        onCreate(db)
    }

    // 이미지 삽입 메서드
    fun insertImageToGallery(image: ByteArray): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(IMAGE_BYTE, image)
        }
        return db.insert(TABLE_NAME2, null, values).also {
            db.close()
        }
    }

    fun deleteImageFromGallery(id: Long): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_NAME2, "$GALLERY_ID=?", arrayOf(id.toString()))
        return result > 0
    }

    // 갤러리 테이블에서 모든 이미지 로드
    fun getAllImagesFromGallery(): List<Pair<Long, ByteArray>> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME2,
            arrayOf(GALLERY_ID, IMAGE_BYTE),
            null,
            null,
            null,
            null,
            null
        )
        val images = mutableListOf<Pair<Long, ByteArray>>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(GALLERY_ID))
                val image = cursor.getBlob(cursor.getColumnIndexOrThrow(IMAGE_BYTE))
                images.add(Pair(id, image))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return images
    }

}
