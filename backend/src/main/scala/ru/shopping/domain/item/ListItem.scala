package ru.shopping.domain.item

case class ListItem(listId: ListItem.Id, name: String, comment: String, price: BigDecimal, mark: ItemMark, id: ListItem.Id)

object ListItem {
  type Id = Long
}