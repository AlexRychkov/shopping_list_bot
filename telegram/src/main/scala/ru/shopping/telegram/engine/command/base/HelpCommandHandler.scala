package ru.shopping.telegram.engine.command.base

import cats.effect.IO
import ru.shopping.telegram.api.message.MessageApi
import ru.shopping.telegram.api.update.Processable
import ru.shopping.telegram.engine.command.BotCommand.help
import ru.shopping.telegram.engine.command.mapper.HelpMapper
import ru.shopping.telegram.engine.command.{BotCommand, CommandHandler}

class HelpCommandHandler(private val messageApi: MessageApi) extends CommandHandler {
  override val processCommand: BotCommand = help
  private val helpText =
    """Use buttons to interact.
      |Commands:
      |  /lists - watch your lists
      |  /cabinet - link to web cabinet
      |  /help - help""".stripMargin

  override protected def handleInner(update: Processable): IO[Unit] = for {
    _ <- messageApi.sendMessage(HelpMapper(update.chatId, helpText))
  } yield ()
}