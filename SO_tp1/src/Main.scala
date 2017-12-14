import scala.collection.mutable.Queue
//import java.util.concurrent.Executors


object Main{
    def main(args :Array[String]){


	//Instanciando objeto Menu
  var Mymenu = new Menu

  //Opções do Menu

  Mymenu.fos
  Mymenu.tiposistema
  Mymenu.recursosistema
  Mymenu.processamento
  Mymenu.algoritmos
  Mymenu.nprocessos

  var pm : ProcessManager=null
  var cpus : Array[Cpu] = null
  var threads : Array[Thread] = null
  var hd:HardDisk=null
  var printer:Printer=null
  var busSignal = new SignalBus()

  var t1 : Thread =null
  var t2 : Thread =null
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
  pm.verifySettings
  printer = new Printer(7,busSignal)
  hd = new HardDisk(10,busSignal)
  cpus = new Array[Cpu](Mymenu.d)
  threads = new Array[Thread](Mymenu.d+2)

  for(i<-1 to Mymenu.d)
      cpus(i-1) = new Cpu(i,pm,busSignal)
  for(i<-1 to (Mymenu.d))
      threads(i-1) = new Thread(cpus(i-1))
  threads(Mymenu.d) = new Thread(hd)
  threads(Mymenu.d+1) = new Thread(printer)


  threads.foreach(t=>{t.start})
  threads.foreach(t=>{t.join})
  Results.showResults


/* Relatorio de testes ainda falta arrumar
  Results.testFlag = true
  var numCpus:Int=1
  var pm : ProcessManager=null
  var cpu : Cpu = null
  var threads : Array[Thread] = null
  var hd:HardDisk=null
  var printer:Printer=null
  var busSignal = new SignalBus()
  var schudelers =new Array[Schudeler](4)
  schudelers(0) = new FIFO()
  schudelers(1) = new ShortestJobFirst()
  schudelers(2) = new RoundRobin()
  schudelers(3) = new ShortestProcessRemaining()
  var q = ProcessFactory.buildProcessQueue(100)//cria uma fila de processos para teste
//  Process.showProcessQueue(ProcessFactory.resetProcessQueue)
  for(j<-1 to schudelers.length){

      pm = new ProcessManager(new Dispatcher(),schudelers(j-1),q.clone)
      pm.verifySettings
      printer = new Printer(7,busSignal)
      hd = new HardDisk(10,busSignal)

      threads = new Array[Thread](3)

    //  for(i<-1 to numCpus)
      cpu = new Cpu(j,pm,busSignal)
      //for(i<-1 to (numCpus))
      threads(0) = new Thread(cpu)
      threads(1) = new Thread(hd)
      threads(2) = new Thread(printer)


      threads.foreach(t=>{t.start})
      threads.foreach(t=>{t.join})
      Results.showResults
      Results.cleanQueue

      //println("Apos")
    }
*/
  }
}
