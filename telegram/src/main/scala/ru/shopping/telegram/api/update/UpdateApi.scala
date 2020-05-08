package ru.shopping.telegram.api.update

import cats.effect.IO
import org.http4s.Uri
import org.http4s.client.Client
import ru.shopping.telegram.api.Api
import ru.shopping.telegram.engine.storage.UpdateIdStorage

class UpdateApi(private val client: Client[IO]) {
  def updates(): IO[Update] = client.expect[Update](updateUrl())

  private def updateUrl(): Uri = Api.updates(UpdateIdStorage.next)
}

object UpdateApi {
  def apply(client: Client[IO]) = new UpdateApi(client)
}