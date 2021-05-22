package currencies

import scala.concurrent.Await
import scala.concurrent.duration._

import org.scalatest._

import currencies.models.CurrencyRatesOnDate
import currencies.services.CurrencyRateFetcher

class CurrencyRateFetcherSpec extends FlatSpec with Matchers {

  "CurrencyRateFetcherSpec" should "return the list of available currency rates on all days" in {
    val allRates = Await.result(CurrencyRateFetcher.fetch("http://www.mocky.io/v2/5b7694f03000006000848bb9"), 30.seconds)
    allRates shouldBe a[Right[_, CurrencyRatesOnDate]]
    allRates.right.get.size shouldEqual 11
  }

}
