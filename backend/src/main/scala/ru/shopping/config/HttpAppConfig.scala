package ru.shopping.config

import cats.effect.IO
import com.softwaremill.macwire.{Module, wire, wireSet}
import org.http4s.HttpApp
import org.http4s.implicits._
import org.http4s.server.Router
import ru.shopping.controller.{Controller, ShoppingListController}

@Module
class HttpAppConfig {
  lazy val listController = wire[ShoppingListController]
  lazy val controllers: Seq[Controller] = wireSet[Controller].toSeq
  lazy val httpApp: HttpApp[IO] = Router(controllers.map(ctr => s"/api/${ctr.version}/${ctr.rootPath}" -> ctr.controller): _*).orNotFound
  val repositoryConfig = wire[RepositoryConfig]

  def apply(): HttpApp[IO] = httpApp
}
