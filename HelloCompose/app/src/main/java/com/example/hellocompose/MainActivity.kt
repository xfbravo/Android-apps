package com.example.hellocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.example.hellocompose.ui.theme.HelloComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelloComposeTheme {
                RememberByKeyDemo()
            }
        }
    }
}

@Composable
fun RememberByKeyDemo(){
    val rememberKey= remember { mutableStateOf(0) }
    var counter=remember(rememberKey.value){
        mutableStateOf(0)
    }
    Column (
        modifier=Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        if(counter.value==0){
            Text(
                text="点击第一个按钮开始计数，\n点击第二个按钮重新开始",
            )
        }
        else{
            Text(
                text="当前计数值：${counter.value}",
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick={
            counter.value++
        }){
            Text("增加计数值")
        }
        Spacer(modifier = Modifier.height(5.dp))
        Button(onClick={
            rememberKey.value++
        }){
            Text("修改Key值")
        }
    }
}

@Composable
fun MyApp2(modifier: Modifier= Modifier){
    Surface(
        modifier=modifier,
        color= MaterialTheme.colorScheme.background
    ){
        ClickButton()
    }

}
@Composable
fun ClickButton(){
    var info by rememberSaveable { mutableStateOf("请点击按钮，程序统计单击次数") }
    var count by rememberSaveable { mutableStateOf(0) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick={
                info="我被单击了${++count} 次"
            }
        ){
            Text(
                text="click me!",
                fontSize = 30.sp,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text=info,
            fontSize = 20.sp,
            color = Color.Blue
        )

    }
}

@Composable
fun MyApp(info:String,content:@Composable () ->Unit){
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text=info,
            textAlign = TextAlign.Center,
        )
        content()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        textAlign = TextAlign.Center,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HelloComposeTheme {
        HelloApp()
    }
}
@Composable
fun HelloApp(){
    var userInput by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(value = userInput, onValueChange = {
            userInput= it
        })
        Spacer(modifier = Modifier.height(20.dp))
        Greeting(name = userInput)
    }
}

