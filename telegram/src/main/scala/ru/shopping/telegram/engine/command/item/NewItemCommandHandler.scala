package ru.shopping.telegram.engine.command.item

import cats.effect.IO
import ru.shopping.api.ItemApi
import ru.shopping.telegram.api.message.{ForceReply, MessageApi, Reply}
import ru.shopping.telegram.api.update.{AnswerCallbackQuery, CallbackQuery, Message, Processable}
import ru.shopping.telegram.engine.command.BotCommand.newItem
import ru.shopping.telegram.engine.command.mapper.ItemMapper
import ru.shopping.telegram.engine.command.{BotCommand, CommandHandler}
import ru.shopping.telegram.engine.storage.AttachedListStorage

class NewItemCommandHandler(private val messageApi: MessageApi,
                            private val itemApi: ItemApi) extends CommandHandler {
  override val processCommand: BotCommand = newItem
  private val promptPhrase = "Enter new item name"
  override val processReply: Option[String] = Some(promptPhrase)
  private val listId = """/newItem (\d+).*""".r
  private val name = """.+""".r

  override def handleInner(update: Processable): IO[Unit] = {
    update.command match {
      case listId(id) => AttachedListStorage.put(update.userId, id.toLong)
      case _ =>
    }
    update match {
      case callbackQuery@CallbackQuery(_, _, _, _) => handleQuery(callbackQuery)
      case message@Message(_, _, _, _, _, _) => handleMessage(message)
      case _ => IO.unit
    }
  }

  private def handleQuery(update: CallbackQuery) = for {
    _ <- messageApi.answerCallbackQuery(AnswerCallbackQuery(update.id))
    _ <- askItemName(update)
  } yield ()

  private def askItemName(update: Processable) = for {
    _ <- messageApi.sendMessage(Reply(update.chatId, promptPhrase, Some(update.messageId), Some(ForceReply())))
  } yield ()

  private def handleMessage(update: Message) = update.command match {
    case name() => addItem(update)
    case _ => IO.unit
  }

  private def addItem(update: Message) = {
    val listId = AttachedListStorage.get(update.userId)
    listId.fold(IO.unit)(id => for {
      _ <- itemApi.addItem(id, update.text)
      items <- itemApi.items(id)
      reply = ItemMapper(update.chatId, items)
      _ <- messageApi.sendMessage(reply)
    } yield ())
  }
}