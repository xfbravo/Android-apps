package com.example.ebook

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookViewModel: ViewModel() {
    //TODO 字体大小
    private val _fontSize= MutableLiveData(20f)
    val fontSize: MutableLiveData<Float>
        get() = _fontSize
    fun updateFontSize(newSize: Float) {
        _fontSize.value = newSize
    }
    //TODO 夜间模式
    private val _isNightMode= MutableLiveData(false)
    val isNightMode: MutableLiveData<Boolean>
        get() = _isNightMode
    fun toggleNightMode(){
        _isNightMode.value = _isNightMode.value?.not()
    }
    //TODO 当前书籍ID,用于菜单查询章节
    val currentBookId= mutableStateOf<Int?>(null)

    //TODO 章节跳转功能
    private val _jumpChapter= MutableLiveData<Int?>(null)
    val jumpChapter: MutableLiveData<Int?>
        get() = _jumpChapter

    fun requestJumpToChapter(chapterIndex: Int?) {
        _jumpChapter.value = chapterIndex
    }
}