package currencies.models

sealed abstract class Currency(val code: String) {
  override def toString: String = code
}

object Currency {

  def apply(value: String): Currency = {
    fromCode(value) match {
      case Some(currency) => currency
      case None => throw new RuntimeException(s"Currency code $value not supported")
    }
  }

  object USD extends Currency("USD")
  object JPY extends Currency("JPY")
  object BGN extends Currency("BGN")
  object CZK extends Currency("CZK")
  object DKK extends Currency("DKK")
  object GBP extends Currency("GBP")
  object HUF extends Currency("HUF")
  object PLN extends Currency("PLN")
  object RON extends Currency("RON")
  object SEK extends Currency("SEK")
  object CHF extends Currency("CHF")
  object ISK extends Currency("ISK")
  object NOK extends Currency("NOK")
  object HRK extends Currency("HRK")
  object RUB extends Currency("RUB")
  object TRY extends Currency("TRY")
  object AUD extends Currency("AUD")
  object BRL extends Currency("BRL")
  object CAD extends Currency("CAD")
  object CNY extends Currency("CNY")
  object HKD extends Currency("HKD")
  object IDR extends Currency("IDR")
  object ILS extends Currency("ILS")
  object INR extends Currency("INR")
  object KRW extends Currency("KRW")
  object MXN extends Currency("MXN")
  object MYR extends Currency("MYR")
  object NZD extends Currency("NZD")
  object PHP extends Currency("PHP")
  object SGD extends Currency("SGD")
  object THB extends Currency("THB")
  object ZAR extends Currency("ZAR")

  val availableCurrencies = Seq(USD, JPY, BGN, CZK, DKK, GBP, HUF, PLN, RON, SEK, CHF, ISK,
                                NOK, HRK, RUB, TRY, AUD, BRL, CAD, CNY, HKD, IDR, ILS, INR,
                                KRW, MXN, MYR, NZD, PHP, SGD, THB, ZAR)

  def fromCode(code: String): Option[Currency] = {
    availableCurrencies.find(_.code.toUpperCase == code.toUpperCase)
  }

  def exists(code: String): Boolean = fromCode(code).isDefined
}

