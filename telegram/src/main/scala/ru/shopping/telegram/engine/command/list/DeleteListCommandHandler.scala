package ru.shopping.telegram.engine.command.list

import cats.effect.IO
import ru.shopping.api.ListApi
import ru.shopping.common.models.ShoppingList
import ru.shopping.telegram.api.message.MessageApi
import ru.shopping.telegram.api.update.{AnswerCallbackQuery, CallbackQuery, Processable}
import ru.shopping.telegram.engine.command.BotCommand.deleteList
import ru.shopping.telegram.engine.command.mapper.ListMapper
import ru.shopping.telegram.engine.command.{BotCommand, CommandHandler}

class DeleteListCommandHandler(private val messageApi: MessageApi,
                               private val listApi: ListApi) extends CommandHandler {
  override val processCommand: BotCommand = deleteList
  private val listIdRegex = """/deleteList (\d+).*""".r

  override protected def handleInner(update: Processable): IO[Unit] = update match {
    case callbackQuery: CallbackQuery => handleQuery(callbackQuery)
    case _ => IO.unit
  }

  private def handleQuery(update: CallbackQuery): IO[Unit] = for {
    _ <- messageApi.answerCallbackQuery(AnswerCallbackQuery(update.id))
    listId = extractListId(update.command)
    _ <- listApi.delete(listId)
    lists <- listApi.lists(update.userId)
    reply = ListMapper(update.chatId, lists)
    _ <- messageApi.sendMessage(reply)
  } yield ()

  private def extractListId(command: String): ShoppingList.Id = command match {
    case listIdRegex(listId) => listId.toLong
    case _ => 0L
  }
}