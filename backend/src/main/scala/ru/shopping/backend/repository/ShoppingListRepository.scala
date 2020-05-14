package ru.shopping.backend.repository

import ru.shopping.backend.database.ApplicationJdbcContext
import ru.shopping.common.models
import ru.shopping.common.models.{ShoppingList, User}

class ShoppingListRepository(private val ctx: ApplicationJdbcContext) {

  import ctx._

  def create(creator: User.Id, name: String): ShoppingList = {
    val shoppingList = models.ShoppingList(0L, creator, name)
    val generatedId = ctx.run(quote {
      query[ShoppingList].insert(lift(shoppingList)).returningGenerated(_.id)
    })
    shoppingList.copy(id = generatedId)
  }

  def get(creator: User.Id, name: String): Option[ShoppingList] = ctx.run(quote {
    query[ShoppingList].filter(list => list.creator == lift(creator) && list.name == lift(name))
  }).headOption

  def get(creator: User.Id): List[ShoppingList] = ctx.run(quote {
    query[ShoppingList].filter(_.creator == lift(creator))
  })

  def edit(listId: ShoppingList.Id, newName: String): ShoppingList = ctx.run(quote {
    query[ShoppingList].filter(list => list.id == lift(listId))
      .update(_.name -> lift(newName))
      .returning(r => r)
  })

  def delete(listId: ShoppingList.Id): Unit = {
    ctx.run(quote {
      query[ShoppingList].filter(list => list.id == lift(listId)).delete
    })
    ()
  }
}
