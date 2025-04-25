package com.example.helloroom.helper
import android.util.Log
import com.example.helloroom.entity.MyData
import kotlin.random.Random

object DbHelper {
    //创建一个用于测试的数据实体类
    fun createExampleMyData(): MyData {
        return MyData(
            id = System.currentTimeMillis().toInt(),
            info = "Hello Room ${Random.nextInt(1000)}"
        )
    }
}

//在Logcat面板中输出相关信息
const val TAG = "Room"
fun showLog(info: Any) {
    Log.d(TAG, info.toString())
}
