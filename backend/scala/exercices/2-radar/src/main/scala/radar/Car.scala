package radar

/**
  * This class represents a car by its licence plate, the maximum authorized speed for the session
  * and the paired info associated to this plate
  */

class Car(val licencePlate: String, val  maxSpeed: Int, val radarDataList: List[(Int,Int)]  ) {
  require(!radarDataList.isEmpty, "List is empty, no radar data available")
  private def licenceP: String = licencePlate
  private def maxS: Int = maxSpeed
  private def listOfRD : List[(Int,Int)] = radarDataList

  /**
    * Prints the licence plate, km marker and speed if a violation is detected
    */

  def checkIfViolation() {
    var auxList = listOfRD
    var previousPair = auxList.head
    auxList = auxList.tail

    auxList.foreach { pairedData=>
      var computedDistance = (pairedData._1-previousPair._1).toDouble
      var computedTime = (pairedData._2-previousPair._2 ).toDouble/3600.00
      var computedSpeed = Math.ceil(computedDistance/computedTime)
      var computedSpeedRounded  = BigDecimal(computedSpeed).setScale(0, BigDecimal.RoundingMode.HALF_UP).toInt
      if(computedSpeed > maxSpeed)
        println(licenceP+","+pairedData._1+","+computedSpeedRounded )

      previousPair = pairedData
    }
  }

}