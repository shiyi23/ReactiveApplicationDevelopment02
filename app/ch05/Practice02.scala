import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContext, Future}

//创建Future的步骤一：先创建Future的所需要访问的ExecutionContext
implicit val myEC = ExecutionContext.fromExecutor( //
  Executors.newFixedThreadPool(3)
)

//创建Future的步骤二：创建具体的Future实例

val mySUM : Future[Double] = Future{ 6 * 7 }

val mySum2 = List(2, 6, 8)

mySum2.foreach(e => println(e))

mySUM.foreach(s => println(s))





