package ru.shopping.telegram.api.update

import cats.effect.IO
import org.http4s.Uri
import org.http4s.client.Client
import ru.shopping.telegram.api.TelegramApi
import ru.shopping.telegram.engine.storage.UpdateIdStorage

class UpdateApi(private val client: Client[IO],
                private val telegramApi: TelegramApi) {
  def updates(): IO[Update] = client.expect[Update](updateUrl())

  private def updateUrl(): Uri = telegramApi.updates(UpdateIdStorage.next)
}

object UpdateApi {
  def apply(client: Client[IO], telegramApi: TelegramApi) = new UpdateApi(client, telegramApi: TelegramApi)
}