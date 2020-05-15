package ru.shopping.backend.service

import java.time.Instant

import ru.shopping.backend.repository.StatisticRepository
import ru.shopping.common.models.ItemMark.Wait
import ru.shopping.common.models.{ItemMark, ListItem, ShoppingList, User}

object DatesConst {
  val PERIOD_DAY = 0
  val PERIOD_WEEK = 1
  val PERIOD_MONTH = 2
  val PERIOD_YEAR = 3
  val PERIOD_ALL_TIME = 4
}

class StatisticService(private val statisticRepository: StatisticRepository) {

  import DatesConst._

  private val DAY = 86400L
  private val periodDifference = Map(
    PERIOD_DAY -> DAY,
    PERIOD_WEEK -> DAY * 7,
    PERIOD_MONTH -> DAY * 31,
    PERIOD_YEAR -> DAY * 365,
    PERIOD_ALL_TIME -> DAY * 365 * 50,
  )

  private def resolveDatesFromPeriod(period: Int) = {
    val diff = periodDifference.getOrElse(period, DAY)
    val from = Instant.now().getEpochSecond - diff
    val to = Instant.now().getEpochSecond
    (from, to)
  }

  def fromPeriodUntilNow(userId: User.Id, period: Int): List[ShoppingList] = {
    val (from, to) = resolveDatesFromPeriod(period)
    statisticRepository.get(userId, from, to)
  }

  def fromPeriodUntilNowForMark(userId: User.Id, period: Int, mark: Int): List[ListItem] = {
    val (from, to) = resolveDatesFromPeriod(period)
    val itemMark = ItemMark.values.find(it => it.order == mark).getOrElse(Wait)
    statisticRepository.get(userId, from, to, itemMark).map { case (l, _) => l }
  }
}