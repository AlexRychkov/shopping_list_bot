package ru.shopping.api

import org.http4s.Uri
import ru.shopping.common.models.{ListItem, ShoppingList, User}
import ru.shopping.telegram.BackendConfig

class BackendApi(backendConfig: BackendConfig) {
  private val baseApi = Uri.unsafeFromString(backendConfig.baseUri) / "api" / "v1"

  def lists(id: Long): Uri = baseApi / "list" / s"$id"

  def mark(listId: ListItem.Id, itemId: ListItem.Id): Uri = baseApi / "list" / s"$listId" / "item" / s"$itemId" / "mark"

  def items(listId: ShoppingList.Id): Uri = baseApi / "list" / s"$listId" / "item"

  def auth(userId: User.Id): Uri = baseApi / "auth" / "req" / s"$userId"
}

object BackendApi {
  def apply(backendConfig: BackendConfig): BackendApi = new BackendApi(backendConfig)
}
