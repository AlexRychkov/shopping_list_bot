package ru.shopping.telegram.api.update

import cats.effect.IO
import io.circe.Printer
import io.circe.generic.auto._
import org.http4s.circe._
import org.http4s.{EntityDecoder, EntityEncoder}
import ru.shopping.telegram.api.update.Message.Id

object Update {
  implicit val updatesEntityDecoder: EntityDecoder[IO, Update] = jsonOf[IO, Update]
}

final case class Update(ok: Boolean, result: List[UpdatesResult])

final case class UpdatesResult(update_id: Long, message: Option[Message], callback_query: Option[CallbackQuery])

trait From {
  val from: User
  val userId: ru.shopping.common.models.User.Id = from.id
}

trait Processable extends From {
  val chatId: Long
  val messageId: Message.Id

  def command: String
}

final case class CallbackQuery(id: String, from: User, message: Message, data: String) extends From with Processable {
  override val command: String = data
  override val chatId: Long = message.chat.id
  override val messageId: Id = message.message_id
}

final case class Message(message_id: Message.Id, from: User, chat: Chat, text: String, reply_to_message: Option[Message], entities: Option[List[Entity]]) extends From with Processable {
  override val chatId: Long = chat.id
  override val messageId: Id = message_id

  override def command: String = reply_to_message.fold(text)(_.text)
}

object Message {
  type Id = Int
}

final case class Entity(offset: Int, length: Int, `type`: String)

final case class User(id: Long, is_bot: Boolean)

final case class Chat(id: Chat.Id, `type`: String)

object Chat {
  type Id = Long
}

final case class AnswerCallbackQuery(callback_query_id: String, text: Option[String] = None, show_alert: Option[Boolean] = None)

object AnswerCallbackQuery {
  private val AnswerCallbackQueryPrinter = Printer.noSpaces.copy(dropNullValues = true)
  implicit val AnswerCallbackQueryEncoder: EntityEncoder[IO, AnswerCallbackQuery] =
    jsonEncoderWithPrinterOf[IO, AnswerCallbackQuery](AnswerCallbackQueryPrinter)
}
