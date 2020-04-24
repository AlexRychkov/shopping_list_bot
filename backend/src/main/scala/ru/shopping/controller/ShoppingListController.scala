package ru.shopping.controller

import cats.effect._
import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._
import ru.shopping.controller.dto.EditShoppingList
import ru.shopping.repository.ShoppingListRepository

class ShoppingListController(private val shoppingListRepository: ShoppingListRepository) extends Controller {
  override val version: String = "v1"
  override val rootPath: String = "list"

  override val controller: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / creator => Ok(shoppingListRepository.get(creator))

    case GET -> Root / creator / name => Ok(shoppingListRepository.get(creator, name))

    case req@POST -> Root / creator => for {
      editShoppingList <- req.as[EditShoppingList]
      resp <- Ok(shoppingListRepository.create(creator, editShoppingList.name))
    } yield resp

    case req@PUT -> Root / creator / name => for {
      editShoppingList <- req.as[EditShoppingList]
      resp <- Ok(shoppingListRepository.edit(creator, name, editShoppingList.name))
    } yield resp

    case DELETE -> Root / creator / name => Ok(shoppingListRepository.delete(creator, name))
  }
}
