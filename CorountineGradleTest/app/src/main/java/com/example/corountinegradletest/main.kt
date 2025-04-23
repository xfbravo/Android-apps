package com.example.corountinegradletest
import kotlinx.coroutines.*
import org.jetbrains.annotations.TestOnly
import java.lang.Thread.sleep
import kotlin.concurrent.thread
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.system.measureTimeMillis

suspend fun coroutine(id:Int, delayTime: Long) {
    val threadName= Thread.currentThread().name
    println("Coroutine $id started, running on thread: $threadName")
    delay(delayTime)
    println("Coroutine $id finished after $delayTime ms")
}

//fun main()=runBlocking{
//    val theadName= Thread.currentThread().name
//    println("Main function started, running on thread: $theadName")
//    launch {
//        coroutine(1,1000)
//    }
//    launch {
//        coroutine(2,800)
//    }
//    println("Main function finished")
//}


//fun main(){
//    println("main thread :${Thread.currentThread().name} started ")
//    Thread{
//        println("sub thread:${Thread.currentThread().name} is running")
//    }.start()
//    println("main thread:${Thread.currentThread().name} finished")
//}

//fun main(){
//    println("main thread :${Thread.currentThread().name} started ")
//    runBlocking{
//        println("runBlocking thread :${Thread.currentThread().name}")
//        GlobalScope.launch{
//            println("GlobalScope thread :${Thread.currentThread().name}")
//            delay(1000)
//            println("GlobalScope thread :${Thread.currentThread().name} finished")
//        }
//        println("runBlocking thread :${Thread.currentThread().name} finished")
//    }
//    println("main thread :${Thread.currentThread().name} finished")
//}

//fun main(){
//    println("main thread :${Thread.currentThread().name} started ")
//    runBlocking{
//        launch{
//            (1..3).forEach {
//                println("$it:subThread[${Thread.currentThread().name}]")
//            }
//        }
//        (1..3).forEach {
//            delay(200)
//            println("$it:fartherThread[${Thread.currentThread().name}]")
//        }
//    }
//}

//fun main(){
//    runBlocking{
//        (1..3).forEach{
//            launch(Dispatchers.IO){
//                delay(300)
//                println("$it:coroutine1 use thread[${Thread.currentThread().name}]")
//            }
//            launch(Dispatchers.Default){
//                delay(100)
//                println("$it:coroutine2 use thread[${Thread.currentThread().name}]")
//            }
//        }
//    }
//}

//fun threadHelloWorld(){
//    thread{
//        sleep(1000)
//        print("world!")
//    }
//    print("hello,")
//}
//
//suspend fun coroutineHelloWorld(){
//    coroutineScope {
//        launch{
//            delay(1000)
//            print("World!")
//        }
//        print("Hello,")
//    }
//}
//suspend fun main(){
//    coroutineHelloWorld()
//    threadHelloWorld()
//}

//fun sleepAndDelay(){
//    println("The thread of this function is :${Thread.currentThread().name}")
//    GlobalScope.launch{
//        delay(1000)
//        println("World!")
//        println("The thread of this coroutine is :${Thread.currentThread().name}")
//    }
//    print("Hello,")
//    Thread.sleep(2000)
//    println("All is printed :${Thread.currentThread().name}")
//
//}
//fun main(){
//    sleepAndDelay()
//    println("Main function finished")
//}

//fun runBlockingThreadInto(){
//    runBlocking{
//        println("before delay:${Thread.currentThread().name}")
//        delay(1000)
//        println("after delay:${Thread.currentThread().name}")
//    }
//    println("before sleep:${Thread.currentThread().name}")
//    Thread.sleep(1000)
//    println("after sleep:${Thread.currentThread().name}")
//}
//fun main()=runBlocking{
//    runBlockingThreadInto()
//    println("Main function finished")
//}


//val SEQUENTIAL_THRESHOLD=5000
//fun compute(array: IntArray,low:Int,high:Int):Long{
//    print("low:$low,high:$high, on thread:${Thread.currentThread().name} ")
//    return if(high-low<SEQUENTIAL_THRESHOLD){
//        (low until high).sumOf { array[it].toLong() }
//    }else{
//        val mid=low+(high-low)/2
//        val left=compute(array,low,mid)
//        val right=compute(array,mid,high)
//        return left+right
//    }
//}
//
//suspend fun coroutineCompute(array: IntArray,low:Int,high:Int):Long{
//    print("low:$low,high:$high, on thread:${Thread.currentThread().name} ")
//    return if(high-low<SEQUENTIAL_THRESHOLD){
//        (low until high).sumOf { array[it].toLong() }
//    }else{
//        val mid=low+(high-low)/2
//        val left=withContext(Dispatchers.Default){compute(array,low,mid)}
//        val right=withContext(Dispatchers.Default){compute(array,mid,high)}
//        return left+right
//    }
//}
//suspend fun main(){
//    val list=mutableListOf<Int>()
//    var limit=20_000_000
//    while(limit>0){
//        list.add(limit--)
//    }
//    var result=0L
//    var time= measureTimeMillis {
//        result=coroutineCompute(list.toIntArray(),0,list.size)
//    }
//    result=0L
//    time=measureTimeMillis{
//        result=coroutineCompute(list.toIntArray(),0,list.size)
//    }
//    println("$result in ${time}ms")
//}

suspend fun main(){
//    val job= CoroutineScope(EmptyCoroutineContext).launch{
//        println("create my own Scope,and launch a coroutine")
//        println("${Thread.currentThread().name}")
//    }
//    job.join()
    useCoroutineScope()
}

suspend fun useCoroutineScope(){
    println("before coroutineScope1")
    coroutineScope {
        for(i in 0 until 10){
            launch{
                delay(1000L-i*10)
                print("@  $i ")
            }
        }
    }
    println("\nafter coroutineScope1")
    println("before coroutineScope2")
    coroutineScope {
        for(i in 11 until 20){
            launch{
                delay(1000L-i*10)
                print("@  $i ")
            }
        }
    }
    println("\nafter coroutineScope2")
}
