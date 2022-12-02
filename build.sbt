ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "typed-schema-test",
    scalacOptions ++= Seq("-language:higherKinds", "-language:implicitConversions", "-Ymacro-annotations", "-deprecation"),
    libraryDependencies ++= Seq(
      "ru.tinkoff" %% "typed-schema-swagger" % "0.15.2",
      "ru.tinkoff" %% "typed-schema-akka-http" % "0.15.2",
      "tf.tofu" %% "derevo-circe" % "0.12.8",
      "tf.tofu" %% "derevo-circe-magnolia" % "0.12.8"
    )
  )
