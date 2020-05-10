package ru.shopping.telegram.engine

import java.util.concurrent.atomic.AtomicLong

object UpdateIdStorage {
  private val current = new AtomicLong(0)

  def next: Long = current.get()

  def lastFetched(last: Long): Unit = current.set(last + 1L)
}
