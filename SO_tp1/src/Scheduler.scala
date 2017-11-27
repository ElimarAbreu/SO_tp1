import scala.collection.mutable.Queue

trait Schudeler{

  def runScheduling(pQueue: Queue[Process]): Process
  def showSchudelerConfig():Unit
  def setQuantuns(pQueue: Queue[Process]): Unit 
}
