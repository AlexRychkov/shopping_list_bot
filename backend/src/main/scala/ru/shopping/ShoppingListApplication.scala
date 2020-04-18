package ru.shopping

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import com.softwaremill.macwire._
import org.http4s.server.blaze.BlazeServerBuilder
import ru.shopping.config.HttpAppConfig

object ShoppingListApplication extends IOApp {
  private val httpAppConfig = wire[HttpAppConfig]

  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(httpAppConfig())
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}