package ru.shopping.backend

import io.getquill.MappedEncoding
import ru.shopping.common.models.ItemMark

package object repository {
  implicit val encodeItemMark = MappedEncoding[ItemMark, String](_.entryName)
  implicit val decodeItemMark = MappedEncoding[String, ItemMark](ItemMark.withNameOption(_).get)
}
