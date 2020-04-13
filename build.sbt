name := "shopping_list_bot"

version := "0.1"

scalaVersion := "2.13.1"

lazy val common = module("common")

lazy val backend = module("backend").dependsOn(common)

lazy val bot = module("telegram").dependsOn(common)

lazy val frontend = module("frontend").dependsOn(common)

lazy val moduleDependencies = Map(
  "backend" -> Seq("com.opentable.components" % "otj-pg-embedded" % "0.13.3")
)

def module(moduleName: String) = Project(moduleName, file(moduleName)).settings(
  scalaVersion := "2.13.1",
  scalacOptions := ScalacOptions.options,
  libraryDependencies ++= Dependencies.commonDependencies ++ moduleDependencies.getOrElse(moduleName, Seq())
)