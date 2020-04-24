package ru.shopping.domain.item

import enumeratum.{Enum, EnumEntry}
import io.circe.{Decoder, Encoder, HCursor, Json}
import io.getquill.MappedEncoding

sealed trait ItemMark extends EnumEntry

object ItemMark extends Enum[ItemMark] {
  override def values: IndexedSeq[ItemMark] = findValues

  case object Wait extends ItemMark

  case object Bought extends ItemMark

  case object Canceled extends ItemMark

  case object Absent extends ItemMark

  implicit val encoder: Encoder[ItemMark] = (mark: ItemMark) => Json.fromString(mark.entryName)
  implicit val decoder: Decoder[ItemMark] = (cursor: HCursor) => for {
    mark <- cursor.value.as[String]
  } yield ItemMark.withNameOption(mark).get


  implicit val encodeItemMark = MappedEncoding[ItemMark, String](_.entryName)
  implicit val decodeItemMark = MappedEncoding[String, ItemMark](ItemMark.withNameOption(_).get)
}