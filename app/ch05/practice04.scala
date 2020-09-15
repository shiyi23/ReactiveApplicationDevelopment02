/**
 * 5.12节：Play中的Future
 */
package ch05

//程序清单5.6
import akka.actor.ActorSystem
import javax.inject.Inject
import play.api.libs.concurrent.CustomExecutionContext
import play.api.libs.ws.WSResponse
import play.api.mvc.{BaseController, ControllerComponents}
import play.api.libs.ws._

import scala.concurrent.{ExecutionContext, Future}
import play.api.mvc.ActionBuilder

// Make sure to bind the new context class to this trait using one of the custom
// binding techniques listed on the "Scala Dependency Injection" documentation page
trait MyExecutionContext extends ExecutionContext

class MyExecutionContextImpl @Inject() (system: ActorSystem)
  extends CustomExecutionContext(system, "my.executor")
    with MyExecutionContext

//可参考的代码
//class HomeController @Inject() ( myExecutionContext: MyExecutionContext,
//                                val controllerComponents: ControllerComponents)
//  extends BaseController {
//  def index = Action.async {
//    Future {
//      // Call some blocking API
//      Ok("result of blocking call")
//    }(myExecutionContext)
//  }
//}

class HomeController @Inject() (WS:WSClient, myExecutionContext: MyExecutionContext,
                                 val controllerComponents: ControllerComponents)
  extends BaseController {
  def availability = Action.async {
    Future {
      val myResponse: Future[WSResponse] =
        WS.url("http://www.playframework.com").get()

      val siteAvailable: Future[Boolean] = //生成Future实例
        myResponse.map(r => r.status == 200)(myExecutionContext) //注意，这里也要加
      // 一个ExecutionContext类型的实例

//      siteAvailable.map {
//        isAvailable =>
//          if (isAvailable) {
//            Ok("The site is up")
//          } else {
//            Ok("The site is down!")
//          }
//      }(myExecutionContext)



      // Call some blocking API
//      Ok("result of blocking call")
    }(myExecutionContext)
  }
}






