package ru.shopping.common.models

import enumeratum.{Enum, EnumEntry}
import io.circe.{Decoder, Encoder, HCursor, Json}

sealed trait ItemMark extends EnumEntry {
  val emoji: String
  val order: Int
}

object ItemMark extends Enum[ItemMark] {
  override def values: IndexedSeq[ItemMark] = findValues

  case object Wait extends ItemMark {
    override val emoji: String = " ⏳ "
    override val order: Int = 0
  }

  case object Absent extends ItemMark {
    override val emoji: String = " 🤷 "
    override val order: Int = 1
  }

  case object Bought extends ItemMark {
    override val emoji: String = " ✅ "
    override val order: Int = 2
  }

  case object Canceled extends ItemMark {
    override val emoji: String = " ❌ "
    override val order: Int = 3
  }

  implicit val encoder: Encoder[ItemMark] = (mark: ItemMark) => Json.fromString(mark.entryName)
  implicit val decoder: Decoder[ItemMark] = (cursor: HCursor) => for {
    mark <- cursor.value.as[String]
  } yield ItemMark.withNameOption(mark).get
}