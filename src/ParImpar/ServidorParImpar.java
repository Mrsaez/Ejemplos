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
public class ServidorParImpar {
    
    private ServerSocket serv;
    private Socket cli;
    private ObjectOutputStream paraClient;
    private ObjectInputStream desdeClient;
    
    public void conectar(int puerto) throws IOException{
        serv=new ServerSocket(puerto);
        cli=new Socket();
        System.out.println("SERVIDOR ESPERANDO");  
        cli=serv.accept();
    }
    
    public void enviar( String result )throws IOException{
       paraClient=new ObjectOutputStream(cli.getOutputStream());
       paraClient.flush();  
       paraClient.writeObject(result);
    }
    
    public String decide()throws IOException, ClassNotFoundException{
      String result;
      desdeClient=new ObjectInputStream(cli.getInputStream());
      int no=(int)desdeClient.readObject();
      if(no%2==0) result="Par";
      else result="Impar";
      return result;
    }
    public void cerrar() throws IOException{
               paraClient.close();
               desdeClient.close();
               cli.close();
               serv.close();
    }
   public static void main(String[] args) throws IOException, ClassNotFoundException { 
      int puerto;
      String res;
        if(args.length<1){
            puerto=5000;
        }
        else {
            puerto=Integer.parseInt(args[0]);
            
        }
        ServidorParImpar servi=new ServidorParImpar();
        servi.conectar(puerto);
        res=servi.decide();
        servi.enviar(res);
        servi.cerrar();
   
   }
}
