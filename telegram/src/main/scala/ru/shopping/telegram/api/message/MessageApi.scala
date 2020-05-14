package ru.shopping.telegram.api.message

import cats.effect.IO
import org.http4s.Method._
import org.http4s.Request
import org.http4s.client.Client
import ru.shopping.telegram.api.TelegramApi
import ru.shopping.telegram.api.update.AnswerCallbackQuery

class MessageApi(private val client: Client[IO],
                 private val telegramApi: TelegramApi) {
  def sendMessage(message: Reply): IO[Unit] = {
    val req = Request(POST)
      .withEntity(message)
      .withUri(telegramApi.message)
    client.expect[Unit](req)
  }

  def answerCallbackQuery(answerCallbackQuery: AnswerCallbackQuery): IO[Unit] = {
    val req = Request(POST)
      .withEntity(answerCallbackQuery)
      .withUri(telegramApi.answerCallbackQuery)
    client.expect[Unit](req)
  }
}

object MessageApi {
  def apply(client: Client[IO], telegramApi: TelegramApi) = new MessageApi(client, telegramApi)
}