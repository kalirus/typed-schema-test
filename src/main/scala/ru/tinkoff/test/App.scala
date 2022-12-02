package ru.tinkoff.test

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import ru.tinkoff.test.Marshalling._
import ru.tinkoff.tschema.swagger.OpenApiInfo

object App {

  implicit val system = ActorSystem("swagger-test")

  val routes = pathPrefix("api") {
    TestModule.route
  } ~ path("swagger")(
    get {
      complete(
        TestModule.swagger
          .make(OpenApiInfo())
          .addServer("/api")
      )
    }
  )

  def main(args: Array[String]): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global
    for (_ <- Http().newServerAt("localhost", 8089).bindFlow(routes))
      println("server started at http://localhost:8089/swagger")
  }
}
