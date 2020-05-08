package ru.shopping.backend.controller

import cats.effect._
import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._
import ru.shopping.backend.repository.ShoppingListRepository
import ru.shopping.common.dto.EditShoppingList

class ShoppingListController(private val shoppingListRepository: ShoppingListRepository) extends Controller {
  override val version: String = "v1"
  override val rootPath: String = "list"

  override val controller: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / LongVar(creator) => Ok(shoppingListRepository.get(creator))

    case GET -> Root / LongVar(creator) / name => Ok(shoppingListRepository.get(creator, name))

    case req@POST -> Root / LongVar(creator) => for {
      editShoppingList <- req.as[EditShoppingList]
      resp <- Ok(shoppingListRepository.create(creator, editShoppingList.name))
    } yield resp

    case req@PUT -> Root / LongVar(creator) / name => for {
      editShoppingList <- req.as[EditShoppingList]
      resp <- Ok(shoppingListRepository.edit(creator, name, editShoppingList.name))
    } yield resp

    case DELETE -> Root / LongVar(creator) / name => Ok(shoppingListRepository.delete(creator, name))
  }
}
