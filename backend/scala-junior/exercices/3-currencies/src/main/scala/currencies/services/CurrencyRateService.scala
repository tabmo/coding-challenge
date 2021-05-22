package currencies.services

import java.time.LocalDate

import currencies.models._

class CurrencyRateService(currencyRates: Seq[CurrencyRatesOnDate]) {

  /**
    * List all currency rates on each day, for each currency.
    */
  def list(): Seq[CurrencyRatesOnDate] = ???

  /**
    * List all currency rates on the specified day, for each currency.
    */
  def listRatesForDate(date: LocalDate): Option[CurrencyRatesOnDate] = ???

  /**
    * List the rate for the specified currency, on the specified day.
    */
  def getRateForCurrencyAndDate(date: LocalDate, currency: Currency): Option[CurrencyRate] = ???

  /**
    * List the rate of the specified currency on each of the available day.
    */
  def getCurrencyHistory(currency: Currency): Map[LocalDate, BigDecimal] = ???

  /**
    * For each couple of days (day1, day2), return whether the currency rate went up or down from day1 to day2.
    *
    * Note: since the webservice returns the days in reverse chronological order, the trend will also be reversed!
    */
  def getCurrencyTrend(currency: Currency): Seq[Direction] = ???

}
