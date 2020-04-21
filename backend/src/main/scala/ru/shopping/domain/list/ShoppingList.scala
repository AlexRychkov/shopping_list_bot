package ru.shopping.domain.list

import ru.shopping.domain.user.UserId

case class ShoppingList(id: ShoppingList.Id, creator: UserId, name: String)

object ShoppingList {
  type Id = Long
}