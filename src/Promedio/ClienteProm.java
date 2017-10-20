/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Promedio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author MarielaRoxaxa
 */
public class ClienteProm {
    public static void main(String[] args) throws UnknownHostException {  
/*        InetAddress dirServidor = InetAddress.getByName(args[0]);
        int puertoServidor = Integer.parseInt(args[1]);
          if (args.length != 2) 
           {System.err.println("Formato: Cliente <IP> <puerto> ");
            System.exit(-1);    
           }  
          else
          {   */  
           try {  
               // Socket clientSocket=new Socket(dirServidor,puertoServidor);  
               Socket clientSocket=new Socket("localhost",5001);  
                System.out.println("Cliente activo");  
                
                InputStreamReader isr = new InputStreamReader(System.in);  
                BufferedReader br = new BufferedReader(isr);  
                
                ObjectOutputStream paraServer = new ObjectOutputStream( clientSocket.getOutputStream());   
                ObjectInputStream desdeServer = new ObjectInputStream( clientSocket.getInputStream()); 
                int no;
                 ArrayList<Integer> lista = new ArrayList<>(); 
                 do{
                     System.out.println("ENTRE UN NÚMERO: (cero para terminar)");  
                      
                     no=Integer.parseInt(br.readLine());  
                     lista.add(no);
                 }while (no!=0);

                 paraServer.writeObject(lista);  
                 
                               
                 double result=(Double)desdeServer.readObject();  
                 System.out.println("EL Promedio es:"+result);  
                
           
           }
    catch( IOException | NumberFormatException | ClassNotFoundException e){
      System.out.println( e );
    }
   // }
    }
}
