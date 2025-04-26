package com.example.ebook

import Book
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ebook.ui.theme.EBookTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.example.ebook.db.BookDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BookActivity : ComponentActivity() {
    private var fullScreen by mutableStateOf(false)
    private val bookViewModel: BookViewModel by viewModels()
    private val db: BookDatabase by lazy {
        BookDatabase.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val book= intent.getParcelableExtra<Book>("book")//TODO 获取书籍信息
        enableEdgeToEdge()
        setContent {
            EBookTheme {
                if(!fullScreen){showCustomToast(this,"${book?.title}")}
                BookTextReader(book?.title.hashCode(),0,bookViewModel,toggleFullScreen = {
                    fullScreen=!fullScreen
                    switchFullScreenMode(this,fullScreen, book?.title)//TODO 进入全屏模式
                })//TODO 显示书籍信息
                HideOrShowFloatingActionButton(bookViewModel,fullScreen)//TODO 显示右下角菜单按钮
            }
        }
    }
}
@Composable
fun BookTextReader(bookId:Int,startChapter:Int = 1,viewModel: BookViewModel,toggleFullScreen: () -> Unit){
    val context=LocalContext.current
    val dao=remember{BookDatabase.getInstance(context).bookContentDao()}
    val scope= rememberCoroutineScope()

    val chapterIndices=remember { mutableStateListOf<Int>() }
    val chapterContentMap= remember { mutableStateMapOf<Int, String>() }
    val listState=rememberLazyListState()
    val fontSize= viewModel.fontSize.observeAsState(20f)
    val isNightMode= viewModel.isNightMode.observeAsState(false)
    val backgroundColor= if (isNightMode.value == true) Color.Black else Color.White
    val textColor= if (isNightMode.value == true) Color.White else Color.Black

    LaunchedEffect(Unit) {
        val range=(startChapter-1)..(startChapter+1)
        range.forEach { index ->
            if(index>=0&&!chapterIndices.contains(index)){
                dao.getChapterContent(bookId,index)?.let{chapter->
                    chapterIndices.add(index)
                    chapterContentMap[index]=chapter.content
                }
            }
        }
        chapterIndices.sort()
    }
    LazyColumn (
        state=listState,
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .clickable { toggleFullScreen() }
    ){
        itemsIndexed (chapterIndices){index,chapterIndex ->
            val content=chapterContentMap[chapterIndex]?:"加载中..."

            Text(
                text = content,
                fontSize= fontSize.value.sp,
                modifier = Modifier.padding(15.dp),
                color = textColor,
            )

            if(index==chapterIndices.lastIndex){
                LaunchedEffect(index) {
                    val nextIndex=chapterIndex+1
                    if(!chapterIndices.contains(nextIndex)){
                        dao.getChapterContent(bookId,nextIndex)?.let{chapter->
                            chapterIndices.add(nextIndex)
                            chapterContentMap[nextIndex]=chapter.content
                            chapterIndices.sort()
                        }
                    }
                }
            }
            if(index==0){
                LaunchedEffect(index) {
                    val previousIndex=chapterIndex-1
                    if(previousIndex>=0&&!chapterIndices.contains(previousIndex)){
                        dao.getChapterContent(bookId,previousIndex)?.let{chapter->
                            chapterIndices.add(previousIndex)
                            chapterContentMap[previousIndex]=chapter.content
                            chapterIndices.sort()
                            scope.launch{
                                delay(100)
                                listState.scrollToItem(index+1)
                            }
                        }
                    }
                }
            }
            val jumpChapter by viewModel.jumpChapter.observeAsState(null)
            LaunchedEffect(jumpChapter) {
                jumpChapter?.let{index->
                    if(!chapterIndices.contains(index)){
                        dao.getChapterContent(bookId,index)?.let{
                            chapterIndices.add(index)
                            chapterContentMap[index]=it.content
                            chapterIndices.sort()
                        }
                    }
                    delay(50)
                    listState.scrollToItem(chapterIndices.indexOf(index))
                    viewModel.requestJumpToChapter(null)
                }
            }
        }
    }
}
