import scala.util.Random
import scala.collection.mutable.Queue

object  ProcessFactory {
  var resetProcessQueue:Queue[Process] = new Queue[Process]()
  var nextId:Int = 1
  var numberProcess:Int = 0
  def buildProcess():Process={
      val r = new Random()
      var newP =  new Process(nextId,Process.READY_STATE, 2*(1 + r.nextInt(50) ),0,0)//gera processos com no maximo 100 quantuns necessarios
      nextId+=1
      newP
  }

  def buildProcessQueue( numProcess: Int): Queue[Process] ={//constroi uma fila de processos com processos de cpu ,hd e impressora
      numberProcess = numProcess
      val rr = new Random()
      var i=0
      var qProcess  = new Queue[Process]()
      val perc = rr.nextDouble()
      var numOther = Math.ceil(perc*numProcess)//if( perc > 0.5 ) Math.ceil((perc-0.5)*numProcess) else Math.ceil(perc*numProcess)  //define q o numero de processos de hd ou impressora seja no maximo 50% do total de processos q o usuario digitou
      //println("Numero de processos de outros"+numOther)
      for (i <- 0 to (numProcess-1)){
             var newP = this.buildProcess()
                if(numOther>0){
                  newP.setQuantumSignal_=(1+rr.nextInt(newP.myQuantum -1))//configura em qual quantum do cpu o processo deve solicitar outro recurso(impressora ou hd)
                  if(rr.nextBoolean())
                    newP.hdQuantum_=(2*(1 + rr.nextInt(10) ))
                  else
                    newP.printerQuantum_=(2*(1 + rr.nextInt(10) ))

                  numOther = numOther-1
                }
                qProcess+=newP
          }
          qProcess
        }



}
