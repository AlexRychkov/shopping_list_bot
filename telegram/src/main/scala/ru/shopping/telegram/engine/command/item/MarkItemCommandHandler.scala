package ru.shopping.telegram.engine.command.item

import cats.effect.IO
import ru.shopping.api.ItemApi
import ru.shopping.common.models.{ItemMark, ShoppingList}
import ru.shopping.telegram.api.message.MessageApi
import ru.shopping.telegram.api.update.{AnswerCallbackQuery, CallbackQuery, Processable}
import ru.shopping.telegram.engine.command.CommandHandler
import ru.shopping.telegram.engine.command.mapper.ItemMapper
import ru.shopping.telegram.engine.storage.AttachedListStorage

import scala.util.matching.Regex

trait MarkItemCommandHandler extends CommandHandler {
  protected val itemApi: ItemApi
  protected val messageApi: MessageApi
  protected val mark: ItemMark
  protected lazy val markRegexInline: String = mark.toString
  protected lazy val actionText: String = markRegexInline.toLowerCase()
  protected lazy val itemIdRegex: Regex = s"""/mark${markRegexInline} (\\d+).*""".r

  override protected def handleInner(update: Processable): IO[Unit] = update match {
    case callbackQuery: CallbackQuery => handleQuery(callbackQuery)
    case _ => IO.unit
  }

  private def extractItemId(command: String): ShoppingList.Id = command match {
    case itemIdRegex(listId) => listId.toLong
    case _ => 0L
  }

  private def handleQuery(update: CallbackQuery): IO[Unit] = {
    val itemId = extractItemId(update.command)
    AttachedListStorage.get(update.userId).fold(IO.unit)(listId => for {
      item <- itemApi.mark(listId, itemId, mark)
      text = s"""You mark "$actionText" item "${item.name}""""
      _ <- messageApi.answerCallbackQuery(AnswerCallbackQuery(update.id, Some(text)))
      items <- itemApi.items(listId)
      reply = ItemMapper(update.chatId, items)
      _ <- messageApi.sendMessage(reply)
    } yield ())
  }
}