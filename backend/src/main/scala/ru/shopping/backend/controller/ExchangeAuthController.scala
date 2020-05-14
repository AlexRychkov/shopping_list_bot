package ru.shopping.backend.controller

import cats.effect._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._
import ru.shopping.backend.controller.middleware.HeaderAuthMiddleware
import ru.shopping.backend.service.AuthService

class ExchangeAuthController(private val authService: AuthService) extends Controller {
  override val version: String = "v1"
  override val rootPath: String = "auth/exch/"

  private val authRoutes = HeaderAuthMiddleware(AuthedRoutes.of {
    case GET -> Root as user => Ok(authService.exchangeToken(user))
  })

  override val controller: HttpRoutes[IO] = authRoutes
}