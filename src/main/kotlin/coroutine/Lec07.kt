package coroutine

import kotlinx.coroutines.*
import java.util.concurrent.Executors

suspend fun main() {
  lec07Example2()
}

fun lec07Example2() {
  // `+`기호를 사용해서 여러 Element들을 조합할 수 있다
  CoroutineName("나만의 코루틴") + SupervisorJob()
  CoroutineName("나만의 코루틴") + Dispatchers.Default

  val threadPool = Executors.newSingleThreadExecutor()
  CoroutineScope(threadPool.asCoroutineDispatcher()).launch {
    printWithThread("새로운 코루틴")
  }
  threadPool.shutdown()
//  CoroutineScope(Dispatchers.Default)
}

suspend fun lec07Example1() {
  val job = CoroutineScope(Dispatchers.Default).launch {
    delay(1_000L)
    printWithThread("Job 1")

    val newCoroutineContext = coroutineContext + CoroutineName("이름")
    val minusKey = coroutineContext.minusKey(CoroutineName.Key)  // 존재하는 context 자체에 Element를 추가하는 방법

    printWithThread("Element 추가 전 = $coroutineContext")
    printWithThread("Element 추가 후 = $newCoroutineContext")
    printWithThread("Element 제거 후 = $minusKey")
  }

  job.join()
}

class AsyncLogic {
  // 내부적으로 자체 CoroutineScope를 가지고 있음
  private val scope = CoroutineScope(Dispatchers.Default)

  fun doSomething() {
    scope.launch {
      // 무언가 코루틴이 시작되어 작업!
    }
  }

  // scope 자체를 취소 -> scope에서 돌고있는 모든 코루틴에 취소 신호를 보냄
  fun destroy() {
    scope.cancel()
  }
}