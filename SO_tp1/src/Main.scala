import scala.collection.mutable.{PriorityQueue,Queue}


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
  var cpu = new Cpu(Mymenu.d)
  
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
  
  cpu.myPManager_=(pm)
  cpu.runCpu

  /*println("Informe o numero do algoritmo de escalonamento")
  println("1- FIFO")
  println("2- ShortestJobFirst")
  println("3- RoundRobin")
  println("4- ShortestProcessRemaining")
	*/
  /*var x = scala.io.StdIn.readInt();
    x match {

        case 1=> pm = new ProcessManager(new Dispatcher(),new FIFO(),n)
        case 2=> pm = new ProcessManager(new Dispatcher(),new ShortestJobFirst(),n)
        case 3=> pm = new ProcessManager(new Dispatcher(),new RoundRobin(),n)
        case 4=> pm = new ProcessManager(new Dispatcher(),new ShortestProcessRemaining(),n)
        case _ => println("Valor invalido")
    }*/
 

/*
  var p = ProcessFactory.buildProcess()
  p.receivedQuantum_=(40)
  p.insightProcess
  p.receivedQuantum_=(p.receivedQuantum-1)
  p.insightProcess
  */

  }
}
