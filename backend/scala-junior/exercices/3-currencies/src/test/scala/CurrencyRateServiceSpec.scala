import java.time.LocalDate

import org.scalatest._

import currencies.models.{Currency, CurrencyRate, CurrencyRatesOnDate, Direction}
import currencies.services.CurrencyRateService

class CurrencyRateServiceSpec extends FlatSpec with Matchers {

  private val dataSample: Seq[CurrencyRatesOnDate] = Seq(
    buildCROD(2018, 8, 15, Seq(buildCR(Currency.IDR, BigDecimal(12.3456)), buildCR(Currency.CHF, BigDecimal(43.6754)))),
    buildCROD(2018, 8, 14, Seq(buildCR(Currency.IDR, BigDecimal(9.1234)))),
    buildCROD(2018, 8, 13, Seq(buildCR(Currency.IDR, BigDecimal(9.1234)))),
    buildCROD(2018, 8, 12, Seq(buildCR(Currency.IDR, BigDecimal(10.55))))
  )

  private val service = new CurrencyRateService(dataSample)

  "list" should "return the complete list of days, each with a list of conversion rates" in {
    val allRates = service.list()
    allRates.size shouldEqual 4

    val firstRate = allRates(0)
    firstRate.date shouldEqual LocalDate.of(2018, 8, 15)
    firstRate.rates.size shouldEqual 2
  }

  "listRatesForDate" should "return all the rates for a given day" in {
    service.listRatesForDate(LocalDate.now) shouldEqual None
    val currencyRates = service.listRatesForDate(LocalDate.of(2018, 8, 14))
    currencyRates.map(_.rates.size) shouldEqual Some(1)
  }

  "getRateForCurrencyAndDate" should "return the rate of a given currency for a given day" in {
    service.getRateForCurrencyAndDate(LocalDate.now, Currency.CHF) shouldEqual None
    val currencyRate = service.getRateForCurrencyAndDate(LocalDate.of(2018, 8, 15), Currency.CHF)
    currencyRate.map(_.rate) shouldEqual Some(BigDecimal(43.6754))
  }

  "getCurrencyHistory" should "return all the rates on all available days" in {
    service.getCurrencyHistory(Currency.IDR) shouldBe Map(
      LocalDate.of(2018, 8, 15) -> BigDecimal(12.3456),
      LocalDate.of(2018, 8, 14) -> BigDecimal(9.1234),
      LocalDate.of(2018, 8, 13) -> BigDecimal(9.1234),
      LocalDate.of(2018, 8, 12) -> BigDecimal(10.55)
    )
  }

  "getCurrencyTrend" should "return the changes of the rates of a currency on all available days" in {
    service.getCurrencyTrend(Currency.IDR) shouldBe Seq(Direction.Down, Direction.Stable, Direction.Up)
  }

  private def buildCR(currency: Currency, rate: BigDecimal): CurrencyRate = CurrencyRate(currency, rate)
  private def buildCROD(year: Int, month: Int, day: Int, rates: Seq[CurrencyRate]): CurrencyRatesOnDate =
    CurrencyRatesOnDate(LocalDate.of(year, month, day), rates)

}
