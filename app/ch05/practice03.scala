
//程序清单5.5：对阻塞操作创建Future

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import java.io.File

def fileExists(path: String): Future[Boolean] = Future { //Future代码块
  blocking { //blocking标记能够用于说明执行上下文特定的代码片段是阻塞的
    new File(path).exists()
  }
}




