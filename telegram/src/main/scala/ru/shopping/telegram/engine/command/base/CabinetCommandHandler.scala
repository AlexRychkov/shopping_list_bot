package ru.shopping.telegram.engine.command.base

import cats.effect.IO
import ru.shopping.api.AuthApi
import ru.shopping.telegram.AppConfig
import ru.shopping.telegram.api.message.Reply.MD2
import ru.shopping.telegram.api.message.{MessageApi, Reply}
import ru.shopping.telegram.api.update.{Message, Processable}
import ru.shopping.telegram.engine.command.BotCommand.cabinet
import ru.shopping.telegram.engine.command.mapper.HelpMapper
import ru.shopping.telegram.engine.command.{BotCommand, CommandHandler}

class CabinetCommandHandler(private val config: AppConfig,
                            private val messageApi: MessageApi,
                            private val authApi: AuthApi) extends CommandHandler {
  override val processCommand: BotCommand = cabinet
  override val processReply: Option[String] = Some(HelpMapper.goToCabinetPhrase)
  private val someFalse = Some(false)
  private val markDownV2 = Some(MD2)

  override protected def handleInner(update: Processable): IO[Unit] = update match {
    case message: Message => for {
      token <- authApi.requestToken(update.userId)
      url = s"${config.frontend.url}login/${token.drop(1).dropRight(1)}/"
      reply = Reply(
        chat_id = message.chatId,
        text = s"Your [link]($url) *expired* in one minute 😉",
        parse_mode = markDownV2,
        disable_web_page_preview = someFalse
      )
      _ <- messageApi.sendMessage(reply)
    } yield ()
    case _ => IO.unit
  }
}