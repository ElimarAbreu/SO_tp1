import scala.collection.mutable.Queue



object Main{
  //  Menu
  def main(args :Array[String]){
    var pm = new ProcessManager(new Dispatcher(),new FirstJob())
    //pm.initPManager()//
    var i=0
    var cpu = new Cpu(1,pm)
    Menu
    println("Informe o numero de processos:")
    val aa = scala.io.StdIn.readInt();
    for (i <- 0 to (aa-1))
        pm.insertCpuQueue(ProcessFactory.buildProcess())


  //  while(!pm.queues(0).isEmpty)
    //    println(pm.queues(0).dequeue.showProcess())

        cpu.runCpu()
  }
}
