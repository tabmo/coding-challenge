package currencies.models

sealed trait Direction

object Direction {

  case object Up extends Direction

  case object Down extends Direction

  case object Stable extends Direction

}
