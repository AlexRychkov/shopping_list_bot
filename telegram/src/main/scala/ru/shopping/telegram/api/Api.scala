package ru.shopping.telegram.api

import org.http4s.Uri
import org.http4s.implicits._

private[api] object Api {
  val message: Uri = baseApi / "sendMessage"
  val answerCallbackQuery: Uri = baseApi / "answerCallbackQuery"
  private val token = s"xxx"
  private val baseApi = uri"https://api.telegram.org" / token

  def updates(from: Long): Uri = baseApi / "getUpdates" =? Map("offset" -> List(from))
}
