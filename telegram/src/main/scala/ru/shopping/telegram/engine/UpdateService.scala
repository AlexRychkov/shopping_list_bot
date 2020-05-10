package ru.shopping.telegram.engine

import java.util.concurrent.TimeUnit

import cats.effect.{IO, Timer}
import ru.shopping.telegram.api.update.UpdateApi

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.FiniteDuration

class UpdateService(val executionContext: ExecutionContext,
                    private val updateRepository: UpdateApi,
                    private val updatesEngine: UpdatesEngine) {
  private implicit val timer: Timer[IO] = IO.timer(executionContext)
  private val oneSecond = FiniteDuration.apply(1000, TimeUnit.MILLISECONDS)

  def loop(): IO[Unit] = IO.suspend {
    updateRepository.updates()
      .flatMap(upd => updatesEngine.process(upd))
      .flatMap(_ => {
        IO.sleep(oneSecond)
        loop()
      })
  }
}

object UpdateService {
  def apply(executionContext: ExecutionContext, updateRepository: UpdateApi, updatesEngine: UpdatesEngine): UpdateService =
    new UpdateService(executionContext, updateRepository, updatesEngine)
}