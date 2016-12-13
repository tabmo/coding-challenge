package dna.models

object DNA {

  /**
    * Helper allowing to create a DNA sequence (sequence of Base) from a String
    */
  def apply(bases: String): DNA = {
    bases.map {
      case 'A' => A
      case 'T' => T
      case 'C' => C
      case 'G' => G
      case other => throw new IllegalArgumentException(s"invalid base $other")
    }
  }

}
