package currencies.models

import java.time.LocalDate

// Represent the rate of a specific currency against the Euro
case class CurrencyRate(currency: Currency, rate: BigDecimal)
// Represent all the rates of available currencies against the Euro on a specific day
case class CurrencyRatesOnDate(date: LocalDate, rates: Seq[CurrencyRate])
