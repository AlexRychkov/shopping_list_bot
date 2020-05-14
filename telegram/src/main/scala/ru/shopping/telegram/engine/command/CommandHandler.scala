package ru.shopping.telegram.engine.command

import cats.effect.IO
import ru.shopping.telegram.api.update.Processable

trait CommandHandler {
  val processCommand: BotCommand
  val processReply: Option[String] = None

  final def handle(update: Processable): IO[Unit] = {
    val validation = validate(update)
    if (validation.isDefined) {
      // todo make error handling
    }
    handleInner(update)
  }

  def validate(update: Processable): Option[Error] = {
    println(update.command)
    None
  } // todo implement

  protected def handleInner(update: Processable): IO[Unit]

  trait Error

}