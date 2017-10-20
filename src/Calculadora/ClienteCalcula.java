package Calculadora;

import java.io.*;
import java.net.Socket;

/**
 *
 * @author MarielaRoxaxa
 */
public class ClienteCalcula {
    
    public static void main(String[] args) {  
       /* InetAddress dirServidor = InetAddress.getByName(args[0]);
        int puertoServidor = Integer.parseInt(args[1]);
          if (args.length != 2) 
           {System.err.println("Formato: Cliente <IP> <puerto> ");
            System.exit(-1);    
           }  
          else
          {  */   
           int result;
           try {  
               // Socket clientSocket=new Socket(dirServidor,puertoServidor);  
               Socket clientSocket=new Socket("localhost",5001);  
                System.out.println("Cliente activo");  
                
                InputStreamReader isr = new InputStreamReader(System.in);  
                BufferedReader br = new BufferedReader(isr);  
                
                ObjectOutputStream paraServer = new ObjectOutputStream( clientSocket.getOutputStream());   
                ObjectInputStream desdeServer = new ObjectInputStream( clientSocket.getInputStream()); 

                 System.out.println("ENTRE UN NUMERO:");  
                 int no=Integer.parseInt(br.readLine());  
                 paraServer.writeObject(no);  
                 
                 System.out.println("ENTRE OTRO NUMERO:");  
                 no=Integer.parseInt(br.readLine());  
                 paraServer.writeObject(no);  
                 
                 result=(int)desdeServer.readObject();  
                 System.out.println("LA SUMA ES:"+result);  
                 result=(int)desdeServer.readObject();  
                 System.out.println("LA RESTA ES:"+result); 
                 result=(int)desdeServer.readObject();  
                 System.out.println("LA DIVISION ES:"+result);  
                 result=(int)desdeServer.readObject();  
                 System.out.println("LA MULTIPLICACION ES:"+result);  
           
           }
    catch( IOException | NumberFormatException | ClassNotFoundException e){
      System.out.println( e );
    }
    }
    }
//}
