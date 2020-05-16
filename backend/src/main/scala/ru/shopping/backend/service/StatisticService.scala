package ru.shopping.backend.service

import java.time.Instant

import ru.shopping.backend.repository.StatisticRepository
import ru.shopping.common.models.ItemMark.Wait
import ru.shopping.common.models.{ItemMark, ListItem, ShoppingList, User}
import scala.concurrent.duration._

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
    PERIOD_DAY -> DAYS.toSeconds(1),
    PERIOD_WEEK -> DAYS.toSeconds(7),
    PERIOD_MONTH -> DAYS.toSeconds(31),
    PERIOD_YEAR -> DAYS.toSeconds(365),
    PERIOD_ALL_TIME -> DAYS.toSeconds(365 * 50),
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