package EjUDP;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClienteUDP {
public static void main (String args[]){
    DatagramSocket dgSocket = null;
    DatagramPacket datagram;
    InetAddress destino = null;
    if (args.length != 1) {
             System.out.println("Se espera dirección IP...");return;          
        }
    byte msg[] = new byte[1024];
        try {
            dgSocket = new DatagramSocket();
            destino = InetAddress.getByName (args[0]);
        } catch (UnknownHostException | SocketException ex) {
            System.err.println( ex );
            return;
           }
        datagram = new DatagramPacket (msg, msg.length, destino,8050);
        try {
            
            dgSocket.send(datagram);
            dgSocket.receive(datagram);
            String received = new String (datagram.getData());
            System.out.println ("DATOS DEL DATAGRAMA: " + received );
        } catch (IOException ex) {
            System.err.println( ex );
          }
        finally {
            if (dgSocket!=null) 
                dgSocket.close();
        }  
   }
}
