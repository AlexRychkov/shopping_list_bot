package ru.shopping.telegram.engine.command.mapper

import ru.shopping.common.models.ShoppingList
import ru.shopping.telegram.api.message.{InlineKeyboardButton, InlineKeyboardMarkup, Reply}
import ru.shopping.telegram.api.update.Chat

object ListMapper {
  private val addListButton = List(List(InlineKeyboardButton(s" ➕ Add list", s"/newList")))

  def apply(chatId: Chat.Id, lists: List[ShoppingList]): Reply = {
    val listsButtons = lists.map(el => List(
      InlineKeyboardButton(s"🔍 ${el.name}", s"/items ${el.id}"),
      InlineKeyboardButton(s"✏️ ", s"/renameList ${el.id}"),
      InlineKeyboardButton(s"❌ ", s"/deleteList ${el.id}")
    ))
    Reply(
      chat_id = chatId,
      text = if (lists.nonEmpty) "📄 Your lists" else " 🙅 You don't have any list, push button to add one",
      reply_markup = Some(InlineKeyboardMarkup(listsButtons ++ addListButton))
    )
  }
}