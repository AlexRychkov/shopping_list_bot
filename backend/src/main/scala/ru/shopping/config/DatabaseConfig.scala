package ru.shopping.config

import com.softwaremill.macwire.{Module, wire}
import ru.shopping.database.ApplicationJdbcContext

@Module
class DatabaseConfig {
  lazy val jdbcContext = wire[ApplicationJdbcContext]
}
