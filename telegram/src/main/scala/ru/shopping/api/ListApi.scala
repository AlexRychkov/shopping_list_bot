package ru.shopping.api

import cats.effect.IO
import org.http4s.Method.POST
import org.http4s.Method.PUT
import org.http4s.Method.DELETE
import org.http4s.Request
import org.http4s.client.Client
import ru.shopping.common.dto.EditShoppingList
import ru.shopping.common.models.{ShoppingList, User}

class ListApi(private val client: Client[IO],
              private val backendApi: BackendApi) {
  def create(userId: User.Id, name: String): IO[Unit] = {
    val req = Request(POST)
      .withEntity(EditShoppingList(name))
      .withUri(backendApi.lists(userId))
    client.expect[Unit](req)
  }

  def lists(userId: User.Id): IO[List[ShoppingList]] = {
    val uri = backendApi.lists(userId)
    client.expect[List[ShoppingList]](uri)
  }

  def rename(listId: ShoppingList.Id, name: String): IO[Unit] = {
    val req = Request(PUT)
      .withEntity(EditShoppingList(name))
      .withUri(backendApi.lists(listId))
    client.expect[Unit](req)
  }

  def delete(listId: ShoppingList.Id): IO[Unit] = {
    val req = Request[IO](DELETE, backendApi.lists(listId))
    client.expect[Unit](req)
  }
}

object ListApi {
  def apply(client: Client[IO], backendApi: BackendApi) = new ListApi(client, backendApi)
}