package ru.shopping.common.dto

import cats.effect.IO
import io.circe.generic.auto._
import org.http4s.circe._
import org.http4s.{EntityDecoder, EntityEncoder}

case class EditShoppingList(name: String)

object EditShoppingList {
  implicit val editShoppingListEntityDecoder: EntityDecoder[IO, EditShoppingList] = jsonOf[IO, EditShoppingList]
  implicit val editShoppingListEntityEncoder: EntityEncoder[IO, EditShoppingList] = jsonEncoderOf[IO, EditShoppingList]
}