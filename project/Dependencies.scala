import Versions._
import sbt._

object Dependencies {

  val circe = Seq(
    "io.circe" %% "circe-core" % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.circe" %% "circe-literal" % circeVersion,
    "org.http4s" %% "http4s-circe" % http4sVersion
  )

  val config = Seq(
    "com.github.pureconfig" %% "pureconfig" % "0.12.3"
  )

  val http4s = Seq(
    "org.http4s" %% "http4s-blaze-server" % http4sVersion,
    "org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-blaze-client" % http4sVersion
  )

  val macwire = Seq(
    "com.softwaremill.macwire" %% "macros" % "2.3.3" % Provided,
    "com.softwaremill.macwire" %% "util" % "2.3.3"
  )

  val logging = Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
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

  val db = Seq(
    "org.postgresql" % "postgresql" % "42.2.8",
    "io.getquill" %% "quill-jdbc" % quillVersion
  )

  val test = Seq(
    "org.scalatest" %% "scalatest" % "3.0.8" % Test,
    "org.scalamock" %% "scalamock" % "4.4.0" % Test
  )

  val commonDependencies = config ++ macwire ++ logging ++ enumeratum ++ test
}

object Versions {
  val http4sVersion = "0.21.3"
  val circeVersion = "0.13.0"
  val slickVersion = "3.3.2"
  val quillVersion = "3.5.1"
  val scalaVer = "2.13.1"
}