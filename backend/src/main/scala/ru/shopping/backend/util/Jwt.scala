package ru.shopping.backend.util

import java.time.Instant
import java.time.temporal.ChronoUnit

import io.circe.syntax._
import pdi.jwt.{JwtAlgorithm, JwtCirce, JwtClaim, Jwt => Jwtc}
import ru.shopping.backend.AppConfig
import ru.shopping.common.dto.UserAuth
import io.circe.generic.auto._, io.circe.jawn.decode
import scala.util.{Failure, Success}

object Jwt {
  private val key = AppConfig().jwt.secret
  private val algo = JwtAlgorithm.HS256
  private val seqAlgo = Seq(algo)

  def generate(auth: UserAuth, expirationAfterSecond: Option[Long] = None): String = {
    val claim = JwtClaim(
      content = auth.asJson.toString(),
      expiration = expirationAfterSecond.map(it => Instant.now().plus(it, ChronoUnit.SECONDS).getEpochSecond)
    )
    JwtCirce.encode(claim, key, algo)
  }

  def isValid(token: String): Boolean = Jwtc.isValid(token, key, seqAlgo)

  def userAuth(token: String): Option[UserAuth] = JwtCirce.decode(token, key, seqAlgo) match {
    case Failure(_) => None
    case Success(value) => decode[UserAuth](value.content).fold(_ => None, u => Some(u))
  }
}
