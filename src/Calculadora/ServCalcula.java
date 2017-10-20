
package Calculadora;

/**
 *
 * @author MarielaRoxaxa
 */
import java.io.*;
import java.net.*;


public class ServCalcula {
    public static void main(String[] args) { 
           try{  
               // int puertoServidor = Integer.parseInt(args[0]); 
                
                ServerSocket serverSocket=new ServerSocket(5001);  
                System.out.println("SERVIDOR ESPERANDO");  
                Socket clientSocket=serverSocket.accept();  
                ObjectOutputStream paraClient = new ObjectOutputStream( clientSocket.getOutputStream());   
                ObjectInputStream desdeClient = new ObjectInputStream( clientSocket.getInputStream()); 
                InputStreamReader isr = new InputStreamReader(System.in);  
                BufferedReader br = new BufferedReader(isr); 
                String sigue="s";
                while(sigue.equals("s")||sigue.equals("S"))  
                {  
                  int a=(int)desdeClient.readObject();  
                  int b=(int)desdeClient.readObject();  
                  int result;  
                  paraClient.flush();
                  result=a+b;
                  paraClient.writeObject(result);  
                  result=a-b;
                  paraClient.writeObject(result);  
                  if(b>0)result=a/b;
                  else result=0;
            
                  paraClient.writeObject(result);
                  result=a*b;
                  paraClient.writeObject(result);  
                    
                  System.out.println("Desea continuar? S/N: ");
                  sigue=br.readLine();
                   
                    
                }
           }
           catch( IOException | ClassNotFoundException e) {
               System.out.println( "AQUI "+e );
}
}
}
