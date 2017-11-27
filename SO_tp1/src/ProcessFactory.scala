object  ProcessFactory {
  val r = scala.util.Random;
  var nextId:Int =0;

  def buildProcess():Process={
      var newP =  new Process(nextId,Process.READY_STATE, 2*(1 + r.nextInt(20) ))//gera processos com no maximo 100 quantuns necessarios
      nextId+=1
      newP
  }


}
