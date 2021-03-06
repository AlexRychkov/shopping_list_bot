package ru.shopping.telegram.engine

import cats.effect.IO
import ru.shopping.api.{AuthApi, ItemApi, ListApi}
import ru.shopping.telegram.AppConfig
import ru.shopping.telegram.api.message.MessageApi
import ru.shopping.telegram.api.update.Processable
import ru.shopping.telegram.engine.command._
import ru.shopping.telegram.engine.command.base.{CabinetCommandHandler, HelpCommandHandler, StartCommandHandler}
import ru.shopping.telegram.engine.command.item.{ItemsCommandHandler, MarkAbsentItemCommandHandler, MarkBoughtItemCommandHandler, MarkCanceledItemCommandHandler, MarkWaitItemCommandHandler, NewItemCommandHandler}
import ru.shopping.telegram.engine.command.list.{DeleteListCommandHandler, ListsCommandHandler, NewListCommandHandler, RenameListCommandHandler}

class HandlersService(private val commandProcessors: Map[BotCommand, CommandHandler],
                      private val replyProcessors: Map[String, CommandHandler],
                      private val unknownCommandHandler: UnknownCommandHandler) {
  def handle(processable: Processable): IO[Unit] = {
    val command = CommandParser.parse(processable)
    val handler = command.fold(
      replyProcessors.getOrElse(processable.command, unknownCommandHandler)
    )(commandProcessors.getOrElse(_, unknownCommandHandler))
    handler.handle(processable)
  }
}

object HandlersService {
  def apply(config: AppConfig, messageApi: MessageApi, listApi: ListApi, itemApi: ItemApi, authApi: AuthApi): HandlersService = {
    val unknownCommandHandler = new UnknownCommandHandler()
    val commandHandlers = Seq(
      new StartCommandHandler(messageApi),
      new HelpCommandHandler(messageApi),
      new ListsCommandHandler(messageApi, listApi),
      new NewListCommandHandler(messageApi, listApi),
      new RenameListCommandHandler(messageApi, listApi),
      new DeleteListCommandHandler(messageApi, listApi),
      new ItemsCommandHandler(messageApi, itemApi),
      new NewItemCommandHandler(messageApi, itemApi),
      new MarkAbsentItemCommandHandler(messageApi, itemApi),
      new MarkBoughtItemCommandHandler(messageApi, itemApi),
      new MarkCanceledItemCommandHandler(messageApi, itemApi),
      new MarkWaitItemCommandHandler(messageApi, itemApi),
      new CabinetCommandHandler(config, messageApi, authApi),
      unknownCommandHandler
    )
    val commandProcessors: Map[BotCommand, CommandHandler] = commandHandlers.map(ch => ch.processCommand -> ch).toMap
    val replyProcessors: Map[String, CommandHandler] = commandHandlers.filter(_.processReply.isDefined).map(ch => ch.processReply.get -> ch).toMap

    new HandlersService(commandProcessors, replyProcessors, unknownCommandHandler)
  }
}