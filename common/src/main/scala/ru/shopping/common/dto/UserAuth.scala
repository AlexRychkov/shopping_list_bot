package ru.shopping.common.dto

import ru.shopping.common.models.User
import io.circe._, io.circe.generic.semiauto._

case class UserAuth(id: User.Id)

object UserAuth {
  implicit val userAuthDecoder: Decoder[UserAuth] = deriveDecoder[UserAuth]
  implicit val userAuthEncoder: Encoder[UserAuth] = deriveEncoder[UserAuth]
}