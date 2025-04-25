package com.example.helloroom.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.helloroom.dao.MyDataDao
import com.example.helloroom.entity.MyData

@Database(
    entities = [MyData::class],
    version = 1,
    exportSchema = false
)
abstract class MyDatabase: RoomDatabase(){
    abstract fun myDataDao(): MyDataDao
}