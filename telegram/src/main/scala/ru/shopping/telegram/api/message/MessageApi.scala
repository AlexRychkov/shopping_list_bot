package ru.shopping.telegram.api.message

import cats.effect.IO
import org.http4s.Method._
import org.http4s.Request
import org.http4s.client.Client
import ru.shopping.telegram.api.Api
import ru.shopping.telegram.api.update.AnswerCallbackQuery

class MessageApi(private val client: Client[IO]) {
  def sendMessage(message: Reply): IO[Unit] = {
    val req = Request(POST)
      .withEntity(message)
      .withUri(Api.message)
    client.expect[Unit](req)
  }

  def answerCallbackQuery(answerCallbackQuery: AnswerCallbackQuery): IO[Unit] = {
    val req = Request(POST)
      .withEntity(answerCallbackQuery)
      .withUri(Api.answerCallbackQuery)
    client.expect[Unit](req)
  }
}

object MessageApi {
  def apply(client: Client[IO]) = new MessageApi(client)
}