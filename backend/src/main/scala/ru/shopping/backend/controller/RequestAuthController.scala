package ru.shopping.backend.controller

import cats.effect._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._
import ru.shopping.backend.service.AuthService

class RequestAuthController(private val authService: AuthService) extends Controller {
  override val version: String = "v1"
  override val rootPath: String = "auth/req/"

  override val controller: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / LongVar(userId) => Ok(authService.generateLimitToken(userId))
  }
}