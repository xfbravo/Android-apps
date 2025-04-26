package com.example.ebook

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.ebook.db.BookDatabase
import kotlinx.coroutines.launch


@Composable
fun HideOrShowFloatingActionButton(viewModel: BookViewModel,fullScreen: Boolean){
    if(!fullScreen){
        UseFloatingActionButton(viewModel)
    }
}

//TODO 显示右下角菜单按钮
@Composable
fun UseFloatingActionButton(viewModel: BookViewModel) {
    var menuExpanded by remember { mutableStateOf(false) }
    var sizeMenuExpanded by remember { mutableStateOf(false) }
    var chapterMenuExpanded by remember { mutableStateOf(true) }
    var chapterTitles by remember { mutableStateOf<List<Pair<Int,String>>>(emptyList()) }

    val context= LocalContext.current
    val scope= rememberCoroutineScope()
    val db= remember { BookDatabase.getInstance(context) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        // 主菜单按钮作为第一个 DropdownMenu 的锚点
        Box(modifier = Modifier
            .padding(50.dp)
        ) {
            FloatingActionButton(
                onClick = { menuExpanded = !menuExpanded },
                modifier = Modifier.offset(x = (-8).dp, y = (-8).dp)
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
            }
            //主菜单
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                offset = DpOffset(0.dp, (-10).dp)  // 可调整偏移
            ) {
                DropdownMenuItem(
                    text = { Text("改变字体大小") },
                    onClick = {
                        sizeMenuExpanded = true
                    }
                )
                DropdownMenuItem(
                    text = { Text("夜间模式") },
                    onClick = {
                        menuExpanded = false
                        viewModel.toggleNightMode()
                    }
                )
                DropdownMenuItem(
                    text = { Text("设置书签") },
                    onClick = {
                        menuExpanded = false
                        // TODO()
                    }
                )
                DropdownMenuItem(
                    text = { Text("切换章节") },
                    onClick = {
                        chapterMenuExpanded=true
                        scope.launch {
                            val dao=db.bookContentDao()
                            val bookId=viewModel.currentBookId.value?:return@launch
                            val infos=dao.getAllChapterTitles(bookId)
                            chapterTitles=infos.map{it.chapterIndex to it.title}//转化成Pair
                        }
                    }
                )
                DropdownMenuItem(
                    text = { Text("选择书签") },
                    onClick = {
                        menuExpanded = false
                        // TODO()
                    }
                )
            }

            // 第二个菜单锚定在第一个按钮的下面，通过 offset 控制位置
            DropdownMenu(
                expanded = sizeMenuExpanded,
                onDismissRequest = {
                    sizeMenuExpanded = false
                    menuExpanded = false
                },
                offset = DpOffset(-(165).dp, (-100).dp)  // 明显下移
            ) {
                DropdownMenuItem(
                    text = { Text("小") },
                    onClick = {
                        viewModel.updateFontSize(16f)
                        sizeMenuExpanded = false
                        menuExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("中") },
                    onClick = {
                        viewModel.updateFontSize(20f)
                        sizeMenuExpanded = false
                        menuExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("大") },
                    onClick = {
                        viewModel.updateFontSize(24f)
                        sizeMenuExpanded = false
                        menuExpanded = false
                    }
                )
            }
            DropdownMenu(
                expanded = chapterMenuExpanded,
                onDismissRequest = {
                    sizeMenuExpanded = false
                    menuExpanded = false
                },
                offset = DpOffset(-(165).dp, (-100).dp)  // 明显下移
            ){
                chapterTitles.forEach { (index,title)->
                    DropdownMenuItem(
                        text={Text(title)},
                        onClick = {
                            viewModel.requestJumpToChapter(index)
                            chapterMenuExpanded = false
                            menuExpanded = false
                        }
                    )
                }
            }
        }
    }
}