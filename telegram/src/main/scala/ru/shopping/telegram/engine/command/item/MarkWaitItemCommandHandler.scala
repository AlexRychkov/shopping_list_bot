package ru.shopping.telegram.engine.command.item

import ru.shopping.api.ItemApi
import ru.shopping.common.models.ItemMark
import ru.shopping.common.models.ItemMark.Wait
import ru.shopping.telegram.api.message.MessageApi
import ru.shopping.telegram.engine.command.BotCommand
import ru.shopping.telegram.engine.command.BotCommand.markWait

class MarkWaitItemCommandHandler(override val messageApi: MessageApi,
                                 override val itemApi: ItemApi) extends MarkItemCommandHandler {
  override val processCommand: BotCommand = markWait
  override protected val mark: ItemMark = Wait
}