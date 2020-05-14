package ru.shopping.telegram.engine.command

import cats.effect.IO
import ru.shopping.telegram.api.update.Processable
import ru.shopping.telegram.engine.command.BotCommand.unknown

class UnknownCommandHandler extends CommandHandler {
  override val processCommand: BotCommand = unknown

  override protected def handleInner(update: Processable): IO[Unit] = IO.unit
}