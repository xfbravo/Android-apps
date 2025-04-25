package com.example.helloroom

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.helloroom.dao.MyDataDao
import com.example.helloroom.db.MyDatabase
import com.example.helloroom.helper.DbHelper
import com.example.helloroom.helper.showLog
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class MyDatabaseTest {
    private lateinit var myDataDao: MyDataDao
    private lateinit var db: MyDatabase

    @Before
    fun createDb() {
        //获取用于测试的上下文对象
        val context = InstrumentationRegistry
            .getInstrumentation().targetContext
        //实例化Room数据库对象，在单元测试时，通常使用内存数据库
        db = Room.inMemoryDatabaseBuilder(
            context, MyDatabase::class.java
        ).build()
        //获取数据存取对象
        myDataDao = db.myDataDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
    @Test
    fun addTest() {
        val mydata = DbHelper.createExampleMyData()
        myDataDao.add(mydata)
        showLog("$mydata 已插入")
        val objFromDb = myDataDao.findById(mydata.id)
        showLog("从数据库中提取的记录：$objFromDb")
        Assert.assertNotNull(objFromDb)
    }
}

