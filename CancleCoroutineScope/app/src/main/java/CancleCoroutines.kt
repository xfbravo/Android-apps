import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.coroutines.yield


//suspend fun main(){
//    val job= GlobalScope.launch{
//        repeat(1000){
//            delay(100)
//            println(".")
//        }
//    }
//    delay(2500)
//    job.cancel()
//    job.join()
//    println("\n结束!")
//}

//suspend fun main(){
//    var i =0
//    var job= GlobalScope.launch {
//        repeat(1000){
//            print("${i++}, ")
////            Thread.sleep(1)
//            yield()
//        }
//    }
//    delay(1)
//    job.cancelAndJoin()
//    println("\nfinished at: $i")
//}

//suspend fun main(){
//    var i =0
//    val job = GlobalScope.launch {
//        repeat(1000){
//            if(!isActive){
//                return@launch
//            }
//            print("${i++}, ")
//            Thread.sleep(10)
//        }
//    }
//    delay(100)
//    job.cancelAndJoin()
//    println("\nfinished at: $i")
//}

//private suspend fun useEnsureActive(){
//     var i =0
//     val job= GlobalScope.launch {
//         repeat(1000){
//             ensureActive()
//             print("${i++}, ")
//             Thread.sleep(10)
//         }
//     }
//     delay(100)
//     job.cancelAndJoin()
//        println("\nfinished at: $i")
//}
//suspend fun main(){
//    useEnsureActive()
//}

//处理取消时的异常
//suspend fun main(){
//    var i=0
//    val job= GlobalScope.launch {
//        try{
//            repeat(1000){
//                ensureActive()
//                print("${i++}, ")
//                Thread.sleep(10)
//            }
//        }
//        catch(ex: CancellationException){
//            println("\nCancelled: ${ex.message}")
//        }
//        finally{
//            println("In finally")
//        }
//    }
//    delay(100)
//    job.cancel(CancellationException("for some reason"))
//    job.join()
//    println("\nfinished at: $i")
//}

//suspend fun main(){
//    val job = GlobalScope.launch {
//        var i = 0
//        val result= withTimeoutOrNull(1000){
//            try {
//                repeat(1000){
//                    print("${i++}, ")
//                    delay(10)
//                }
//                1000
//            }
//            catch(ex: CancellationException){
//                println("Timeout!")
//            }
//        }
//        println("\nresult: $result")
//    }
//    job.join()
//
//}

//fun main()= runBlocking {
//    val parent=launch{
//        launch{
//            repeat(100) {
//                println("$it,")
//                delay(1)
//            }
//        }
//        println("parent last statement")
//    }
//    delay(10)
//    parent.cancelChildren()
//    parent.join()
//    println("\nFinished!")
//}

//fun main()=runBlocking {
//    val scopeJob=Job()
//    val scope= CoroutineScope(scopeJob+ Dispatchers.Default)
//    val job1=scope.launch{
//        println("job1 finishes in 100ms")
//        delay(100)
//        println("job1 finished")
//    }
//    val job2=scope.launch{
//        println("Throw a CancellationException in 50 ms")
//        delay(50)
//        println("CancellationException Thrown")
//        throw RuntimeException()
//    }
//    joinAll(job1,job2)
//    println("ScopeJob:$scopeJob")
//    println("job1:$job1")
//    println("job2:$job2")
//}

//fun main()=runBlocking<Unit> {
//    val scope=CoroutineScope(Job())
//    try{
//        doSomethingSupsended()
//    }
//    catch (e: Exception){
//        println("Caught exception: ${e.message}")
//    }
//
//    delay(1000)
//    println("Main function finished")
//}
//
//suspend fun functionThatThrows(){
//    delay(100)
//    println("throwing exception")
//    throw RuntimeException("This is a test exception")
//}
//
//suspend fun doSomethingSupsended(){
//    coroutineScope {
//        launch {
//            throw RuntimeException("This is a test exception")
//        }
//    }
//}

//fun main():Unit=runBlocking {
//    val scope=CoroutineScope(Dispatchers.Default)
//    val handler= CoroutineExceptionHandler {
//        _, exception ->
//        println(exception.message)
//    }
//    scope.launch(handler) {
//        delay(1000)
//        throw Exception("Something wrong")
//    }.join()
//}

//fun main()=runBlocking{
//    val myExceptionHandler= CoroutineExceptionHandler{
//            _, exception ->
//        println("Caught $exception")
//    }
////    val scopeJob=Job()
//    val scopeJob=SupervisorJob()
//    val scope=CoroutineScope(scopeJob+Dispatchers.Default+myExceptionHandler)
//    val job1=scope.launch {
//        println("job1 finishes in 100ms")
//        delay(100)
//        println("job1 finished")
//    }
//    val job2=scope.launch {
//        println("Throw a CancellationException in 50 ms")
//        delay(50)
//        println("CancellationException Thrown")
//        throw RuntimeException()
//    }
//    joinAll(job1,job2)
//    println("ScopeJob:$scopeJob")
//    println("job1:$job1")
//    println("job2:$job2")
//}

fun main()= runBlocking {
    val scopeJob=Job()
    val scope=CoroutineScope(scopeJob+Dispatchers.Default)
    val job1=scope.launch {
        println("job1 finishes in 100ms")
        delay(100)
        println("job1 finished")
    }
    val job2=scope.async {
        println("Throw a CancellationException in 50 ms")
        delay(50)
        println("CancellationException Thrown")
        throw RuntimeException()
    }
    joinAll(job1,job2)
    println("ScopeJob:$scopeJob")
    println("job1:$job1")
    println("job2:$job2")
    try{
        job2.await()
    }
    catch (e: Exception){
        println("Caught exception: ${e.message}")
    }
}
