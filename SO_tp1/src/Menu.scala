

object Menu {
      println("Sistema Operacional Fictício");
      println("Informe o tipo de sistema a ser simulado:");
      println("1 - Sistemas Batch\n" + "2 - Time Sharing\n" + "3 - Tempo Real");
  
      val a = scala.io.StdIn.readInt();
      println("Informe os recursos do sistema a ser simulado:");
      println("1 - Monoprogramado\n" + "2 - Multiprogramado");
      val b = scala.io.StdIn.readInt();
      println("1 - Preemptivo\n" + "2 - Cooperativo(não premptivo)");
      val c = scala.io.StdIn.readInt();
      println("1 - 1 processador\n" + "2 - 2 processadores");
      val d = scala.io.StdIn.readInt();
}