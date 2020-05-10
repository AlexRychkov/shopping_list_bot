package ru.shopping.telegram.engine

import cats.effect.IO
import cats.implicits._
import ru.shopping.telegram.api.update.{Update, UpdatesResult}

class UpdatesEngine(private val handlersService: HandlersService) {
  def process(updates: Update): IO[Unit] = {
    val processed = for {
      update <- updates.result
      _ = UpdateIdStorage.lastFetched(update.update_id)
      u = processUpdate(update)
    } yield u
    processed.sequence_
  }

  private def processUpdate(update: UpdatesResult): IO[Unit] =
    if (update.message.isDefined) handlersService.handle(update.message.get)
    else if (update.callback_query.isDefined) handlersService.handle(update.callback_query.get)
    else IO.unit
}

object UpdatesEngine {
  def apply(handlersService: HandlersService): UpdatesEngine = new UpdatesEngine(handlersService)
}