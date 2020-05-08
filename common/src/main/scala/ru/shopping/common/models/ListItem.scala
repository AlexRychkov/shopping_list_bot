package ru.shopping.common.models

import cats.effect.IO
import io.circe.generic.auto._
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.{EntityDecoder, EntityEncoder}

case class ListItem(listId: ShoppingList.Id, name: String, comment: String, price: BigDecimal, mark: ItemMark, id: ListItem.Id)

object ListItem {
  type Id = Long

  implicit val listItemEntityDecoder: EntityDecoder[IO, ListItem] = jsonOf[IO, ListItem]
  implicit val listItemEntityListDecoder: EntityDecoder[IO, List[ListItem]] = jsonOf[IO, List[ListItem]]
  implicit val listItemEntityEncoder: EntityEncoder[IO, ListItem] = jsonEncoderOf[IO, ListItem]
}