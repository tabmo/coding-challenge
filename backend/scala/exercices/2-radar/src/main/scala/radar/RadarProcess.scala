package radar

import scala.collection.mutable.ListBuffer
import scala.io.Source

object RadarProcess {
  
  def main(args: Array[String]): Unit = {
    args.foreach { arg =>
      val inputFile = new FileParser(arg)
      inputFile.parseFile()
      inputFile.processData()
      println(" - - - -")
    }
  }

}
