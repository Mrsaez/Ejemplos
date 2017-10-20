/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ParImpar;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author MarielaRoxaxa
 */
public class ServidorParImpar1 {
    
    private ServerSocket serv;
    private Socket cli;
    private ObjectOutputStream paraClient;
    private ObjectInputStream desdeClient;
    
  public void conectar(int puerto) throws ClassNotFoundException{
   String result;   
  try{
        serv=new ServerSocket(puerto);
        cli=new Socket();
        System.out.println("SERVIDOR ESPERANDO");  
        cli=serv.accept();
       desdeClient=new ObjectInputStream(cli.getInputStream());
       int no=(int)desdeClient.readObject();
       if(no%2==0) result="Par";
       else result="Impar";
       paraClient=new ObjectOutputStream(cli.getOutputStream());
       paraClient.flush();  
       paraClient.writeObject(result);
       paraClient.close();
       desdeClient.close();
       cli.close();
       serv.close();
        }
        catch(IOException e){}
   
   }
    
   public static void main(String[] args) throws IOException, ClassNotFoundException { 
      int puerto;
      
        if(args.length<1){
            puerto=5000;
        }
        else {
            puerto=Integer.parseInt(args[0]);     
        }
     ServidorParImpar1 s=new ServidorParImpar1();
     s.conectar(puerto);
        
}
}