package dna

import dna.models._

object DnaTools {

  /**
    * (Very Easy) Return the size of the DNA sequence (ie. the number of bases in the sequence)
    */
  def size(dna: DNA): Int = {
    dna.length
  }

  /**
    * (Very Easy) Remove all references to this `base` in a `dna` sequence
    */
  def removeAllBase(dna: DNA, base: Base): DNA =
    dna.filter(_ != base)

  /**
    * (Very Easy) Check if the `subsequence` is contained in the main DNA sequence
    */
  def contains(dna: DNA, subsequence: DNA): Boolean = {
    dna.containsSlice(subsequence)
  }

  /**
    * (Easy) Return the complementary sequences of a DNA sequence.
    *
    * Nucleobase A/T are complements of each other, as C and G.
    */
  def complement(base: Base): Base = base match {
    case A => T
    case T => A
    case C => G
    case G => C
  }

  def complementary(dna: DNA): DNA = {
    dna.map(x => complement(x))
  }

  /**
    * (Easy) Count the number of each base in the DNA sequence
    */
  def countBases(dna: DNA): Map[Base, Int] = {
    val initMap = Map(A -> 0, T -> 0, C -> 0, G -> 0)
    val unCompleteMap = dna.groupBy(x => x).mapValues(_.size)
    initMap.map { case (base, count) => base -> unCompleteMap.getOrElse(base, 0)}
    }

  /**
    * (Medium) Process the Hamming distance of two DNA sequences.
    *
    * The hamming distance is calculated by comparing two DNA strands
    * and counting how many of the nucleotides are different from their equivalent
    * in the other string.
    *
    * Note: The Hamming distance is only defined for sequences of equal length.
    * You must count the differences in the first N bases, where `N = min(dna1.size, dna2.size)`
    *
    *  Eg:
    *   - Distance ATCG & ATGG = 1
    *   - Distance ATCG & TAGC = 4
    *   - Distance TTAAT & TTAAGCA = 1
    *
    * @return the hamming distance of dna1 and dna2
    */


  def hammingDistance(dna1: DNA, dna2: DNA): Long ={
    (dna1).zip(dna2).filter(dnaChar => dnaChar._1 != dnaChar._2).length
  }



  /**
    * (Hard) Translate a DNA sequence to its protein representation.
    * Each triplet in the DNA sequence are translated in a (non unique) character, according to the `translation table` defind bellow
    *
    * If the DNA sequence is not valid to be translated (length not divisible by 3), the function should return None.
    * Otherwise, the protein translation must be returned in Some.
    *
    * Eg:
    *  - ACCGCTAGTATATTT = TASIF
    *
    * @return An option representing the protein translation
    */

  //

  def translateProteins(dna: DNA): Option[String] = {
    if (dna.length%3 != 0) None
    else{
      val dnaBy3 = dna.map(x=>x).toList.sliding(3,3).toList

      var test = new StringBuilder
      dnaBy3.foreach{ v =>
        test.append(codeMap(v.mkString))
      }
      Some(test.toString())
    }
  }

  val translationTable = Seq(
    "TTT F","CTT L","ATT I","GTT V","TTC F","CTC L","ATC I","GTC V","TTA L","CTA L","ATA I","GTA V","TTG L","CTG L","ATG M","GTG V",
    "TCT S","CCT P","ACT T","GCT A","TCC S","CCC P","ACC T","GCC A","TCA S","CCA P","ACA T","GCA A","TCG S","CCG P","ACG T","GCG A",
    "TAT Y","CAT H","AAT N","GAT D","TAC Y","CAC H","AAC N","GAC D","TAA #","CAA Q","AAA K","GAA E","TAG #","CAG Q","AAG K","GAG E",
    "TGT C","CGT R","AGT S","GGT G","TGC C","CGC R","AGC S","GGC G","TGA #","CGA R","AGA R","GGA G","TGG W","CGG R","AGG R","GGG G")

  val codeMap = Map(translationTable map { a => a.subSequence(0,3).toString -> a.charAt(4).toString }: _*)

}
