package coroutine

import kotlinx.coroutines.*

fun main() {
  printWithThread("main 실행")
  lec05Example6()
  printWithThread("main 종료")
}

fun lec05Example6(): Unit = runBlocking {
  printWithThread("runBlocking 실행")

  val exceptionHandler = CoroutineExceptionHandler { cause, throwable ->
    printWithThread("CoroutineExceptionHandler 실행")
    printWithThread("예외가 발생한 CoroutineContext 정보: $cause")
    printWithThread("발생한 예외: $throwable")
    throw throwable
  }

  val job = CoroutineScope(Dispatchers.Default).async(exceptionHandler) {
    printWithThread("launch 실행 & 예외 발생")
    throw IllegalArgumentException()
  }

  delay(1000L)
}


fun lec05Example5(): Unit = runBlocking {
  val job = launch {
    try {
      throw IllegalArgumentException()
    } catch (e: IllegalArgumentException) {
      printWithThread("정상 종료")
    }
  }
}


fun lec05Example4(): Unit = runBlocking {
  val job = async(SupervisorJob()) {
    throw IllegalArgumentException()
  }

  delay(1_000L)
//  job.await()
}


fun lec05Example3(): Unit = runBlocking {
  printWithThread("runBlocking 실행")
  val job = CoroutineScope(Dispatchers.Default).async {
    printWithThread("async 실행")
    throw IllegalArgumentException()
  }

  delay(100L)
//  job.await()
  printWithThread("runBlocking 종료")
}

fun lec05Example2(): Unit = runBlocking {
  printWithThread("runBlocking 실행")

  val job = CoroutineScope(Dispatchers.Default).launch {
    printWithThread("launch 실행")
    throw IllegalArgumentException()
  }

  delay(100L)
  printWithThread("runBlocking 종료")
}


fun lec05Example1(): Unit = runBlocking {
  printWithThread("runBlocking 실행")
  val job1 = CoroutineScope(Dispatchers.Default).launch {
    delay(1_000L)
    printWithThread("Job 1")
  }

  val job2 = CoroutineScope(Dispatchers.Default).launch {
    delay(1_000L)
    printWithThread("Job 2")
  }
}