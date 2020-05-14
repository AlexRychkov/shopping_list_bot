package ru.shopping.telegram.engine.command.mapper

import ru.shopping.telegram.api.message.{KeyboardButton, Reply, ReplyKeyboardMarkup}
import ru.shopping.telegram.api.update.Chat

object HelpMapper {
  val showListsPhrase = "📄 Show lists"
  private val showListsButton = Some(ReplyKeyboardMarkup(List(List(KeyboardButton(showListsPhrase)))))

  def apply(chatId: Chat.Id, text: String) = Reply(
    chat_id = chatId,
    text = text,
    reply_markup = showListsButton
  )
}