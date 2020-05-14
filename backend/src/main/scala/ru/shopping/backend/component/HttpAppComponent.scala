package ru.shopping.backend.component

import cats.effect.IO
import com.softwaremill.macwire.{Module, wire, wireSet}
import org.http4s.HttpApp
import org.http4s.implicits._
import org.http4s.server.Router
import ru.shopping.backend.controller.{Controller, ExchangeAuthController, ListItemController, RequestAuthController, ShoppingListController}
import org.http4s.server.middleware.CORS

@Module
class HttpAppComponent(serviceComponent: ServiceComponent) {
  import serviceComponent._
  lazy val listController = wire[ShoppingListController]
  lazy val itemController = wire[ListItemController]
  lazy val exchAuthController = wire[ExchangeAuthController]
  lazy val reqAuthController = wire[RequestAuthController]
  lazy val controllers: Seq[Controller] = wireSet[Controller].toSeq
  lazy val httpApp: HttpApp[IO] = Router(controllers.map(ctr => s"/api/${ctr.version}/${ctr.rootPath}" -> ctr.controller): _*).orNotFound
  val repositoryComponent = wire[RepositoryComponent]

  def apply(): HttpApp[IO] = CORS(httpApp)
}
