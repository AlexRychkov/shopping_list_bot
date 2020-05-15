package ru.shopping.api

import cats.effect.IO
import org.http4s.Method.{PATCH, POST}
import org.http4s.Request
import org.http4s.client.Client
import ru.shopping.common.dto.EditListItem
import ru.shopping.common.models.{ItemMark, ListItem, ShoppingList}

class ItemApi(private val client: Client[IO],
              private val backendApi: BackendApi) {
  def addItem(listId: ListItem.Id, name: String): IO[Unit] = {
    val req = Request(POST)
      .withEntity(EditListItem(name, None, None))
      .withUri(backendApi.items(listId))
    client.expect[Unit](req)
  }

  def items(listId: ListItem.Id): IO[List[ListItem]] = {
    val uri = backendApi.items(listId)
    client.expect[List[ListItem]](uri)
  }

  def mark(listId: ShoppingList.Id, itemId: ListItem.Id, mark: ItemMark): IO[ListItem] = {
    val req = Request[IO](PATCH)
      .withEntity(s""""${mark.toString}"""")
      .withUri(backendApi.mark(listId, itemId))
    client.expect[ListItem](req)
  }
}

object ItemApi {
  def apply(client: Client[IO], backendApi: BackendApi) = new ItemApi(client, backendApi)
}