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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp


@Composable
fun hideOrShowFloatingActionButton(viewModel: BookViewModel,fullScreen: Boolean){
    if(!fullScreen){
        useFloatingActionButton(viewModel)
    }
}

//TODO 显示右下角菜单按钮
@Composable
fun useFloatingActionButton(viewModel: BookViewModel) {
    var menuExpanded by remember { mutableStateOf(false) }
    var sizeMenuExpanded by remember { mutableStateOf(false) }

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
                        menuExpanded = false
                        // TODO()
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
        }
    }
}