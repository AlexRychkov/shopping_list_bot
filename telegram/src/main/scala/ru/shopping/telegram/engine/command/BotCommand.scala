package ru.shopping.telegram.engine.command

import enumeratum.{Enum, EnumEntry}

sealed trait BotCommand extends EnumEntry

object BotCommand extends Enum[BotCommand] {

  override def values: IndexedSeq[BotCommand] = findValues

  case object start extends BotCommand

  case object help extends BotCommand

  case object unknown extends BotCommand

  case object lists extends BotCommand

  case object newList extends BotCommand

  case object renameList extends BotCommand

  case object items extends BotCommand

  case object newItem extends BotCommand

}