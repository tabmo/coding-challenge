import org.scalatest._

import dna.DnaTools
import dna.models._

class DnaToolsSpec extends FlatSpec with Matchers {

  "size" should "return the number of base in the sequence" in {
    DnaTools.size(Seq(A, T, C, G)) shouldEqual 4
    DnaTools.size(DNA("")) shouldEqual 0
    DnaTools.size(DNA("ATCGATCG")) shouldEqual 8
  }

  "removeAllBases" should "remove all occurences of a base in the DNA sequence" in {
    DnaTools.removeAllBase(DNA(""), A) shouldEqual DNA("")
    DnaTools.removeAllBase(DNA("ATCG"), A) shouldEqual DNA("TCG")
    DnaTools.removeAllBase(DNA("AATTCC"), G) shouldEqual DNA("AATTCC")
    DnaTools.removeAllBase(DNA("ATCGATCG"), G) shouldEqual DNA("ATCATC")
  }

  "contains" should "check if a subsequence is contained in a DNA sequence" in {
    DnaTools.contains(DNA(""), DNA("")) shouldEqual true
    DnaTools.contains(DNA(""), DNA("A")) shouldEqual false
    DnaTools.contains(DNA("A"), DNA("A")) shouldEqual true
    DnaTools.contains(DNA("ATCG"), DNA("AT")) shouldEqual true
    DnaTools.contains(DNA("ATCG"), DNA("TCG")) shouldEqual true
    DnaTools.contains(DNA("ATCG"), DNA("GC")) shouldEqual false
  }

  "complementary" should "transform a DNA sequence to it's complementary sequence" in {
    DnaTools.complementary(DNA("")) shouldEqual DNA("")
    DnaTools.complementary(DNA("ATCG")) shouldEqual DNA("TAGC")
    DnaTools.complementary(DNA("TAGCATCG")) shouldEqual DNA("ATCGTAGC")
  }

  "countBases" should "count the number of each base in a DNA sequence" in {
    DnaTools.countBases(DNA("")) shouldEqual Map(A -> 0, T -> 0, C -> 0, G -> 0)
    DnaTools.countBases(DNA("ATCG")) shouldEqual Map(A -> 1, T -> 1, C -> 1, G -> 1)
    DnaTools.countBases(DNA("ATACAC")) shouldEqual Map(A -> 3, T -> 1, C -> 2, G -> 0)
  }

  "hammingDistance" should "calculate the hamming distance of two DNA seq" in {
    DnaTools.hammingDistance(DNA(""), DNA("")) shouldEqual 0
    DnaTools.hammingDistance(DNA("ATCG"), DNA("ATCG")) shouldEqual 0
    DnaTools.hammingDistance(DNA("ATCG"), DNA("GCTA")) shouldEqual 4
    DnaTools.hammingDistance(DNA("ATCG"), DNA("AACG")) shouldEqual 1
    DnaTools.hammingDistance(DNA("TTAAT"), DNA("TTAAGCA")) shouldEqual 1
  }

  "translateProteins" should "translate a DNA sequence to its protein representation" in {
    DnaTools.translateProteins(DNA("")) shouldEqual Some("")
    DnaTools.translateProteins(DNA("ATCG")) shouldEqual None
    DnaTools.translateProteins(DNA("ATC")) shouldEqual Some("I")
    DnaTools.translateProteins(DNA("ACCGCTAGTATATTT")) shouldEqual Some("TASIF")
  }
}

