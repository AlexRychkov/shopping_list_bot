package ru.shopping.backend.component

import com.softwaremill.macwire.{Module, wire}
import ru.shopping.backend.service.AuthService

@Module
class ServiceComponent {
  lazy val authService = wire[AuthService]
}
