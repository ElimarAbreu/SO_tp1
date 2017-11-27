import scala.collection.mutable.Queue

class FirstJob extends Schudeler{

  def runScheduling(q: Queue[Process]): Process={
      this.setQuantuns(q)
      q.dequeue()
  }

  def showSchudelerConfig():Unit={
        println("Algoritmo de agendamento : FirstJob")
  }


    def setQuantuns(q: Queue[Process]):Unit={
        q.foreach((e)=>{
            e.receivedQuantum_=(e.myQuantum)//configura para os quantuns recebidos serem iguais ao necessario p finalizar a tarefa
        })
        //for(i <- 0 to q.length-1){
        //    println("RQ:"+q(i).receivedQuantum+"MQ"+q(i).myQuantum)

        //}

    }

}
