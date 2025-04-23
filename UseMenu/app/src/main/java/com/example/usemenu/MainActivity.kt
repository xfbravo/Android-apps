package com.example.usemenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toolbar
import java.lang.reflect.Method
class MainActivity : AppCompatActivity() {

    lateinit var tvInfo:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInfo=findViewById(R.id.tvInfo)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        //设置在任何情况下均显示图标
        setIconEnable(menu!!,true);
        //加载菜单资源
        menuInflater.inflate(R.menu.mymenu,menu)
        return true
    }

    //Hack手段，使用反射打开“显示菜单项图标”功能
    private fun setIconEnable(menu: Menu, enable: Boolean) {
        try {
            val clazz =
                Class.forName("androidx.appcompat.view.menu.MenuBuilder")
            val m: Method = clazz.getDeclaredMethod(
                "setOptionalIconsVisible",
                Boolean::class.javaPrimitiveType
            )
            m.isAccessible = true
            //下面传入参数
            m.invoke(menu, enable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when(item.itemId){
            R.id.mnuAbout->tvInfo.text="About"
            R.id.mnuEdit->tvInfo.text="Edit"
            R.id.mnuExit->finish()
            R.id.mnuNew->tvInfo.text="New"
            R.id.mnuOpen->tvInfo.text="Open"
            R.id.mnuSave->tvInfo.text="Save"
        }
        return true
    }
}