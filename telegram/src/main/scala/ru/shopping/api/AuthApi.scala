package ru.shopping.api

import cats.effect.IO
import org.http4s.client.Client
import ru.shopping.common.models.User

class AuthApi(private val client: Client[IO], private val backendApi: BackendApi) {
  def requestToken(userId: User.Id): IO[String] = {
    val uri = backendApi.auth(userId)
    client.expect[String](uri)
  }
}

object AuthApi {
  def apply(client: Client[IO], backendApi: BackendApi): AuthApi = new AuthApi(client, backendApi)
}