package com.example.ebook

import Book
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ebook.ui.theme.EBookTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EBookTheme {
                Library()
            }
        }
    }
}

@Composable
fun Library() {
    val context= LocalContext.current
    val books=listOf(//TODO 书籍列表
        Book("What if", R.drawable.what_if, R.raw.book1),
        Book("Loving Hot Family", R.drawable.lovinghotfamily_bookcover, R.raw.lovinghotfamily),
        Book("球状闪电", R.drawable.lightning_ball, R.raw.lightning_ball),
        Book("乡村教师", R.drawable.country_teacher, R.raw.country_teacher),
    )

    Column {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Library",
            fontSize = 50.sp,
        )
        BookList(books = books) { book ->
            val intent = Intent(context, BookActivity::class.java)
            val newBook = Book(//TODO 传递书籍信息
                title = book.title,
                coverResId = book.coverResId,
                rawResId = book.rawResId
            )
            intent.putExtra("book", newBook)
            context.startActivity(intent)
        }
    }
}

@Composable
fun BookList(books:List<Book>,onItemClick:(Book)-> Unit){
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        items(books.size){ index ->
            val book = books[index]
            BookItem(book = book, onItemClick= onItemClick)
        }
    }
}

@Composable
fun BookItem(book: Book,onItemClick:(Book)->Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(book) },
        shape=RoundedCornerShape(8.dp),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(4.dp)
    ){
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id=book.coverResId),
                contentDescription = book.title,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text=book.title,
                fontSize = 18.sp,
                color=Color.Black
            )
        }

    }
}