package ru.shopping.telegram.engine.command.item

import cats.effect.IO
import ru.shopping.api.ItemApi
import ru.shopping.telegram.api.message.MessageApi
import ru.shopping.telegram.api.update.{AnswerCallbackQuery, CallbackQuery, Processable}
import ru.shopping.telegram.engine.command.BotCommand.items
import ru.shopping.telegram.engine.command.mapper.ItemMapper
import ru.shopping.telegram.engine.command.{BotCommand, CommandHandler}
import ru.shopping.telegram.engine.storage.AttachedListStorage

class ItemsCommandHandler(private val messageApi: MessageApi,
                          private val itemApi: ItemApi) extends CommandHandler {
  override val processCommand: BotCommand = items
  private val itemsRegex = """/items (\d+)""".r

  override protected def handleInner(update: Processable): IO[Unit] = update match {
    case callbackQuery: CallbackQuery => for {
      _ <- messageApi.answerCallbackQuery(AnswerCallbackQuery(callbackQuery.id))
      listId = extractListId(update)
      _ = AttachedListStorage.put(update.userId, listId)
      items <- itemApi.items(listId)
      reply = ItemMapper(callbackQuery.chatId, items)
      _ <- messageApi.sendMessage(reply)
    } yield ()
    case _ => IO.unit
  }

  private def extractListId(update: Processable): Long = update.command match {
    case itemsRegex(listId) => listId.toLong
    case _ => 0
  }
}