package ru.shopping.telegram.engine.command.list

import cats.effect.IO
import ru.shopping.api.ListApi
import ru.shopping.telegram.api.message.MessageApi
import ru.shopping.telegram.api.update.{Message, Processable}
import ru.shopping.telegram.engine.command.BotCommand.lists
import ru.shopping.telegram.engine.command.mapper.{HelpMapper, ListMapper}
import ru.shopping.telegram.engine.command.{BotCommand, CommandHandler}
import ru.shopping.telegram.engine.storage.ListInfoStorage

class ListsCommandHandler(private val messageApi: MessageApi,
                          private val listApi: ListApi) extends CommandHandler {
  override val processCommand: BotCommand = lists
  override val processReply: Option[String] = Some(HelpMapper.showListsPhrase)

  override protected def handleInner(update: Processable): IO[Unit] = update match {
    case message: Message => for {
      lists <- listApi.lists(update.userId)
      _ = ListInfoStorage.put(lists)
      reply = ListMapper(message.chatId, lists)
      _ <- messageApi.sendMessage(reply)
    } yield ()
    case _ => IO.unit
  }
}