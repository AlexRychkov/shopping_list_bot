package ru.shopping.backend.component

import com.softwaremill.macwire.{Module, wire}
import ru.shopping.backend.service.{AuthService, StatisticService}

@Module
class ServiceComponent(repositoryComponent: RepositoryComponent) {
  import repositoryComponent._

  lazy val authService = wire[AuthService]
  lazy val statisticService = wire[StatisticService]
}