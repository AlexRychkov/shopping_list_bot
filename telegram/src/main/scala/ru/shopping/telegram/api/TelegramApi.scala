package ru.shopping.telegram.api

import org.http4s.Uri
import ru.shopping.telegram.TelegramConfig

class TelegramApi(private val telegramConfig: TelegramConfig) {
  private val baseUri = Uri.unsafeFromString(telegramConfig.baseUri) / telegramConfig.token
  val message: Uri = baseUri / "sendMessage"
  val answerCallbackQuery: Uri = baseUri / "answerCallbackQuery"

  def updates(from: Long): Uri = baseUri / "getUpdates" =? Map("offset" -> List(from))
}

object TelegramApi {
  def apply(telegramConfig: TelegramConfig): TelegramApi = new TelegramApi(telegramConfig)
}