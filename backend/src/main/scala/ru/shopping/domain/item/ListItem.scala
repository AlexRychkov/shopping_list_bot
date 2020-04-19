package ru.shopping.domain.item

import ru.shopping.domain.list.ListId

case class ListItem(listId: ListId, name: String, comment: String, price: BigDecimal, mark: ItemMark, id: ItemId)
