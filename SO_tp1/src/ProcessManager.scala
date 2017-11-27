import scala.collection.mutable.Queue

class ProcessManager(private var _myDis: Dispatcher, private var _mySch: Schudeler,private var _queues: Array[Queue[Process]]){

    def this(myDis: Dispatcher,mySch : Schudeler){
        this(myDis,mySch, Array(new Queue[Process](),new Queue[Process](),new Queue[Process]()))//Array(0) fila do cpu,rray(1) e Array(2) :HD e impressora
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

    def verifySettings():Unit={
        this.mySch.showSchudelerConfig()

    }

    def serveToCpu(cpu: Cpu): Boolean ={//rotina que trata do gerenciamento do cpu
        var resultProcess:Process = null
        var prevProcess = cpu.curProcess
          if(prevProcess!=null){//caso haja um processo q estava anteriormente executando no cpu
                if(prevProcess.myQuantum>0){//o processo q estava no cpu teve seu quantum expirado
                      prevProcess.state_=(Process.READY_STATE)
                      this.insertCpuQueue(prevProcess)
                }else{
                  println("Processo:"+prevProcess.ID+" Terminou!")//processo terminou
                  prevProcess = null
                }
        }
        if(!this.queues(0).isEmpty){
            //println("COndicao para pegar um novo processo")
              resultProcess = this.mySch.runScheduling(this.queues(0))
              resultProcess.showProcess()
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
