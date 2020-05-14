package ru.shopping.telegram.engine.storage

import java.util.concurrent.ConcurrentHashMap

import ru.shopping.common.models.ShoppingList

object ListInfoStorage {
  private val lists = new ConcurrentHashMap[ShoppingList.Id, ShoppingList]()

  def put(lists: List[ShoppingList]): Unit =
    lists.foreach(list => put(list.id, list))

  def put(listId: ShoppingList.Id, list: ShoppingList): Unit = {
    lists.put(listId, list)
    ()
  }

  def get(listId: ShoppingList.Id): Option[ShoppingList] = Option(lists.get(listId))
}
