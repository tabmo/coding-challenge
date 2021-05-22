package currencies

import java.time.LocalDate
import scala.concurrent.ExecutionContext.Implicits.global

import org.slf4j.LoggerFactory

import currencies.models.Currency
import currencies.services.{CurrencyRateFetcher, CurrencyRateService}

object CurrencyRateApp extends App {

  val logger = LoggerFactory.getLogger(this.getClass())

  CurrencyRateFetcher.fetch("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml").map {
    case Right(rates) => {
      val service = new CurrencyRateService(rates)
      logger.info(s"All rates for today: ${service.listRatesForDate(LocalDate.now)}")
      logger.info(s"Trend for IDR on the last days: ${service.getCurrencyTrend(Currency.IDR)}")
      System.exit(0)
    }
    case Left(error) => {
      logger.error(s"Error fetching currency rates: $error")
      System.exit(1)
    }
  }

}
