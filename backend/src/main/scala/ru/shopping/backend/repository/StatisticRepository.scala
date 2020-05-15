package ru.shopping.backend.repository

import ru.shopping.backend.database.ApplicationJdbcContext
import ru.shopping.common.models.{ItemMark, ListItem, ShoppingList, User}

import scala.annotation.unused

class StatisticRepository(private val ctx: ApplicationJdbcContext) {

  import ctx._

  def get(creator: User.Id, from: Long, to: Long): List[ShoppingList] = ctx.run(quote {
    query[ShoppingList].filter(list =>
      list.creator == lift(creator) && list.created >= lift(from) && list.created <= lift(to)
    )
  })

  @unused
  def get(creator: User.Id, from: Long, to: Long, mark: ItemMark): List[(ListItem, ShoppingList)] = ctx.run(quote {
    for {
      listItem <- query[ListItem] if listItem.mark == lift(mark)
      list <- query[ShoppingList] if list.id == listItem.listId && list.creator == lift(creator) && list.created >= lift(from) && list.created <= lift(to)
    } yield (listItem, list)
  })
}