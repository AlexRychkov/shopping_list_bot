package ru.shopping.common.models

import java.time.Instant

import cats.effect.IO
import io.circe.generic.auto._
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.{EntityDecoder, EntityEncoder}

case class ShoppingList(id: ShoppingList.Id, creator: User.Id, name: String, created: Long = Instant.now().getEpochSecond)

object ShoppingList {
  type Id = Long

  implicit val shoppingListEntityDecoder: EntityDecoder[IO, ShoppingList] = jsonOf[IO, ShoppingList]
  implicit val shoppingListEntityListDecoder: EntityDecoder[IO, List[ShoppingList]] = jsonOf[IO, List[ShoppingList]]
  implicit val shoppingListEntityEncoder: EntityEncoder[IO, ShoppingList] = jsonEncoderOf[IO, ShoppingList]
}