package ru.shopping.backend.component

import com.softwaremill.macwire.{Module, wire}
import ru.shopping.backend.repository.{ListItemRepository, ShoppingListRepository, StatisticRepository}

@Module
class RepositoryComponent {
  lazy val shoppingListRepository = wire[ShoppingListRepository]
  lazy val listItemRepository = wire[ListItemRepository]
  lazy val statisticRepository = wire[StatisticRepository]
  lazy val databaseComponent = wire[DatabaseComponent]
}
