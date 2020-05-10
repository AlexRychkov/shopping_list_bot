package ru.shopping.api

import cats.effect.IO
import org.http4s.Method.POST
import org.http4s.Request
import org.http4s.client.Client
import ru.shopping.common.dto.EditShoppingList
import ru.shopping.common.models.{ShoppingList, User}

class ListApi(private val client: Client[IO]) {
  def create(userId: User.Id, name: String): IO[Unit] = {
    val req = Request(POST)
      .withEntity(EditShoppingList(name))
      .withUri(Api.lists(userId))
    client.expect[Unit](req)
  }

  def lists(userId: User.Id): IO[List[ShoppingList]] = {
    val uri = Api.lists(userId)
    client.expect[List[ShoppingList]](uri)
  }
}

object ListApi {
  def apply(client: Client[IO]) = new ListApi(client)
}