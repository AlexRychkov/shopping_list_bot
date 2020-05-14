package ru.shopping.telegram

import cats.effect.{IO, Resource, Sync}
import pureconfig._
import pureconfig.generic.auto._

final case class TelegramConfig(baseUri: String, token: String)

final case class BackendConfig(baseUri: String)

final case class FrontendConfig(url: String)

final case class AppConfig(telegram: TelegramConfig, backend: BackendConfig, frontend: FrontendConfig)

object AppConfig {
  def apply(): Resource[IO, AppConfig] = {
    Resource.liftF[IO, AppConfig](Sync[IO].delay(ConfigSource.default.loadOrThrow[AppConfig]))
  }
}