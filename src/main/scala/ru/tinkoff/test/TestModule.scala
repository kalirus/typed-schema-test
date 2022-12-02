package ru.tinkoff.test

import derevo.circe.{decoder, encoder}
import derevo.derive
import ru.tinkoff.test.Marshalling._
import ru.tinkoff.tschema.akkaHttp.{MkRoute, Routable}
import ru.tinkoff.tschema.swagger.{MkSwagger, Swagger}
import ru.tinkoff.tschema.syntax._

object definitions {

  @derive(encoder, decoder, Swagger)
  case class ClassA(name: String, parent: Option[ClassA])

  @derive(encoder, decoder, Swagger)
  case class ClassB(a: Option[ClassA])

  def test = operation("test") |> get |> complete[ClassB]

  def api = tagPrefix("module") |> (test)
}

object TestModule {

  import definitions._

  trait ModuleApi {
    def test: ClassB
  }

  object ModuleApiImpl extends ModuleApi {
    override def test: ClassB = {
      ClassB(a = Some(ClassA(
        name = "John Doe",
        parent = Some(ClassA(
          name = "John Doe Sr.",
          parent = None)
        )
      )))
    }
  }

  val swagger = MkSwagger(api)

  implicit def successRoutable: Routable[ClassB, ClassB] = Routable.single[ClassB]

  val route = MkRoute(api)(ModuleApiImpl)
}
