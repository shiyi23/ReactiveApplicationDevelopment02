package ch05

import javax.inject.Inject

import scala.concurrent.Future
import scala.concurrent.duration._
import play.api.mvc._
import play.api.libs.ws._
import play.api.http.HttpEntity
import akka.actor.ActorSystem
import akka.actor.TypedActor.dispatcher
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.ExecutionContext
import play.api.libs.concurrent.ExecutionContextProvider



class Application @Inject() (WS: WSClient, //使用@Inject注解来实现依赖注入,顺便实现WSClient对象
                             val controllerComponents: ControllerComponents) extends BaseController {
  val myReponse: Future[WSResponse] = WS.url("http://www.playframework.com").get()

  val myReponse2: Future[WSResponse] = WS.url("http://www.playframework.com").get()

  val siteOnline: Future[Boolean] = myReponse.map { r => r.status == 200 }

  val siteAvailable: Future[Option[Boolean]] = myReponse.map {
    r => Some(r.status == 200)
  } recover { //recover函数的参数是一个偏函数
    case ce: java.net.ConnectException => None
  }

  siteOnline.foreach { isOnline =>
    if (isOnline) {
      println("The Play site is up")
    }else {
      println("The Play site is down")
    }
  }


  /**
   * 5.1.1 Future基础：(3): 组合Future
   */

  def siteAvailable(url: String): Future[Boolean] = //这里的函数返回类型是不可以省略的
    WS.url(url).get().map {
      r => r.status == 200
  }

  val playSiteAvailable = siteAvailable("http://www.playframework.com")

  val playGithubAvailable = siteAvailable("https://github.com/playframework")

  val allSiteAvailable: Future[Boolean] = for {
    siteAvailable <- playSiteAvailable //简单说这是一个集合遍历的方法,往深了说还是for推导式这个语法糖
    githubAvailable <- playGithubAvailable
  } yield (siteAvailable && githubAvailable) //返回的是两个集合经过&&操作后形成的集合

  val overallAvailability: Future[Option[Boolean]] =
    allSiteAvailable.map {
      a =>
        Option(a)
    } recover {
      case ce: java.net.ConnectException => None
    }

//  /**
//   * 声明自定义的ExecutionContext并基于它运行简单的Future代码,
//   * 也是一种便捷的创建Future的方式
//   */
//
//  import scala.concurrent._
//  import java.util.concurrent.Executors
//
//  implicit val ec = ExecutionContext.fromExecutor(
//    Executors.newFixedThreadPool(3) //声明固定大小的、
//    // 具有三个线程的线程池
//  )
//
//  val sum: Future[Int] = Future{5 * 3}
//
//  sum.foreach { s=> println(s)}





}




