package com.example.ebook

import Book
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ebook.ui.theme.EBookTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color

class BookActivity : ComponentActivity() {
    private var fullScreen by mutableStateOf(false)
    private val bookViewModel: BookViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val book= intent.getParcelableExtra<Book>("book")//TODO 获取书籍信息
        enableEdgeToEdge()
        setContent {
            EBookTheme {
                if(!fullScreen){showCustomToast(this,"${book?.title}")}
                BookContent(book = book!!,bookViewModel, toggleFullScreen = {
                    fullScreen=!fullScreen
                    switchFullScreenMode(this,fullScreen,book.title)//TODO 进入全屏模式
                })//TODO 显示书籍信息
                hideOrShowFloatingActionButton(bookViewModel,fullScreen)//TODO 显示右下角菜单按钮
            }
        }
    }
}
//TODO 获取书籍内容并显示
@Composable
fun BookContent(book: Book,viewModel: BookViewModel,toggleFullScreen: () -> Unit = {}) {
    val bookContent=readBookContent(book.rawResId, LocalContext.current.resources)
    val fontSize= viewModel.fontSize.observeAsState(20f)
    val isNightMode= viewModel.isNightMode.observeAsState(false)
    val backgroundColor=if(isNightMode.value) Color.Black else Color.White
    val textColor=if(isNightMode.value) Color.White else Color.Black
    ScrollableBookContent(bookContent, fontSize.value,backgroundColor,textColor, toggleFullScreen)//TODO 显示书籍内容
}


//TODO 显示书籍内容
@Composable
fun ScrollableBookContent(
    content:String,
    fontSize: Float,
    backgroundColor: Color,
    textColor: Color,
    toggleFullScreen: () -> Unit
)
{
    val scrollState= rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(5.dp)
            .verticalScroll(scrollState)
            .clickable { toggleFullScreen() }
    ){
        Text(
            text = content,
            fontSize = fontSize.sp,
            color = textColor,
            modifier = Modifier.padding(5.dp)
        )
    }
}

//TODO 读取书籍内容
fun readBookContent(resId: Int,resources:android.content.res.Resources): String {
    val inputStream = resources.openRawResource(resId)
    return inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }
}

