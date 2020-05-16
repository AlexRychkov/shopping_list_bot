package ru.shopping.backend.controller

import cats.effect._
import io.circe.generic.auto._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._
import org.http4s.{AuthedRoutes, HttpRoutes}
import ru.shopping.backend.controller.middleware.HeaderAuthMiddleware
import ru.shopping.backend.service.StatisticService

class StatisticController(private val statisticService: StatisticService) extends Controller {
  override val version: String = "v1"
  override val rootPath: String = "statistic/"

  private val authedRoutes = HeaderAuthMiddleware(AuthedRoutes.of {
    case GET -> Root / "created" / IntVar(period) as user => Ok(statisticService.fromPeriodUntilNow(user.id, period))

    case GET -> Root / "total" / IntVar(total) / IntVar(period) as user => Ok(statisticService.fromPeriodUntilNowForMark(user.id, period, total))
  })

  override val controller: HttpRoutes[IO] = authedRoutes
}



