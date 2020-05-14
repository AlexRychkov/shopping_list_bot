package ru.shopping.telegram.engine.command.base

import cats.effect.IO
import ru.shopping.telegram.api.message.MessageApi
import ru.shopping.telegram.api.update.Processable
import ru.shopping.telegram.engine.command.BotCommand.start
import ru.shopping.telegram.engine.command.mapper.HelpMapper
import ru.shopping.telegram.engine.command.{BotCommand, CommandHandler}

class StartCommandHandler(private val messageApi: MessageApi) extends CommandHandler {
  override val processCommand: BotCommand = start
  private val greetingText = "Hi! I am shopping list bot. You can trust to me serve your shopping lists."

  override protected def handleInner(update: Processable): IO[Unit] = for {
    _ <- messageApi.sendMessage(HelpMapper(update.chatId, greetingText))
  } yield ()
}
