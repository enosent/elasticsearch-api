package com.ymcorp.search

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.ymcorp.search.core.ElasticsearchService

import scala.io.StdIn

/**
  * Created by mac on 2017. 3. 2..
  */
object WebServer {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("piki-search")
    implicit val materializer = ActorMaterializer()

    implicit val executionContext = system.dispatcher

    val route =
      get {
        path("list") {
          complete(HttpEntity(ContentTypes.`application/json`, ElasticsearchService.list))
        }
      }~
      get {
        path("aggregate") {
          complete(HttpEntity(ContentTypes.`application/json`, ElasticsearchService.aggregate))
        }
      }~
      get {
        path("score") {
          complete(HttpEntity(ContentTypes.`application/json`, ElasticsearchService.scoreSearch))
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}