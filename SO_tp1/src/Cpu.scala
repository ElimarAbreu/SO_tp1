class Cpu( private var _coreId: Int, private var _myPManager: ProcessManager , private var _curProcess: Process) {

  def this(_coreId: Int){
      this(_coreId,null,null)
  }

  def this(_coreId: Int,myPManager: ProcessManager){
      this(_coreId,myPManager,null)
  }

  def coreId = _coreId//getter do id
  def myPManager = _myPManager
  def myPManager_= (recPManager: ProcessManager): Unit={this._myPManager = recPManager}//setter do gerenciador de processos desta cpu

  def curProcess = _curProcess//getter do processo atualmente sendo executado
  def curProcess_= (newCProcess: Process): Unit = {this._curProcess = newCProcess}// setter de um novo processo , vai ser usado no Dispatcher




  private def processManagerRequest():Boolean={
      this.myPManager.serveToCpu(this)
  }

  def checkPManager():Boolean={
      if(this.myPManager!=null){
          this.myPManager.verifySettings()
          true
      }else{
          println("Erro CPU sem ProcessManager")
          false
      }
  }

  def runProcessInBatchMode():Unit={// @TODO melhorar a maneira como os quatuns sao analizados durante a execucao
        println("|________Iniciando processo:"+this.curProcess.showProcess()+"________|")
      while(this.curProcess.receivedQuantum>0){
          println("Executando o quantum:"+(this.curProcess.myQuantum -this.curProcess.receivedQuantum))
            this.curProcess.receivedQuantum_=(this.curProcess.receivedQuantum-1)
      }
      this.curProcess.myQuantum_=(0)
  }



  def runCpu(){
    if(checkPManager){
      while(this.processManagerRequest()){
        this.runProcessInBatchMode()
      }
    }else println("Erro nas configurações")

  }




}
