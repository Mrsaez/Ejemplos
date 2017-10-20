/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package palindrome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

/**
 *
 * @author MarielaRoxaxa
 */
public class ClientePali {
    public static void main(String[] args) {  
           int port=5001;  
           try (Socket clientSocket = new Socket("localhost",port)) {
                   System.out.println("Cliente activo");  
                   
                   InputStreamReader isr = new InputStreamReader(System.in);  
                   BufferedReader br = new BufferedReader(isr);  
                   
                   ObjectOutputStream paraServer = new ObjectOutputStream( clientSocket.getOutputStream());   
                   ObjectInputStream desdeServer = new ObjectInputStream( clientSocket.getInputStream()); 

                    System.out.println("INGRESE UNA PALABRA:");  
                    String str=br.readLine();  
                    
                    paraServer.writeObject(str);
                    
                    String str_result=(String)desdeServer.readObject();  
                    
                    System.out.println("RESULTADO:"+str_result);  
                    System.out.println("GETPORT:"+clientSocket.getPort());
                     System.out.println("GETlOCALPORT:"+clientSocket.getLocalPort());
                      System.out.println("iNNETADDRESS:"+clientSocket.getInetAddress());
                    System.out.println("GET LOCalADDRESS:"+clientSocket.getLocalAddress());  
                    
                    
                    isr.close();
                    br.close();
                    paraServer.close();
                    desdeServer.close();
                    clientSocket.close();
               }
                
           
          
    catch( IOException | ClassNotFoundException e){
      System.out.println( e );
    }
    }
}
