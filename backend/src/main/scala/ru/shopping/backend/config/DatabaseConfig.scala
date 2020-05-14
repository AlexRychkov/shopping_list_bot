package ru.shopping.backend.config

import com.softwaremill.macwire.{Module, wire}
import ru.shopping.backend.database.ApplicationJdbcContext

@Module
class DatabaseConfig {
  lazy val jdbcContext = wire[ApplicationJdbcContext]
}
