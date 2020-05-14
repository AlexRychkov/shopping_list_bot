package ru.shopping.telegram.engine.command.item

import ru.shopping.api.ItemApi
import ru.shopping.common.models.ItemMark
import ru.shopping.common.models.ItemMark.Absent
import ru.shopping.telegram.api.message.MessageApi
import ru.shopping.telegram.engine.command.BotCommand
import ru.shopping.telegram.engine.command.BotCommand.markAbsent

class MarkAbsentItemCommandHandler(override val messageApi: MessageApi,
                                   override val itemApi: ItemApi) extends MarkItemCommandHandler {
  override val processCommand: BotCommand = markAbsent
  override protected val mark: ItemMark = Absent
}