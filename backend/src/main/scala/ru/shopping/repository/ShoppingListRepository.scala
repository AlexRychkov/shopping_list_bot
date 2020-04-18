package ru.shopping.repository

import ru.shopping.database.ApplicationJdbcContext
import ru.shopping.domain.list.ShoppingList
import ru.shopping.domain.user.UserId

class ShoppingListRepository(private val ctx: ApplicationJdbcContext) {

  import ctx._

  def create(shoppingList: ShoppingList): ShoppingList = ctx.run(quote {
    query[ShoppingList].insert(lift(shoppingList)).returning(a => a)
  })

  def get(creator: UserId, name: String): Option[ShoppingList] = ctx.run(quote {
    query[ShoppingList].filter(list => list.creator == lift(creator) && list.name == lift(name))
  }).headOption

  def get(creator: UserId): List[ShoppingList] = ctx.run(quote {
    query[ShoppingList].filter(_.creator == lift(creator))
  })

  def edit(creator: String, name: String, newName: String): ShoppingList = ctx.run(quote {
    query[ShoppingList].filter(list => list.creator == lift(creator) && list.name == lift(name))
      .update(_.name -> lift(newName))
      .returning(r => r)
  })

  def delete(creator: UserId, name: String): Unit = {
    ctx.run(quote {
      query[ShoppingList].filter(list => list.creator == lift(creator) && list.name == lift(name)).delete
    })
    ()
  }
}
