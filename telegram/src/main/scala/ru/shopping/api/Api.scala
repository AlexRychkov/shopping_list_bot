package ru.shopping.api

import org.http4s.Uri
import org.http4s.implicits._
import ru.shopping.common.models.{ListItem, User}

private[api] object Api {
  private val baseApi = uri"http://localhost:8080/api/v1/"

  def lists(userId: User.Id): Uri = baseApi / "list" / s"$userId"

  def items(listId: ListItem.Id): Uri = baseApi / "list" / s"$listId" / "item"
}
