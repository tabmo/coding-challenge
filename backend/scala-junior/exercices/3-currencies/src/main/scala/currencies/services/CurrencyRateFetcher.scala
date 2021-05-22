package currencies.services

import scala.concurrent.Future

import play.api.libs.ws.ahc.StandaloneAhcWSClient

import currencies.models.CurrencyRatesOnDate

object CurrencyRateFetcher {

  private val wsClient = StandaloneAhcWSClient() // Documentation: https://www.playframework.com/documentation/2.6.x/ScalaWS

  /**
    * Fetch the available currency rates over HTTP.
    *
    * The method returns a Left containing the network error message, if any. Otherwise, it returns a Right containing
    * the currency rates.
    */
  def fetch(url: String): Future[Either[String, Seq[CurrencyRatesOnDate]]] = ???

}
