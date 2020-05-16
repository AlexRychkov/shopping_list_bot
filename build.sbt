import sbt.Keys.libraryDependencies

name := "shopping_list"
version := "0.1"
scalaVersion := Version.scala

def module(moduleName: String) = Project(moduleName, file(moduleName))
  .settings(
    scalaVersion := Version.scala,
    scalacOptions := ScalacOptions.options,
    test in assembly := {}
  )

lazy val common = module("common")
  .settings(
    libraryDependencies ++= Dependencies.common ++ Dependencies.circe
  )

lazy val backend = module("backend")
  .dependsOn(common)
  .settings(
    mainClass in assembly := Some("ru.shopping.backend.BackendApplication"),
    libraryDependencies ++= Dependencies.common ++ Dependencies.circe ++ Dependencies.db ++ Dependencies.http4s ++ Dependencies.jwt,
  )

lazy val bot = module("telegram")
  .dependsOn(common)
  .settings(
    mainClass in assembly := Some("ru.shopping.telegram.TelegramApplication"),
    libraryDependencies ++= Dependencies.common ++ Dependencies.http4s ++ Dependencies.circe,
  )