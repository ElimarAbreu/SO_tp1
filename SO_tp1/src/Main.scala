import scala.collection.mutable.Queue
import java.util.concurrent.Executors


object Main{
    def main(args :Array[String]){


	//Instanciando objeto Menu
  var Mymenu = new Menu;

  //Opções do Menu
  Mymenu.fos
  Mymenu.tiposistema
  Mymenu.recursosistema
  Mymenu.processamento
  Mymenu.algoritmos
  Mymenu.nprocessos

   var pm : ProcessManager=null
  var cpu : Cpu= null
  var busSignal = new SignalBus()
  var hd:HardDisk=null
  //Se o sistema for preemptivo.
  if(Mymenu.c==1){
    Mymenu.e match {
        case 1=> pm = new ProcessManager(new Dispatcher(),new RoundRobin(),Mymenu.f)
        case 2=> pm = new ProcessManager(new Dispatcher(),new ShortestProcessRemaining(),Mymenu.f)
    }
  }
  //Se o sistema for cooperativo(não premptivo).
  if(Mymenu.c ==2 || Mymenu.b==1){
    Mymenu.e match {
        case 1=> pm = new ProcessManager(new Dispatcher(),new FIFO(),Mymenu.f)
        case 2=> pm = new ProcessManager(new Dispatcher(),new ShortestJobFirst(),Mymenu.f)
    }
  }

  cpu = new Cpu(Mymenu.d,pm,busSignal)
  hd = new HardDisk(10,busSignal)

  new Thread(cpu).start
  new Thread(hd).start


/*Descomentar esta parte para teste
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

*/


  }
}
