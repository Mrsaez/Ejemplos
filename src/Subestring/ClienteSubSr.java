package Subestring;

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
public class ClienteSubSr {
     public static void main(String[] args) {  
           int port=5001;  
           try (Socket clientSocket = new Socket("localhost",port)) {
                   System.out.println("Cliente activo");  
                   
                     
                   BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));  
                   
                   ObjectOutputStream paraServer = new ObjectOutputStream( clientSocket.getOutputStream());   
                   ObjectInputStream desdeServer = new ObjectInputStream( clientSocket.getInputStream()); 

                    System.out.println("INGRESE UNA PALABRA:");  
                    String str=teclado.readLine();  
                    
                    paraServer.writeObject(str);
                    
                    System.out.println("INGRESE OTRA PALABRA:");  
                    str=teclado.readLine();  
                    
                    paraServer.writeObject(str);
                    
                    String str_result=(String)desdeServer.readObject();  
                    
                    System.out.println("RESULTADO:"+str_result);  
                    
                    
                    
                    teclado.close();
                    paraServer.close();
                    desdeServer.close();
                    clientSocket.close();
               }
                
           
          
    catch( IOException | ClassNotFoundException e){
      System.out.println( e );
    }
    }
}
