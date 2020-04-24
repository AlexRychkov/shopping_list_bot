package ru.shopping.config

import com.softwaremill.macwire.{Module, wire}
import ru.shopping.repository.{ListItemRepository, ShoppingListRepository}

@Module
class RepositoryConfig {
  lazy val shoppingListRepository = wire[ShoppingListRepository]
  lazy val listItemRepository = wire[ListItemRepository]
  val databaseConfig = wire[DatabaseConfig]
}
