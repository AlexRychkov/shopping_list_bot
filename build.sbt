name := "shopping_list"
version := "0.1"
scalaVersion := Version.scala

lazy val moduleDependencies = Map(
  "backend" -> Seq(Dependencies.circe, Dependencies.db, Dependencies.http4s, Dependencies.jwt).flatten,
  "common" -> Dependencies.circe,
  "telegram" -> Seq(Dependencies.http4s, Dependencies.circe).flatten
)

def module(moduleName: String) = Project(moduleName, file(moduleName))
  .settings(
    scalaVersion := Version.scala,
    scalacOptions := ScalacOptions.options,
    libraryDependencies ++= Dependencies.common ++ moduleDependencies.getOrElse(moduleName, Seq()),
    test in assembly := {}
  )

lazy val common = module("common")

lazy val backend = module("backend")
  .dependsOn(common)
  .settings(
    mainClass in assembly := Some("ru.shopping.backend.BackendApplication")
  )

lazy val bot = module("telegram")
  .dependsOn(common)
  .settings(
    mainClass in assembly := Some("ru.shopping.telegram.TelegramApplication")
  )