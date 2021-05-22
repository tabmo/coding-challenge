package currencies

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

package object services {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

}
