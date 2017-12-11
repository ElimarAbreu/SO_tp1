import scala.collection.mutable.Queue
import java.util.concurrent.Executors


object Main{
    def main(args :Array[String]){


  //    Menu

  var pm : ProcessManager=null
  var cpu:Cpu = null
  var hd: HardDisk= null
  var s : SignalBus = new SignalBus()
  println("Informe o numero de processos:")
  val n = scala.io.StdIn.readInt();

  println("Informe o numero do algoritmo de escalonamento")
  println("1- FIFO")
  println("2- ShortestJobFirst")
  println("3- RoundRobin")
  println("4- ShortestProcessRemaining")

  var x = scala.io.StdIn.readInt();
    x match {

        case 1=> pm = new ProcessManager(new Dispatcher(),new FIFO(),n)
        case 2=> pm = new ProcessManager(new Dispatcher(),new ShortestJobFirst(),n)
        case 3=> pm = new ProcessManager(new Dispatcher(),new RoundRobin(),n)
        case 4=> pm = new ProcessManager(new Dispatcher(),new ShortestProcessRemaining(),n)
        case _ => println("Valor invalido")
    }
    hd = new HardDisk(3,s)
    cpu = new Cpu(1,pm,s)
  
    new Thread(cpu).start
    new Thread(hd).start





  }
}
