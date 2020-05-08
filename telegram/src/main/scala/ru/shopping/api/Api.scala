package ru.shopping.api

import org.http4s.Uri
import org.http4s.implicits._
import ru.shopping.common.models.{ListItem, ShoppingList}

private[api] object Api {
  private val baseApi = uri"http://localhost:8080/api/v1/"

  def lists(id: Long): Uri = baseApi / "list" / s"$id"

  def mark(listId: ListItem.Id, itemId: ListItem.Id): Uri = baseApi / "list" / s"$listId" / "item" / s"$itemId" / "mark"

  def items(listId: ShoppingList.Id): Uri = baseApi / "list" / s"$listId" / "item"
}
