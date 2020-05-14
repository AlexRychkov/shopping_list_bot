package ru.shopping.common.dto

import cats.effect.IO
import io.circe.generic.auto._
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.{EntityDecoder, EntityEncoder}

case class EditListItem(name: String, comment: String, price: BigDecimal)

object EditListItem {
  implicit val editListItemEntityDecoder: EntityDecoder[IO, EditListItem] = jsonOf[IO, EditListItem]
  implicit val editListItemEntityEncoder: EntityEncoder[IO, EditListItem] = jsonEncoderOf[IO, EditListItem]
}