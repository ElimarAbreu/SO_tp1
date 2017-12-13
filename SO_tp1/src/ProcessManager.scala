import scala.collection.mutable.Queue

class ProcessManager(private var _myDis: Dispatcher, private var _mySch: Schudeler,private var _mainQueue:Queue[Process],private var _returnQueue:Queue[Process],private var _countProcessBack:Int){


    def this(myDis: Dispatcher,mySch : Schudeler, numProcess: Int){
        this(myDis,mySch, ProcessFactory.buildProcessQueue(numProcess),new Queue[Process](),0 )//Array(0) fila do cpu,Array(1) armazena os processos que ja foram executados pelo hd e impressora
      }

    //getters
    def myDis = _myDis
    def mySch = _mySch
    def mainQueue = _mainQueue
    def returnQueue = _returnQueue
    def countProcessBack = _countProcessBack//utilizado para que o cpu nao possa encerrar sem que todos os processos q requisitaram dispositivos de I/O tenham sido devidamente finalizados

    def countProcessBack_=(num:Int): Unit={_countProcessBack =num}

    def insertMainQueue(p: Process){
        mainQueue+=p
    }

    def insertReturnQueue(p: Process){
        returnQueue+=p
    }

    def headerMsg():String ={
        "|[ProcessManager]|>"

    }

  private  def executeScheduler():Unit={// funcao que aciona o algoritmo de agendamento de processos
       _mainQueue=this.mySch.runScheduling(mainQueue)
    }

  def verifySettings():Unit={
        println(this.headerMsg +" Algoritmo de Agendamento ")
        this.mySch.showSchudelerConfig
        println(this.headerMsg+ "Fila de processos")
        Process.showProcessQueue(mainQueue)

    }


    private def getProcessFromResources(cpu: Cpu):Unit={

          cpu.mySignalBus.getCpuQueue(returnQueue)//pega os processos que finalizaram I/O
          returnQueue.foreach(e=>{
            e.state_=(Process.READY_STATE)
          })
          //println("Tamanho:"+returnQueue.length)
          countProcessBack_=(countProcessBack - returnQueue.length)//decrementa o contador de processos que utilizaram I/O
          while(!returnQueue.isEmpty){
              this.insertMainQueue(returnQueue.dequeue)//insere o processo de volta na fila princial de processos do cpu
          }

        }

  private def runInPreemptiveMode(cpu: Cpu):Boolean={
        var resultProcess : Process = null
          if(cpu.mySignalBus.getSignal)
              this.getProcessFromResources(cpu)

          if(!mainQueue.isEmpty){//ocorre a troca de processos, caso na haja mais processos na fila principal do cpu, sinaliza ao cpu para encerrar
                executeScheduler //executa o algoritmo de agendamento
                resultProcess = mainQueue.dequeue
                this.myDis.dispatchProcess(cpu,resultProcess)//coloca outro processo para executar na cpu
            true
          }else if(countProcessBack > 0){
                    this.myDis.dispatchProcess(cpu,null)//quando nao ha mais processos na fila principal porem o cpu deve esperar por processos que estao sendo executados por dispositvos I/O
                  true
          }
          else{
              false
          }
    }

  private def runInNotPreemptiveMode(cpu: Cpu, mustWait: Boolean): Boolean={
        //println("runInNotPreemptiveMode")
            var resultProcess : Process = null
            if(mustWait){//caso o ultimo processo tenha requisitado o hd ou impressora
                while(!cpu.mySignalBus.notPreemptiveTurn){//espera até que o processo volte do HD ou impressora
                }
                resultProcess = cpu.mySignalBus.getOneProcess
                this.myDis.dispatchProcess(cpu,resultProcess)
                true
            }else{//caso o ultimo processo tenha encerrado normalmente
                if(!mainQueue.isEmpty){//caso ainda hajam processos na fila principal
                  resultProcess = mainQueue.dequeue
                  this.myDis.dispatchProcess(cpu,resultProcess)
                  true
                }else{
                   false //todos os processos ja foram executados
                }
            }

        }


  def serveToCpu(cpu: Cpu): Boolean ={//rotina que trata do gerenciamento do cpu

    this.synchronized{

            if(!ProcessManager._firstExecuteScheduling){//executa somente uma vez o algoritmo de agendamento em sistemas batch
              executeScheduler
            //Process.showProcessQueue(mainQueue)
              ProcessManager._firstExecuteScheduling = true
            }
            var prevProcess = cpu.curProcess
            var mustWait:Boolean = false
            cpu.tickIdleClock(1)
              if(prevProcess!=null){//caso haja um processo q estava anteriormente executando na cpu
                    if(prevProcess.remainingQuantum>0){//o processo q estava no cpu teve seu quantum expirado ou solicitou o  hd ou impressora
                          if(!prevProcess.hasSignalInThisQuantum){//caso o processo nao tenha solicitado a cpu ou hd neste indice de quantum

                            prevProcess.state_=(Process.READY_STATE)
                            this.insertMainQueue(prevProcess)//coloca o processo novamente na fila de processos da cpu
                          }else{//processo solicitou o hd ou impressora
                            prevProcess.setQuantumSignal_=(0)
                            prevProcess.state_=(Process.BLOCKED_STATE)
                            mustWait = true//flag usada no modo nao preemptivo
                            countProcessBack_=(countProcessBack+1)//contador usado no modo preemptivo para indicar ao S.O que nao encerre as atividades até que chege a 0
                            if(prevProcess.hdQuantum > 0){
                                cpu.mySignalBus.insertHDQueue(prevProcess)//processo encaminhado para a fila do hd no barramento
                            }else{
                            //  println("Else vazio")
                                cpu.mySignalBus.insertPrinterQueue(prevProcess)//processo vai para fila da impressora no barramento
                            }
                        }
                    }
                    else{//o processo termina
                      println("\n"+"|[ProcessManager]__>:Processo:ID["+prevProcess.ID+"]Terminou|")
                      prevProcess = null
                    }
            }

            if(mySch.preemptiveFlag)
                runInPreemptiveMode(cpu)
            else
              runInNotPreemptiveMode(cpu,mustWait)

        }


    }



    object ProcessManager{
      var _firstExecuteScheduling: Boolean = false

    }

}
