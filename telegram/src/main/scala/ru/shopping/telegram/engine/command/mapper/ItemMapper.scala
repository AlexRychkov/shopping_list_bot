package ru.shopping.telegram.engine.command.mapper

import ru.shopping.common.models.{ItemMark, ListItem}
import ru.shopping.telegram.api.message.Reply.MD2
import ru.shopping.telegram.api.message.{InlineKeyboardButton, InlineKeyboardMarkup, Reply}
import ru.shopping.telegram.api.update.Chat
import ru.shopping.telegram.engine.storage.ListInfoStorage

object ItemMapper {
  def apply(chatId: Chat.Id, items: List[ListItem]): Reply = {
    val itemsButtons = items.sortBy(_.mark.order).flatMap(item => List(
      List(InlineKeyboardButton(s" ${item.mark.emoji} ${item.name}", s"${item.id}")),
      List(
        InlineKeyboardButton(ItemMark.Absent.emoji, s"/markAbsent ${item.id}"),
        InlineKeyboardButton(ItemMark.Bought.emoji, s"/markBought ${item.id}"),
        InlineKeyboardButton(ItemMark.Canceled.emoji, s"/markCanceled ${item.id}"),
        InlineKeyboardButton(ItemMark.Wait.emoji, s"/markWait ${item.id}")
      ).filter(_.text != item.mark.emoji)
    ))
    val listId = if (items.nonEmpty) Some(items.head.listId) else None
    val listName = listId.fold("This")(id => ListInfoStorage.get(id).fold("This")(_.name))
    val addItemButton = List(List(InlineKeyboardButton(" ➕ Add item", s"/newItem $listId")))
    Reply(
      chat_id = chatId,
      text = if (items.nonEmpty) s"📝 *$listName* list" else s"❗ *$listName* list is empty",
      reply_markup = Some(InlineKeyboardMarkup(itemsButtons ++ addItemButton)),
      parse_mode = Some(MD2)
    )
  }
}