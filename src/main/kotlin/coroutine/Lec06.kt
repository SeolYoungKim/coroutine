package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
  printWithThread("runBlocking 실행")
  launch {
    delay(600L)
    printWithThread("A")
  }

  launch {
    delay(500L)
    throw IllegalArgumentException("코루틴 실패!")
  }
  
  printWithThread("runBlocking 종료")
}