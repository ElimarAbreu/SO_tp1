import scala.collection.mutable.Queue

class SignalBus(private var _signal:Boolean , private var signalToContinue:Boolean , private var _cpuQueue:Queue[Process],private var _hdQueue:Queue[Process] , private var _printerQueue: Queue[Process] ){

    def this(){
      this(false,true,new Queue[Process](),new Queue[Process](),new Queue[Process]())
    }

    def setSignalToContinue_=(newSignal: Boolean): Unit={this.synchronized{signalToContinue = newSignal}}

    def getSignalToContinue(): Boolean={this.synchronized{signalToContinue}}

    def setSignal_=(s: Boolean): Unit={this.synchronized{_signal = s}}
    def getSignal():Boolean={this.synchronized{_signal}}

    def cpuQueue = _cpuQueue
    def hdQueue = _hdQueue
    def printerQueue = _printerQueue

    //Funcoes que o cpu vai utilizar no barramento de sinal
    def getCpuQueue(recQ: Queue[Process]): Queue[Process]={
      this.synchronized{
          while(!cpuQueue.isEmpty){
              recQ+=cpuQueue.dequeue
          }
      //  println("Tamanho em signal bus"+recQ.length)
        setSignal_=(false)
        recQ
      }
    }


    def insertHDQueue(p: Process): Unit={//metodo que vai ser utilizado pelo cpu para inserir na fila do HD
        this.synchronized{
            _hdQueue+=p
        }
    }

    def insertPrinterQueue(p: Process): Unit={//funcao que vai ser utilizada pelo cpu para inserir na fila da impressora
        this.synchronized{
            _printerQueue+=p
        }
    }


    //funcoes que o hd e a impressora vao usar no barramento


    def hasProcessInHDQueue():Boolean={this.synchronized{(!_hdQueue.isEmpty)}}
    def hasProcessInPrinterQueue():Boolean={this.synchronized{(!_printerQueue.isEmpty)}}


    def insertCpuQueue(p: Process): Unit={//funcao que vai ser utilizada pelo hd e impressora para devolver os processos que executaram
        this.synchronized{
                  _cpuQueue+=p
                  if(!getSignal)setSignal_=(true)
        }
    }

    def getHDQueue(q: Queue[Process]):Queue[Process]={

      this.synchronized{
        while(!hdQueue.isEmpty){
            q+=hdQueue.dequeue
        }
        q
      }
    }

    def getPrinterQueue(q: Queue[Process]): Queue[Process]={

      this.synchronized{
        while(!printerQueue.isEmpty){
            q+=printerQueue.dequeue
        }
          q
      }
    }

}
