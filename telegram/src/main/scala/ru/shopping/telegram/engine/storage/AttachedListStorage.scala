package ru.shopping.telegram.engine.storage

import java.util.concurrent.ConcurrentHashMap

import ru.shopping.common.models.{ShoppingList, User}

object AttachedListStorage {
  private val userList = new ConcurrentHashMap[User.Id, ShoppingList.Id]()

  def put(userId: User.Id, listId: ShoppingList.Id): Unit = {
    userList.put(userId, listId)
    ()
  }

  def get(userId: User.Id): Option[ShoppingList.Id] = Option(userList.get(userId))

  def getOrElse(userId: User.Id): Option[ShoppingList.Id] = Option(userList.get(userId))
}
