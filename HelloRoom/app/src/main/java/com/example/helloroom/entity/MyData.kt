package com.example.helloroom.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mydata")
data class MyData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val info: String
)