package ru.shopping.telegram.engine.command.list

import cats.effect.IO
import ru.shopping.api.ListApi
import ru.shopping.telegram.api.message.{ForceReply, MessageApi, Reply}
import ru.shopping.telegram.api.update.{AnswerCallbackQuery, CallbackQuery, Message, Processable}
import ru.shopping.telegram.engine.command.BotCommand.newList
import ru.shopping.telegram.engine.command.mapper.ListMapper
import ru.shopping.telegram.engine.command.{BotCommand, CommandHandler}

class NewListCommandHandler(private val messageApi: MessageApi,
                            private val shoppingApi: ListApi) extends CommandHandler {
  override val processCommand: BotCommand = newList
  private val promptPhrase = "Enter new list name"
  override val processReply: Option[String] = Some(promptPhrase)
  private val listName = """/newList (.*)""".r
  private val list = """/newList[\s]*""".r
  private val name = """.+""".r

  override def handleInner(update: Processable): IO[Unit] = update match {
    case callbackQuery: CallbackQuery => handleQuery(callbackQuery)
    case message: Message => handleMessage(message)
    case _ => IO.unit
  }

  private def handleQuery(update: CallbackQuery) = for {
    _ <- messageApi.answerCallbackQuery(AnswerCallbackQuery(update.id))
    _ <- askListName(update)
  } yield ()

  private def askListName(update: Processable) = for {
    _ <- messageApi.sendMessage(Reply(update.chatId, promptPhrase, Some(update.messageId), Some(ForceReply())))
  } yield ()

  private def handleMessage(update: Message) = update.command match {
    case listName(name) => createList(update, name)
    case name() => createList(update, update.text)
    case list() => askListName(update)
    case _ => IO.unit
  }

  private def createList(update: Message, name: String) = for {
    _ <- shoppingApi.create(update.userId, name)
    lists <- shoppingApi.lists(update.userId)
    reply = ListMapper(update.chatId, lists)
    _ <- messageApi.sendMessage(reply)
  } yield ()
}