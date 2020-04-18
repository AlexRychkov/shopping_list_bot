package ru.shopping.config

import com.softwaremill.macwire.{Module, wire}
import ru.shopping.repository.ShoppingListRepository

@Module
class RepositoryConfig {
  lazy val shoppingListRepository = wire[ShoppingListRepository]
  val databaseConfig = wire[DatabaseConfig]
}
