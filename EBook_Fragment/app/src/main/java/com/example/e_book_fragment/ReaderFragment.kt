package com.example.e_book_fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.e_book_fragment.BookFunctions.PageAdapter
import com.example.e_book_fragment.BookFunctions.displayFullBook
import com.example.e_book_fragment.BookFunctions.extractChapters
import com.example.e_book_fragment.BookFunctions.extractChaptersTitles
import com.example.e_book_fragment.BookFunctions.jumpToChapter
import com.example.e_book_fragment.BookFunctions.readBookContent
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ReaderFragment : Fragment() {
    private lateinit var textView: TextView//TODO 显示书籍内容
    private lateinit var scrollView: ScrollView//TODO 用于滚动
    private lateinit var viewPager: ViewPager2//TODO 用于翻页
    private var bookContent: String = ""//TODO 用来读取书籍内容
    private var chapters: List<String> = listOf()//TODO 用来存储章节
    private var chapterPositions = mutableListOf<Int>()//TODO 用来存储章节位置
    private var isVerticalMode = true//TODO 用来判断是否为竖屏模式
    private var pages = listOf<String>()//TODO 用来存储分页后的内容
    private var fullScreen=false//TODO 用来判断是否为全屏模式
    private lateinit var exit: ImageView//TODO 退出按钮
    private lateinit var btnMenu: FloatingActionButton//TODO 右下角菜单按钮
    fun switchScreenMode(){
        if (!fullScreen) {
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        } else {
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        exit.isVisible = !exit.isVisible
        btnMenu.isVisible = !btnMenu.isVisible
        fullScreen = !fullScreen
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reader, container, false)
        textView = view.findViewById(R.id.content)//TODO 显示书籍内容
        scrollView = view.findViewById(R.id.scrollView)//TODO 用于滚动
        viewPager = view.findViewById(R.id.viewPager)//TODO 用于翻页
        btnMenu=view.findViewById(R.id.btnMenu)//TODO 右下角菜单按钮
        exit=view.findViewById(R.id.exit)//TODO 退出按钮
        val bookRawResId = arguments?.getInt("bookRawResId")
        if (bookRawResId != null && bookRawResId != 0) {
            bookContent = readBookContent(bookRawResId, resources)//TODO 读取书籍内容
            chapters = extractChapters(bookContent)//TODO 提取章节
        }
        //TODO 显示书籍内容
        scrollView.visibility = View.VISIBLE
        viewPager.visibility = View.GONE
        displayFullBook(textView, chapters, chapterPositions)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.recyclerView)
        val library = requireActivity().findViewById<TextView>(R.id.library)

        // 确保在切换夜间模式后保持隐藏状态
        recyclerView.visibility = RecyclerView.GONE
        library.visibility = TextView.GONE

        //TODO 设置右下角菜单按钮
        btnMenu.setOnClickListener { view -> showPopupMenu(view) }
        //TODO 退出按钮
        exit.isClickable = true
        exit.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        //TODO 点击进入或退出全屏

        if(scrollView.isVisible){
            textView.isClickable = true
            textView.setOnClickListener {
                switchScreenMode()
            }
        }
    }

    //TODO 显示弹出菜单
    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu, popup.menu)  // 使用 res/menu/menu.xml

        // 监听菜单项点击
        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_font_size -> {
                    showFontSizeDialog()
                    true
                }

                R.id.menu_switch_mode -> {
                    toggleNightMode()
                    true
                }

                R.id.menu_choose_chapter -> {
                    showChapterSelectionDialog()
                    true
                }

                R.id.menu_toggle_reading_mode -> {
                    toggleReadingMode()
                    true
                }

                else -> super.onOptionsItemSelected(item)
            }
        }
        popup.show()
    }

    //TODO 显示字体大小对话框
    private fun showFontSizeDialog() {
        val sizes = arrayOf("小", "中", "大")
        val currentSize = when (textView.textSize / resources.displayMetrics.scaledDensity) {
            16f -> 0
            20f -> 1
            24f -> 2
            else -> 0
        }
        AlertDialog.Builder(requireContext())
            .setTitle("选择字体大小")
            .setSingleChoiceItems(sizes, currentSize) { dialog, which ->
                val newSize = when (which) {
                    0 ->16f
                    1 -> 20f
                    2 ->24f
                    else -> 16f
                }
                textView.textSize=newSize
                if(!isVerticalMode){
                    val adapter=viewPager.adapter as PageAdapter
                    adapter.setTextSize(newSize)
                }
            }
            .show()
    }

    //TODO 切换夜间模式
    private fun toggleNightMode() {
        val nightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        AppCompatDelegate.setDefaultNightMode(
            if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.MODE_NIGHT_NO
            } else {
                AppCompatDelegate.MODE_NIGHT_YES
            }

        )
        requireActivity().recreate()
    }

    //TODO 显示章节选择对话框
    private fun showChapterSelectionDialog() {
        val chapters = extractChapters(bookContent)
        val chapterTitles = chapters.mapIndexed { index, _ -> "章节 ${index + 1}" }.toTypedArray()

        AlertDialog.Builder(requireContext())
            .setTitle("选择章节")
            .setItems(chapterTitles) { _, which ->
                jumpToChapter(which, chapterPositions, scrollView, viewPager, isVerticalMode)
            }
            .show()
    }

    //TODO 简单分页函数，根据最大字符数分页
    private fun paginateContent(content: String, maxCharsPerPage: Int = 1000): List<String> {
        return content.chunked(maxCharsPerPage)
    }


    private val pageClickListener=object:PageAdapter.OnPageClickListener{
        override fun onPageClick(){
            switchScreenMode()
        }
    }

    // TODO 修改切换阅读模式的方法，实现模式切换时保持阅读位置不变
    private fun toggleReadingMode() {
        if (isVerticalMode) {
            // 从竖屏切换到横屏，使用 post 确保 scrollView 的布局已完成
            scrollView.post {
                val totalScroll = scrollView.getChildAt(0).height - scrollView.height
                val verticalRatio =
                    if (totalScroll > 0) scrollView.scrollY.toFloat() / totalScroll else 0f

                // 分页全文内容（你也可以使用更精确的分页方法）
                pages = paginateContent(bookContent)
                viewPager.adapter = PageAdapter(pages, pageClickListener)

                // 等待 viewPager 布局完成后设置当前页
                viewPager.post {
                    val targetPage = (verticalRatio * (pages.size - 1)).toInt()
                    viewPager.setCurrentItem(targetPage, false)
                }
                isVerticalMode = false
                scrollView.visibility = View.GONE
                viewPager.visibility = View.VISIBLE
            }
        } else {
            // 从横屏切换回竖屏，post 确保 viewPager 布局完成后再计算比例
            viewPager.post {
                val currentPage = viewPager.currentItem
                val horizontalRatio =
                    if (pages.isNotEmpty()) currentPage.toFloat() / (pages.size - 1) else 0f
                // 使用 post 确保 scrollView 布局完毕
                scrollView.post {
                    val totalScroll = scrollView.getChildAt(0).height - scrollView.height
                    val targetScrollY = (horizontalRatio * totalScroll).toInt()
                    scrollView.scrollTo(0, targetScrollY)
                }
                isVerticalMode = true
                scrollView.visibility = View.VISIBLE
                viewPager.visibility = View.GONE
            }
        }
    }


//    private fun showChapterSelectionDialog() {
//        val chapterTitles= extractChaptersTitles(chapters).toTypedArray()
//
//        AlertDialog.Builder(requireContext())
//            .setTitle("选择章节")
//            .setItems(chapterTitles) { _, which ->
//                jumpToChapter(which, chapterPositions, scrollView)
//            }
//            .show()
//    }

}