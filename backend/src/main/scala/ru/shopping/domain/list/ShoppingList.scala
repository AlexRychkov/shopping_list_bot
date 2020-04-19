package ru.shopping.domain.list

import ru.shopping.domain.user.UserId

case class ShoppingList(id: ListId, creator: UserId, name: String)
