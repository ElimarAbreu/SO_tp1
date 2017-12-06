class Cpu( private var _coreId: Int, private var _myPManager: ProcessManager , private var _curProcess: Process,private var _occupiedClock: Int,private var _idleClock:Int) {

  def this(_coreId: Int){
      this(_coreId,null,null,0,0)
  }

  def this(_coreId: Int,myPManager: ProcessManager){
      this(_coreId,myPManager,null,0,0)
  }

  def coreId = _coreId//getter do id
  def myPManager = _myPManager
  def myPManager_= (recPManager: ProcessManager): Unit={this._myPManager = recPManager}//setter do gerenciador de processos desta cpu

  def curProcess = _curProcess//getter do processo atualmente sendo executado
  def curProcess_= (newCProcess: Process): Unit = {this._curProcess = newCProcess}// setter de um novo processo , vai ser usado no Dispatcher

  def occupiedClock = _occupiedClock
  def tickOccupiedClock(numClocks: Int):Unit ={this._occupiedClock+=numClocks  }

  def idleClock = _idleClock
  def tickIdleClock(numClocks: Int):Unit ={this._idleClock+=numClocks}


  private def processManagerRequest():Boolean={
      this.myPManager.serveToCpu(this)
  }

private  def checkPManager():Boolean={
      if(this.myPManager!=null){
          this.myPManager.verifySettings()
          true
      }else{
          println("|[CPU]__>:Erro CPU sem ProcessManager|")
          false
      }
  }


def showResultCpuClock(){
    println()
    println("___Log de de ciclos do CPU___")
    println("------Ciclos ocupados:"+this.occupiedClock)
    println("------Ciclos ociosos:"+this.idleClock)


}

  private def runCurrentProcess():Unit={// @TODO melhorar a maneira como os quantuns sao analizados durante a execucao
        println("|[CPU]__>:Iniciando processo:("+this.curProcess.ID+")|")

        while(this.curProcess.receivedQuantum >0){
              tickOccupiedClock(1)//incrementa uma unidade na quantidade de clock ocupado
              println("|[CPU]__>:"+this.curProcess.showProcessRunning()+"|")
              curProcess.remainingQuantum_=(curProcess.remainingQuantum -1)//diminui um quantum da quantidade restante para acabar a tarefa
              curProcess.receivedQuantum_=(curProcess.receivedQuantum-1)
        }


  }



  def runCpu(){
    if(checkPManager){
      myPManager.executeScheduler
      while(this.processManagerRequest()){
            runCurrentProcess

      }
    }else println("Erro nas configurações")
    showResultCpuClock
  }


}
