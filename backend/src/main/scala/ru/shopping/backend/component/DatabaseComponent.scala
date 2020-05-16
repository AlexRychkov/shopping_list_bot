package ru.shopping.backend.component

import com.softwaremill.macwire.{Module, wire}
import ru.shopping.backend.database.ApplicationJdbcContext

@Module
class DatabaseComponent {
  lazy val jdbcContext = wire[ApplicationJdbcContext]
}
