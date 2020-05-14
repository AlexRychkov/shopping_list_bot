import sbt._

object Dependencies {
  val circe = Seq(
    "io.circe" %% "circe-core" % Version.circe,
    "io.circe" %% "circe-generic" % Version.circe,
    "io.circe" %% "circe-literal" % Version.circe,
    "org.http4s" %% "http4s-circe" % Version.http4s
  )

  val config = Seq(
    "com.github.pureconfig" %% "pureconfig" % "0.12.3"
  )

  val http4s = Seq(
    "org.http4s" %% "http4s-blaze-server" % Version.http4s,
    "org.http4s" %% "http4s-dsl" % Version.http4s,
    "org.http4s" %% "http4s-blaze-client" % Version.http4s
  )

  val jwt = Seq (
    "com.pauldijou" %% "jwt-circe" % Version.jwt
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
    "com.typesafe.slick" %% "slick" % Version.slick,
    "com.typesafe.slick" %% "slick-hikaricp" % Version.slick
  )

  val enumeratum = Seq(
    "com.beachape" %% "enumeratum" % "1.5.15",
    "com.beachape" %% "enumeratum-slick" % "1.5.16",
    "com.beachape" %% "enumeratum-circe" % "1.5.23"
  )

  val db = Seq(
    "org.postgresql" % "postgresql" % "42.2.8",
    "io.getquill" %% "quill-jdbc" % Version.quill
  )

  val test = Seq(
    "org.scalatest" %% "scalatest" % "3.0.8" % Test,
    "org.scalamock" %% "scalamock" % "4.4.0" % Test
  )

  val common = config ++ macwire ++ logging ++ enumeratum ++ test
}