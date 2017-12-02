object Menu {
  
      //Funções com as opções do menu.
      def tiposistema{ 
        println("Informe o tipo de sistema a ser simulado:"); 
        println("1 - Sistemas Batch");
        println("2 - Time Sharing");
        val a = scala.io.StdIn.readInt();
        if(a!=1 && a!=2){
          erro
          tiposistema
        }
      }
      
      def recursosistema{
         println("Informe os recursos do sistema a ser simulado:");
         println("1 - Monoprogramado");
         println("2 - Multiprogramado");
         val b = scala.io.StdIn.readInt();
         if(b==2){
           prempcao
         }
         else if(b!=1 && b!=2){
           erro
           recursosistema
        }
      }
      
      def prempcao{
         println("Informe uma opção:");
         println("1 - Preemptivo");
         println("2 - Cooperativo(não premptivo)");
         val c = scala.io.StdIn.readInt();
         if(c!=1 && c!=2){
           erro
           prempcao
        }
      }
      def processamento{
         println("Informe a quantidade de processadores:");
         println("1 - 1 processador");
         println("2 - 2 processadores");
         val d = scala.io.StdIn.readInt();
         if(d!=1 && d!=2){
           erro
           processamento
         }
      }
      def algoritmos{
         println("Informe o algoritmo de escalonamento a ser utilizado:");
         println("1 -  First-In First-Out (FIFO)");
         //println("2 - Shortest Job First");
         //println("3 - Round Robin");
         val e = scala.io.StdIn.readInt();
         if(e!=1){
           erro
           algoritmos
         }
      }
      def nprocessos{
         println("Informe a quantidade de processos:");
         val f = scala.io.StdIn.readInt();
      }
      def erro{
        println("Opção inválida!");
      }
      
      //chamada das funções do menu. 
      println("Sistema Operacional Fictício");
      tiposistema
      recursosistema
      processamento
      algoritmos
      
}