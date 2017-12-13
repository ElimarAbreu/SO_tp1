class Cpu( private var _coreId: Int, private var _myPManager: ProcessManager ,private var _mySignalBus: SignalBus, private var _curProcess: Process,private var _occupiedClock: Int,private var _idleClock:Int) extends Runnable {



  def this(_coreId: Int,myPManager: ProcessManager,bus: SignalBus){
      this(_coreId,myPManager,bus,null,0,0)
  }

  def coreId = _coreId//getter do id
  def myPManager = _myPManager
  def myPManager_= (recPManager: ProcessManager): Unit={this._myPManager = recPManager}//setter do gerenciador de processos desta cpu

  def mySignalBus = _mySignalBus
  def mySignalBus_=(bus: SignalBus): Unit={_mySignalBus = bus}

  def curProcess = _curProcess//getter do processo atualmente sendo executado
  def curProcess_= (newCProcess: Process): Unit = {this._curProcess = newCProcess}// setter de um novo processo , vai ser usado no Dispatcher

  def occupiedClock = _occupiedClock
  def tickOccupiedClock(numClocks: Int):Unit ={this._occupiedClock+=numClocks  }

  def idleClock = _idleClock
  def tickIdleClock(numClocks: Int):Unit ={this._idleClock+=numClocks}

  def msgHeaderCpu():String={
    "|[CPU]_(CoreId:"+this.coreId+")|>"
  }

  private def processManagerRequest():Boolean={
      this.myPManager.serveToCpu(this)
  }

/*private def takeBackProcess():Unit={
  if(mySignalBus.getSignal)
  this.myPManager.getProcessFromResources(this)
}
*/
private  def checkPManager():Boolean={
      if(this.myPManager!=null){
          //this.myPManager.verifySettings()
          true
      }else{
          println(this.msgHeaderCpu+":Erro CPU sem ProcessManager|")
          false
      }
  }


def showResultCpuClock(){
    println()
    println("___Log de de ciclos de [CPU]: (CoreId:"+this.coreId+")")
    println("------Ciclos ocupados:"+this.occupiedClock)
    println("------Ciclos ociosos:"+this.idleClock)


}

  private def runCurrentProcess():Unit={// @TODO melhorar a maneira como os quantuns sao analizados durante a execucao
      if(this.curProcess!= null){
          println("\n"+this.msgHeaderCpu +"Iniciando Processo(ID:" +this.curProcess.ID+")|")
          while(this.curProcess.receivedQuantum>0 && this.curProcess.remainingQuantum >0 && !this.curProcess.hasSignalInThisQuantum){
                tickOccupiedClock(1)//incrementa uma unidade na quantidade de clock ocupado
                println(this.msgHeaderCpu+"\t"+this.curProcess.showProcessRunning())
                curProcess.remainingQuantum_=(curProcess.remainingQuantum -1)//diminui um quantum da quantidade restante para acabar a tarefa
                curProcess.receivedQuantum_=(curProcess.receivedQuantum-1)
          }
          if(this.curProcess.hasSignalInThisQuantum){
              println(this.msgHeaderCpu+"Processo Solicitou["+curProcess.ID +"]"+{if(curProcess.hdQuantum>0) "Disco Rigido" else "Impressora"})
            //  curProcess.remainingQuantum_=(curProcess.remainingQuantum -1)
            //  curProcess.receivedQuantum_=(curProcess.receivedQuantum-1)
          }

      }
    }



  private def runCpu(): Unit={
    if(checkPManager && this.mySignalBus!=null){

      while(this.processManagerRequest()){
            runCurrentProcess
      }
      this.mySignalBus.setSignalToContinue_=(false)//avisa os dispositivos de I/O para encerrarem a thread
    }else println("Erro nas configurações")
    showResultCpuClock
  }

  def run(){
      this.runCpu
      println("Cpu Encerrou"+this.coreId)
  }

}
