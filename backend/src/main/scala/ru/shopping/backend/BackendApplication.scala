package ru.shopping.backend

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import com.softwaremill.macwire.wire
import org.http4s.server.blaze.BlazeServerBuilder
import ru.shopping.backend.component.{HttpAppComponent, RepositoryComponent, ServiceComponent}

import scala.annotation.unused

object BackendApplication extends IOApp {
  @unused private lazy val repositoryComponent: RepositoryComponent = wire[RepositoryComponent]
  @unused private lazy val serviceComponent: ServiceComponent = wire[ServiceComponent]
  private lazy val httpAppConfig: HttpAppComponent = wire[HttpAppComponent]

  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(httpAppConfig())
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
}