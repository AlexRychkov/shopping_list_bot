package ru.shopping.backend.service

import ru.shopping.backend.util.Jwt
import ru.shopping.common.dto.UserAuth
import ru.shopping.common.models.User

class AuthService {
  def generateLimitToken(userId: User.Id): String = Jwt.generate(UserAuth(userId), Some(60))

  def exchangeToken(userAuth: UserAuth): String = Jwt.generate(userAuth)

  def isValid(token: String): Boolean = Jwt.isValid(token)
}
