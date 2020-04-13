import Versions._
import sbt._

object Dependencies {
  val akkaHttp = Seq(
    "com.typesafe.akka" %% "akka-http" % "10.1.11",
    "de.heikoseeberger" %% "akka-http-circe" % "1.31.0",
    "com.typesafe.akka" %% "akka-testkit" % "2.6.4" % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % "10.1.11" % Test
  )

  val circe = Seq(
    "io.circe" %% "circe-core" % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.circe" %% "circe-parser" % circeVersion,
    "io.circe" %% "circe-optics" % circeVersion,
    "de.heikoseeberger" %% "akka-http-circe" % circeVersion
  )

  val macwire = Seq(
    "com.softwaremill.macwire" %% "macros" % "2.3.3" % Provided,
    "com.softwaremill.macwire" %% "util" % "2.3.3"
  )

  val logging = Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.3"
  )

  val slick = Seq(
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % slickVersion
  )

  val enumeratum = Seq(
    "com.beachape" %% "enumeratum" % "1.5.15",
    "com.beachape" %% "enumeratum-slick" % "1.5.16",
    "com.beachape" %% "enumeratum-circe" % "1.5.23"
  )

  val test = Seq(
    "org.scalatest" %% "scalatest" % "3.0.8" % Test,
    "org.scalamock" %% "scalamock" % "4.4.0" % Test
  )

  val commonDependencies = macwire ++ logging ++ enumeratum ++ test
}

object Versions {
  val circeVersion = "0.13.0"
  val slickVersion = "3.3.2"
}