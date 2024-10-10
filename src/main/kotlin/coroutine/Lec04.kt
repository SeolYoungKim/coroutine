package coroutine

import kotlinx.coroutines.*


fun main() {
  lec04Example3()
}

fun lec04Example3(): Unit = runBlocking {
  val job = launch(Dispatchers.Default) {
    try {
      delay(1_000L)
    } catch (e: CancellationException) {
      // 아무것도 안한다!
      printWithThread("CancellationException 발생. message: ${e.message}")
    } finally {
      // 필요한 자원을 닫을 수도 있습니다.
    }

    printWithThread("작업이 취소되지 않았음")
  }

  printWithThread("job.cancel() 호출")
  job.cancel()
}


fun lec04Example2(): Unit = runBlocking {
  printWithThread("runBlocking 시작")
  val job = launch(Dispatchers.Default) {
    var i = 1
    var nextPrintTime = System.currentTimeMillis()
    while (i <= 5) {
      if (nextPrintTime <= System.currentTimeMillis()) {
        printWithThread("${i++}번째 출력!")
        nextPrintTime += 1_000L
      }

      if (!isActive) {
        throw CancellationException()  // 작업 취소
      }
    }
  }

//  delay(1000L)
  yield()
  job.cancel()
  printWithThread("runBlocking 종료")
}


fun lec04Example1(): Unit = runBlocking {
  val job1 = launch {
    delay(10)
    printWithThread("Job 1")
  }

  delay(100)
  job1.cancel()
}