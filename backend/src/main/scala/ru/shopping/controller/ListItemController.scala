package ru.shopping.controller

import cats.effect._
import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._
import ru.shopping.controller.dto.EditListItem
import ru.shopping.domain.item.ItemMark
import ru.shopping.domain.item.ItemMark.Canceled
import ru.shopping.repository.ListItemRepository

class ListItemController(private val listItemRepository: ListItemRepository) extends Controller {
  override val version: String = "v1"
  override val rootPath: String = ""

  // TODO parse null values in EditListItem

  override val controller: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "item" / prefix => Ok(listItemRepository.getByPrefix(prefix))

    case GET -> Root / "list" / LongVar(listId) / "item" / LongVar(itemId) => Ok(listItemRepository.get(listId, itemId))

    case req@POST -> Root / "list" / LongVar(listId) / "item" => for {
      editListItem <- req.as[EditListItem]
      resp <- Ok(listItemRepository.create(listId, editListItem.name, editListItem.comment, editListItem.price))
    } yield resp

    case req@PATCH -> Root / "list" / LongVar(listId) / "item" / LongVar(itemId) / "mark" => for {
      mark <- req.as[ItemMark]
      resp <- Ok(listItemRepository.setMark(listId, itemId, mark))
    } yield resp

    case req@PATCH -> Root / "list" / LongVar(listId) / "item" / LongVar(itemId) / "comment" => for {
      comment <- req.as[String]
      resp <- Ok(listItemRepository.setComment(listId, itemId, comment))
    } yield resp

    case req@PATCH -> Root / "list" / LongVar(listId) / "item" / LongVar(itemId) / "price" => for {
      price <- req.as[BigDecimal]
      resp <- Ok(listItemRepository.setPrice(listId, itemId, price))
    } yield resp

    case DELETE -> Root / "list" / LongVar(listId) / "item" / LongVar(itemId) =>
      Ok(listItemRepository.setMark(listId, itemId, Canceled))
  }
}