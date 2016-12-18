package radar

import scala.collection.mutable.ListBuffer
import scala.io.Source

/**
  * Created by Mélanie on 18/12/2016.
  */
class FileParser(fileName: String) {

  def file = fileName
  private val patternFirstLine = "Vitesse max : (.*?) km/h".r
  private val patternMaxSpeed = " [0-9]{2,3}".r
  private var fileDataInfo = new ListBuffer[(String, String, String)]()
  private var maxSpeed: Int = 0

  /**
    * Parses the file and update maxSpeed and fileDataInfo (data from file lines except first) variables
    */

  def parseFile() {
    for (line <- Source.fromFile(file).getLines()) {
      if (patternFirstLine.findFirstMatchIn(line) != None) {
        maxSpeed = (patternMaxSpeed.findFirstIn(line)).get.trim.toInt
      }
      else {
        val Array(licensePlate, kmMarker, date) = line.split(",").map(_.trim)
        fileDataInfo += ((s"$licensePlate", s"$kmMarker", s"$date"))
      }
    }
  }
  /**
    * For every licencePlate the corresponding radar information is collected, then an object Car is created
    * Method checkIfViolation of class Car is called in order to detect violations
    */
  def processData(){
    var fileDataToList = fileDataInfo.toList
    var authorizedSpeed = maxSpeed
    var licencePlatesList = fileDataInfo.map(tuple => (tuple._1)).distinct
    val carRadarInfoList = scala.collection.mutable.SortedSet[(Int, Int)]()
    licencePlatesList.foreach { licencePlate =>
      carRadarInfoList.clear()
      fileDataInfo.foreach { dataElem =>
        if (dataElem._1 == licencePlate) {
          carRadarInfoList += ((dataElem._2.toInt, dataElem._3.toInt))
        }
      }
      val car = new Car(licencePlate, maxSpeed, carRadarInfoList.toList)
      car.checkIfViolation()
    }
  }

}
