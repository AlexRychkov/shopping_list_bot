package ru.shopping.api

import cats.effect.IO
import org.http4s.Method.POST
import org.http4s.Request
import org.http4s.client.Client
import ru.shopping.common.dto.EditListItem
import ru.shopping.common.models.ListItem

class ItemApi(private val client: Client[IO]) {
  def addItem(listId: ListItem.Id, name: String): IO[Unit] = {
    val req = Request(POST)
      .withEntity(EditListItem(name, "", 0))
      .withUri(Api.items(listId))
    client.expect[Unit](req)
  }

  def items(listId: ListItem.Id): IO[List[ListItem]] = {
    val uri = Api.items(listId)
    client.expect[List[ListItem]](uri)
  }
}

object ItemApi {
  def apply(client: Client[IO]) = new ItemApi(client)
}