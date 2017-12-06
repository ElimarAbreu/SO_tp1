import scala.collection.mutable.{PriorityQueue,Queue}



object Main{
    def main(args :Array[String]){


  //    Menu
  var x =10
  x+=10
  println(x)
  println("Informe o numero de processos:")
  val n = scala.io.StdIn.readInt();
  var pm = new ProcessManager(new Dispatcher(),new ShortestJobFirst(),n)
  var cpu = new Cpu(1,pm)
    cpu.runCpu

/*
  var p = ProcessFactory.buildProcess()
  p.receivedQuantum_=(40)
  p.insightProcess
  p.receivedQuantum_=(p.receivedQuantum-1)
  p.insightProcess
  */

  }
}
