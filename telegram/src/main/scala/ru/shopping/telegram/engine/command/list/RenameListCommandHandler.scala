package ru.shopping.telegram.engine.command.list

import cats.effect.IO
import ru.shopping.api.ListApi
import ru.shopping.telegram.api.message.MessageApi
import ru.shopping.telegram.api.update.{Message, Processable}
import ru.shopping.telegram.engine.command.BotCommand.lists
import ru.shopping.telegram.engine.command.mapper.ListMapper
import ru.shopping.telegram.engine.command.{BotCommand, CommandHandler}

class RenameListCommandHandler(private val messageApi: MessageApi,
                               private val listApi: ListApi) extends CommandHandler {
  override val processCommand: BotCommand = lists

  override protected def handleInner(update: Processable): IO[Unit] = update match {
    case message@Message(_, _, _, _, _, _) => for {
      lists <- listApi.lists(update.userId)
      reply = ListMapper(message.chatId, lists)
      _ <- messageApi.sendMessage(reply)
    } yield ()
    case _ => IO.unit
  }
}