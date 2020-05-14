package ru.shopping.backend.controller.middleware

import cats.data._
import cats.effect._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._
import org.http4s.server.AuthMiddleware
import ru.shopping.backend.util.Jwt
import ru.shopping.common.dto.UserAuth

object HeaderAuthMiddleware {
  private val authUser: Kleisli[IO, Request[IO], Either[String, UserAuth]] = Kleisli({ request =>
    val header = request.headers.toList.find(h => h.name.value == "Authorization")
    val user = for {
      header <- header.toRight("Couldn't find an Authorization header")
      user <- Jwt.userAuth(header.value).toRight("Invalid token")
    } yield user
    IO.pure(user)
  })

  private val onFailure: AuthedRoutes[String, IO] = Kleisli(req => OptionT.liftF(Forbidden(req.context)))
  private val authMiddleware: AuthMiddleware[IO, UserAuth] = AuthMiddleware(authUser, onFailure)

  def apply(authRoutes: AuthedRoutes[UserAuth, IO]) = authMiddleware(authRoutes)
}
