import scala.collection.mutable.Queue

class ProcessManager(private var _myDis: Dispatcher, private var _mySch: Schudeler,private var _queues: Array[Queue[Process]]){

    def this(myDis: Dispatcher,mySch : Schudeler){
        this(myDis,mySch, Array(new Queue[Process](),new Queue[Process](),new Queue[Process]()))//Array(0) fila do cpu,rray(1) e Array(2) :HD e impressora
    }
  def this(myDis: Dispatcher,mySch : Schudeler, numProcess: Int){
      this(myDis,mySch, Array(ProcessFactory.buildProcessQueue(numProcess),new Queue[Process](),new Queue[Process]()))//Array(0) fila do cpu,rray(1) e Array(2) :HD e impressora
  }
    //new Array[Queue[Process]](3)

    //getters
    def myDis = _myDis
    def mySch = _mySch
    def queues = _queues

    def initPManager():Unit={

        this.queues(0) = new Queue[Process]()
        this.queues(1) = new Queue[Process]()
        this.queues(2) = new Queue[Process]()

    }

    def insertCpuQueue(p: Process){
        this.queues(0)+=p
    }

    def insertHdQueue(p: Process){
        this.queues(1)+=p
    }

    def insertPrinterQueue(p: Process){
        this.queues(2)+=p
    }

    def showProcessQueue():Unit={

        this.queues(0).foreach(e=>{e.insightProcess()})

    }

    def executeScheduler():Unit={// funcao que aciona o algoritmo de agendamento de processos
        this.queues(0) = this.mySch.runScheduling(this.queues(0))
    }

  def verifySettings():Unit={
        println("|[PManager]__>Fila de processos|")
        this.showProcessQueue
        print("\n"+"|[PManager]__>Algoritmo de Agendamento:")
        this.mySch.showSchudelerConfig
    }

    def serveToCpu(cpu: Cpu): Boolean ={//rotina que trata do gerenciamento do cpu
        var resultProcess:Process = null
        var prevProcess = cpu.curProcess
        cpu.tickIdleClock(1)
          if(prevProcess!=null){//caso haja um processo q estava anteriormente executando na cpu
                if(prevProcess.remainingQuantum>0 /*colocar condicao p qndo solicitar hd ou impressora*/){//o processo q estava no cpu teve seu quantum expirado
                      prevProcess.state_=(Process.READY_STATE)
                      this.insertCpuQueue(prevProcess)//coloca o processo novamente na fila de processos da cpu
                }else{//o processo termina
                  println("\n"+"|[PManager]__>:Processo:"+prevProcess.ID+" Terminou|")
                  println()
                  prevProcess = null
                }
        }
        if(!this.queues(0).isEmpty){//ocorre a troca de processos
            if(this.mySch.preemptiveFlag)executeScheduler //caso o algoritmo de agendamento seja preemptivo
              resultProcess = this.queues(0).dequeue
                //resultProcess.showProcess()
              this.myDis.dispatchProcess(cpu,resultProcess)//coloca outro processo para executar na cpu
          true
        }
        else{
            false
        }

    }





    def serveToHd(hd: HardDisk):Unit={



    }

    def serveToPrinter(imp: Printer):Unit={



    }




}
