package ru.shopping.repository

import ru.shopping.database.ApplicationJdbcContext
import ru.shopping.domain.item.{ItemMark, ListItem}
import ru.shopping.domain.list.ShoppingList

class ListItemRepository(private val ctx: ApplicationJdbcContext) {

  import ctx._

  def create(listId: ShoppingList.Id, name: String, comment: String, price: BigDecimal): ListItem = {
    val listItem = ListItem(listId, name, comment, price, ItemMark.Wait, 0L)
    val generatedId = ctx.run(quote {
      query[ListItem].insert(lift(listItem)).returningGenerated(_.id)
    })
    listItem.copy(id = generatedId)
  }

  def getByPrefix(prefix: String): List[String] = {
    val prefixVar = s"$prefix%"
    ctx.run(quote {
      query[ListItem].filter(item => item.name like lift(prefixVar))
    }).map(_.name)
  }

  def get(listId: ShoppingList.Id): List[ListItem] = ctx.run(quote {
    query[ListItem].filter(item => item.listId == lift(listId))
  })

  def get(listId: ShoppingList.Id, itemId: ListItem.Id): Option[ListItem] = ctx.run(quote {
    query[ListItem].filter(item => item.listId == lift(listId) && item.id == lift(itemId))
  }).headOption

  def setMark(listId: ShoppingList.Id, itemId: ListItem.Id, mark: ItemMark): ListItem = ctx.run(quote {
    query[ListItem].filter(item => item.id == lift(itemId) && item.listId == lift(listId))
      .update(_.mark -> lift(mark))
      .returning(r => r)
  })

  def setComment(listId: ShoppingList.Id, itemId: ListItem.Id, comment: String): ListItem = ctx.run(quote {
    query[ListItem].filter(item => item.id == lift(itemId) && item.listId == lift(listId))
      .update(_.comment -> lift(comment))
      .returning(r => r)
  })

  def setPrice(listId: ShoppingList.Id, itemId: ListItem.Id, price: BigDecimal): ListItem = ctx.run(quote {
    query[ListItem].filter(item => item.id == lift(itemId) && item.listId == lift(listId))
      .update(_.price -> lift(price))
      .returning(r => r)
  })

  def delete(listId: ShoppingList.Id, itemId: ListItem.Id): Unit = {
    ctx.run(quote {
      query[ListItem].filter(item => item.listId == lift(listId) && item.id == lift(itemId)).delete
    })
    ()
  }
}
