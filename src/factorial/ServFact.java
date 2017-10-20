package factorial;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author MarielaRoxaxa
 */
public class ServFact {
    
    private ServerSocket serv;
    private Socket cli;
    private ObjectOutputStream paraClient;
    private ObjectInputStream desdeClient;
    
    public void conectar(int puerto) throws IOException{
        serv=new ServerSocket(puerto);
        cli=new Socket();
        System.out.println("SERVIDOR ESPERANDO");  
        
    }
    
    public void enviar(Object dato) throws IOException{
       
       paraClient.flush();  
       paraClient.writeObject(dato);
       
      
    }
    public Object recibir() throws IOException, ClassNotFoundException{
        Object dato;
        
        dato=desdeClient.readObject();  
        return dato;
    }
    public int calcular(int nro)throws IOException, ClassNotFoundException{
      int result=1;
      
       
      if (nro!=0 && nro!=1){  
            for(int i=2;i<=nro;i++)  
                result*=i;  
                  }  
      return result;
    }
    public void cerrar() throws IOException{
               paraClient.close();
               desdeClient.close();
               cli.close();
               //serv.close();
    }
   public static void main(String[] args) throws IOException, ClassNotFoundException { 
      int puerto,nro, res;
      String sigue;   
      
        if(args.length<1){
            puerto=5000;
        }
        else {
            puerto=Integer.parseInt(args[0]);
            
        }
        
        ServFact servi=new ServFact();
        servi.conectar(puerto);
        while (true){
        sigue="s";
        while(sigue.equals("s")){    
           nro=(int)servi.recibir(); 
           res=servi.calcular(nro);       
           servi.enviar(res);
           
           sigue=(String)servi.recibir();
                  
           }
        servi.cerrar();
        
        }
        
        
   }
    
            
            
            
           }
           


