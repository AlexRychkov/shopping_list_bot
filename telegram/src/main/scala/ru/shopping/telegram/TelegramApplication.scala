package ru.shopping.telegram

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.client.middleware.Logger
import ru.shopping.api.{BackendApi, ItemApi, ListApi}
import ru.shopping.telegram.api.TelegramApi
import ru.shopping.telegram.api.message.MessageApi
import ru.shopping.telegram.api.update.UpdateApi
import ru.shopping.telegram.engine.{HandlersService, UpdateService, UpdatesEngine}

import scala.concurrent.ExecutionContext.global

object TelegramApplication extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    val updateService = for {
      config <- AppConfig()
      telegramApi = TelegramApi(config.telegram)
      backendApi = BackendApi(config.backend)
      client <- BlazeClientBuilder[IO](global).resource.map(client => Logger(logHeaders = false, logBody = true)(client))
      messageApi = MessageApi(client, telegramApi)
      shoppingApi = ListApi(client, backendApi)
      itemApi = ItemApi(client, backendApi)
      handlerService = HandlersService(messageApi, shoppingApi, itemApi)
      updatesEngine = UpdatesEngine(handlerService)
      updatesApi = UpdateApi(client, telegramApi)
      updateService = UpdateService(global, updatesApi, updatesEngine)
    } yield updateService

    updateService
      .use { update => update.loop() }
      .map { _ => ExitCode.Success }
  }
}
