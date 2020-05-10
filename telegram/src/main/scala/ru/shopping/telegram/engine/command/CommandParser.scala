package ru.shopping.telegram.engine.command

import ru.shopping.telegram.api.update.Processable

object CommandParser {
  private val commandRegex = """/(\w+).*""".r

  def parse(processable: Processable): Option[BotCommand] = processable.command match {
    case commandRegex(cmd) => BotCommand.withNameOption(cmd)
    case _ => None
  }
}