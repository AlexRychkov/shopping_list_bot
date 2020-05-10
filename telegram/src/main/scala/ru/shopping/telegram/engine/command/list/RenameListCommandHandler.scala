package ru.shopping.telegram.engine.command.list

import cats.effect.IO
import ru.shopping.api.ListApi
import ru.shopping.common.models.ShoppingList
import ru.shopping.telegram.api.message.{ForceReply, MessageApi, Reply}
import ru.shopping.telegram.api.update.{AnswerCallbackQuery, CallbackQuery, Message, Processable}
import ru.shopping.telegram.engine.command.BotCommand.renameList
import ru.shopping.telegram.engine.command.mapper.ListMapper
import ru.shopping.telegram.engine.command.{BotCommand, CommandHandler}
import ru.shopping.telegram.engine.storage.AttachedListStorage

class RenameListCommandHandler(private val messageApi: MessageApi,
                               private val listApi: ListApi) extends CommandHandler {
  override val processCommand: BotCommand = renameList
  private val promptPhrase = "Enter new name for list"
  override val processReply: Option[String] = Some(promptPhrase)
  private val listIdRegex = """/renameList (\d+).*""".r

  override protected def handleInner(update: Processable): IO[Unit] = update match {
    case callbackQuery: CallbackQuery => handleQuery(callbackQuery)
    case message: Message => handleMessage(message)
    case _ => IO.unit
  }

  private def extractListId(command: String): ShoppingList.Id = command match {
    case listIdRegex(listId) => listId.toLong
    case _ => 0L
  }

  private def handleQuery(update: CallbackQuery): IO[Unit] = for {
    _ <- messageApi.answerCallbackQuery(AnswerCallbackQuery(update.id))
    listId = extractListId(update.command)
    _ = AttachedListStorage.put(update.userId, listId)
    reply = Reply(update.chatId, promptPhrase, Some(update.messageId), Some(ForceReply()))
    _ <- messageApi.sendMessage(reply)
  } yield ()

  private def handleMessage(update: Message): IO[Unit] =
    AttachedListStorage.get(update.userId).fold(IO.unit)(listId => for {
      _ <- listApi.rename(listId, update.text)
      lists <- listApi.lists(update.userId)
      reply = ListMapper(update.chatId, lists)
      _ <- messageApi.sendMessage(reply)
    } yield ())
}