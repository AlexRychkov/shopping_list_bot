package ru.shopping.backend.component

import com.softwaremill.macwire.{Module, wire}
import ru.shopping.backend.repository.{ListItemRepository, ShoppingListRepository}

@Module
class RepositoryComponent {
  lazy val shoppingListRepository = wire[ShoppingListRepository]
  lazy val listItemRepository = wire[ListItemRepository]
  val databaseComponent = wire[DatabaseComponent]
}
