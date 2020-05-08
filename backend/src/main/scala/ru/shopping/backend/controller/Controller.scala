package ru.shopping.backend.controller

import cats.effect.IO
import org.http4s.HttpRoutes

trait Controller {
  val version: String

  val rootPath: String

  val controller: HttpRoutes[IO]
}

