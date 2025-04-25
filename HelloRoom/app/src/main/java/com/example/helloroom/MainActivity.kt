package com.example.helloroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import com.example.helloroom.db.MyDatabase
import com.example.helloroom.entity.MyData
import com.example.helloroom.helper.DbHelper
import com.example.helloroom.helper.showLog
import com.example.helloroom.ui.theme.HelloRoomTheme
import kotlin.concurrent.thread


class MainActivity : ComponentActivity() {
    private val db: MyDatabase by lazy{
        showLog("Database created")
        Room.databaseBuilder(
            this, MyDatabase::class.java,
            "mydata.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        addToDb()
        setContent {
            HelloRoomTheme {
                Greeting(db)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

    fun addToDb(){
        thread{
            val obj: MyData = DbHelper.createExampleMyData()
            db.myDataDao().add(obj)
            val allData= db.myDataDao().getAll()
            println("All data: $allData")
        }
    }
}

@Composable
fun Greeting(db: MyDatabase) {
    var info= " "
    val allData = db.myDataDao().getAll().collectAsState(initial = emptyList()).value
    allData.forEach {
        info+= "${it.info}\n"
    }
    Column(
        modifier = Modifier
            .padding(50.dp)
            .fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Button(
            onClick = {
                val newData= DbHelper.createExampleMyData()
                thread{
                    db.myDataDao().add(newData)
                }
                info+= "${newData.info}\n"
            },
            modifier = Modifier.padding(24.dp)
        ) {
            Text(text = "Insert Data")
        }
        Button(
            onClick = {
                thread{
                    db.myDataDao().deleteAll()
                }
                info= " "
            },
            modifier = Modifier.padding(24.dp)
        ) {
            Text(text = "Delete All Data")
        }

        Text(
            text = info,
            modifier = Modifier
                .padding(24.dp)
        )
    }
}


