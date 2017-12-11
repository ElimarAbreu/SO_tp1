class Process(private var _ID: Int, private var _priority:Int, private var _state:Int, private var _myQuantum:Int, private var _remainingQuantum: Int,private var _receivedQuantum:Int,private var _hdQuantum: Int,private var _printerQuantum: Int, private var _signalResource:Int = 0 ){

//myQuantum é o tempo q o processo vai precisar ao todo para terminar
//receivedQuantum é o quantum q o escalonador forneceu ao processo
  def this(_ID: Int, _state: Int, _myQuantum: Int, _hdQuantum: Int,_printerQuantum: Int){//construtor secundario com apenas 3 argumentos, usar este ao criar processos
    this(_ID,0,_state,_myQuantum,_myQuantum,0,_hdQuantum,_printerQuantum,0);//signal resource é usando para saber em qual quantum avisar o cpu que ele precisa do hd ou impressora
  }

//setters e getters dos atributos da classe
  def ID = _ID
  def ID_= (newId: Int)= this._ID = newId

  def priority = _priority
  def priority_= (newPriority: Int)= this._priority = newPriority

  def state = _state
  def state_=(newState: Int)= this._state = newState

  def myQuantum = _myQuantum
  def myQuantum_= (newQ: Int)= this._myQuantum = newQ

  def remainingQuantum = _remainingQuantum
  def remainingQuantum_= (newQ: Int)= this._remainingQuantum = newQ

  def receivedQuantum = _receivedQuantum
  def receivedQuantum_= (newReceivedQuantum: Int)= this._receivedQuantum = newReceivedQuantum

  def hdQuantum = _hdQuantum
  def hdQuantum_= (setQ: Int) = this._hdQuantum = setQ

  def printerQuantum = _printerQuantum
  def printerQuantum_= (setQ: Int) = this._printerQuantum = setQ

  def signalResource = _signalResource
  def setQuantumSignal_=(numberQ: Int) = this._signalResource = numberQ // define em qual quantum o processador ira saber q este processo precisa do hd ou impressora dentro do intervalo de quantuns do processo[1,(myQuantum-1)]
  def hasSignalInThisQuantum(): Boolean= (((this.myQuantum -this.remainingQuantum) == this._signalResource)&& this._signalResource > 0)


  def showProcessRunning():String={
    ("Running quantum:"+(this.myQuantum -this.remainingQuantum))
  }

  def showProcess():String={

      ("Process ID:"+this._ID+"|"+this.state+"|"+"|AllQuantum:"+this.myQuantum+"|RemainingQuantum:"+this.remainingQuantum)

  }

  def insightProcess()={

    println("ID:"+this._ID+"|priority:"+this.priority+"|state:"+this.state+"|myQuantum:"+this.myQuantum+"|RMQuantum:"+this.remainingQuantum+":|RECQuantum:"+this.receivedQuantum+"|hdQ:"+hdQuantum+"|printQ:"+printerQuantum+"|signalQ:"+signalResource)



  }


}

object  Process{
    val BLOCKED_STATE = 0
    val RUNNING_STATE=1
    val READY_STATE=2

}
