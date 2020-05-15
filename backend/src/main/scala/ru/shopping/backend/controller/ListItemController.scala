package ru.shopping.backend.controller

import cats.effect._
import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._
import ru.shopping.backend.repository.ListItemRepository
import ru.shopping.common.dto.EditListItem
import ru.shopping.common.models.ItemMark
import ru.shopping.common.models.ItemMark.Canceled

class ListItemController(private val listItemRepository: ListItemRepository) extends Controller {
  override val version: String = "v1"
  override val rootPath: String = "item/"

  override val controller: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "prefix" / prefix => Ok(listItemRepository.getByPrefix(prefix))

    case GET -> Root / LongVar(listId) => Ok(listItemRepository.get(listId))

    case GET -> Root / LongVar(listId) / LongVar(itemId) => Ok(listItemRepository.get(listId, itemId))

    case req@POST -> Root / LongVar(listId) => for {
      editListItem <- req.as[EditListItem]
      resp <- Ok(listItemRepository.create(listId, editListItem.name, editListItem.comment, editListItem.price))
    } yield resp

    case req@PATCH -> Root / LongVar(listId) / LongVar(itemId) / "mark" => for {
      mark <- req.as[ItemMark]
      resp <- Ok(listItemRepository.setMark(listId, itemId, mark))
    } yield resp

    case req@PATCH -> Root / LongVar(listId) / LongVar(itemId) / "comment" => for {
      comment <- req.as[String]
      resp <- Ok(listItemRepository.setComment(listId, itemId, Some(comment)))
    } yield resp

    case req@PATCH -> Root / LongVar(listId) / LongVar(itemId) / "price" => for {
      price <- req.as[BigDecimal]
      resp <- Ok(listItemRepository.setPrice(listId, itemId, Some(price)))
    } yield resp

    case DELETE -> Root / LongVar(listId) / LongVar(itemId) =>
      Ok(listItemRepository.setMark(listId, itemId, Canceled))
  }
}