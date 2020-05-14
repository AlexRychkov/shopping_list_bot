package ru.shopping.backend

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import com.softwaremill.macwire.wire
import org.http4s.server.blaze.BlazeServerBuilder
import ru.shopping.backend.component.{HttpAppComponent, ServiceComponent}

import scala.annotation.unused

object BackendApplication extends IOApp {
  @unused private val serviceComponent = wire[ServiceComponent]
  private val httpAppConfig = wire[HttpAppComponent]

  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(httpAppConfig())
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
}