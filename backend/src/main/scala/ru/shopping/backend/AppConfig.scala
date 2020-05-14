package ru.shopping.backend

import com.softwaremill.macwire.Module
import pureconfig._
import pureconfig.generic.auto._

final case class Jwt(secret: String)

final case class AppConfig(jwt: Jwt)

@Module
object AppConfig {
  private lazy val config = ConfigSource.default.loadOrThrow[AppConfig]

  def apply(): AppConfig = config
}