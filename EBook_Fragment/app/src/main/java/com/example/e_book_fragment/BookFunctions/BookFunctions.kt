package com.example.e_book_fragment.BookFunctions


import android.view.ViewTreeObserver
import android.widget.ScrollView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2

fun displayFullBook(textView: TextView, chapters: List<String>,chapterPositions: MutableList<Int>) {
    textView.text = chapters.joinToString("\n\n\n\n")
    textView.viewTreeObserver.addOnGlobalLayoutListener(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            textView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            calculateChapterPositions(chapterPositions, textView, chapters)
        }
    })
}
fun calculateChapterPositions(chapterPositions: MutableList<Int>, textView: TextView, chapters: List<String>) {
    chapterPositions.clear()
    for (i in chapters.indices) {
        val startOffset = textView.text.indexOf(chapters[i])
        if (startOffset != -1) {
            val startLine = textView.layout.getLineForOffset(startOffset)
            val top = textView.layout.getLineTop(startLine)
            chapterPositions.add(top)
        }
    }
}
fun jumpToChapter(chapterIndex: Int, chapterPositions: List<Int>, scrollView: ScrollView,viewPager: ViewPager2,isVerticalMode:Boolean) {
    if (chapterIndex in chapterPositions.indices) {
        val targetPosition = chapterPositions[chapterIndex]
        if(isVerticalMode){
            scrollView.post {
                scrollView.smoothScrollTo(0, targetPosition)
            }
        }
        else{
            viewPager.post {
                viewPager.setCurrentItem(chapterIndex, false)
            }
        }
    }
}
fun readBookContent(resId: Int,resources:android.content.res.Resources): String {
    val inputStream = resources.openRawResource(resId)
    return inputStream.bufferedReader().use { it.readText() }
}
fun extractChapters(content: String): List<String> {
    return content.split("\n\n\n\n")
}
fun extractChaptersTitles(chapters:List<String>):List<String>{
    val titles= mutableListOf<String>()
    for(chapter in chapters){
        val lines=chapter.lines().dropWhile { it.isBlank() }
        val title=lines.firstOrNull{it.isNotBlank()}?.trim()
        if(title!=null){
            titles.add(title)
        }
    }
    return titles
}