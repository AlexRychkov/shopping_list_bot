package ru.shopping.telegram.api.message

import cats.effect.IO
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.{Encoder, Printer}
import org.http4s.EntityEncoder
import org.http4s.circe._
import ru.shopping.telegram.api.update.Message

sealed trait ReplyMarkup

final case class ForceReply(force_reply: Boolean = true, selective: Boolean = false) extends ReplyMarkup

final case class KeyboardButton(text: String)

final case class ReplyKeyboardMarkup(keyboard: List[List[KeyboardButton]], one_time_keyboard: Option[Boolean] = None) extends ReplyMarkup

final case class InlineKeyboardButton(text: String, callback_data: String)

final case class InlineKeyboardMarkup(inline_keyboard: List[List[InlineKeyboardButton]]) extends ReplyMarkup

final case class Reply(chat_id: Long, text: String, reply_to_message_id: Option[Message.Id] = None, reply_markup: Option[ReplyMarkup] = None, parse_mode: Option[String] = None)

object Reply {
  val HTML = "html"
  val MD2 = "MarkdownV2"

  implicit val encodeReplyMarkup: Encoder[ReplyMarkup] = Encoder.instance {
    case forceReply@ForceReply(_, _) => forceReply.asJson
    case replyKeyboardMarkup@ReplyKeyboardMarkup(_, _) => replyKeyboardMarkup.asJson
    case inlineKeyboardMarkup@InlineKeyboardMarkup(_) => inlineKeyboardMarkup.asJson
  }
  private val replyPrinter = Printer.noSpaces.copy(dropNullValues = true)
  implicit val replyEncoder: EntityEncoder[IO, Reply] = jsonEncoderWithPrinterOf[IO, Reply](replyPrinter)
}