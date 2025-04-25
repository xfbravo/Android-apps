package com.example.helloroom.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.helloroom.entity.MyData
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDataDao{
    @Insert
    fun add(myData: MyData)

    @Delete
    fun delete(myData: MyData)

    @Update
    fun update(myData: MyData)

    @Query("SELECT * FROM mydata")
    fun getAll(): Flow<List<MyData>>

    @Query("SELECT * FROM mydata WHERE id = :id")
    fun findById(id: Int): MyData?

    //清空表
    @Query("DELETE FROM mydata")
    fun deleteAll()

}