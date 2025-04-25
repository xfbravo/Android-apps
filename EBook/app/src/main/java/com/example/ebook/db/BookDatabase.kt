package com.example.ebook.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ebook.dao.BookContentDao
import com.example.ebook.entity.BookContentEntity

@Database(
    entities = [BookContentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BookDatabase :RoomDatabase() {
    abstract fun bookContentDao():BookContentDao

    companion object{
        @Volatile private var INSTANCE: BookDatabase? = null

        fun getInstance(context: Context): BookDatabase {
            return INSTANCE?:synchronized(this) {
                INSTANCE?:Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java
                    "book_database.db"
                ).build().also{
                    INSTANCE = it
                }}
            }
        }
    }
}