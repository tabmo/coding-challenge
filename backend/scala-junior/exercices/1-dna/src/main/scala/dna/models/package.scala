package dna

package object models {

  /**
   * All available bases to represent a DNA sequence
   */
  sealed trait Base
  case object A extends Base
  case object T extends Base
  case object G extends Base
  case object C extends Base

  /**
    * Type alias: `DNA` will be replaced at compile time by `Seq[Base]`
    */
  type DNA = Seq[Base]

}
