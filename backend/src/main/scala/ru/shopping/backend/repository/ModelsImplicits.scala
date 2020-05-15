package ru.shopping.backend.repository
import io.getquill.MappedEncoding
import ru.shopping.common.models.ItemMark

object ModelsImplicits {
  implicit val encodeItemMark = MappedEncoding[ItemMark, String](_.entryName)
  implicit val decodeItemMark = MappedEncoding[String, ItemMark](ItemMark.withNameOption(_).get)
}
