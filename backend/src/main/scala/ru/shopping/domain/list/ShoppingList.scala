package ru.shopping.domain.list

import ru.shopping.domain.user.UserId

case class ShoppingList(creator: UserId, name: String)
