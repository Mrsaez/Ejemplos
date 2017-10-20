package UDPMensajes;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class ClienteUDP {
public static void main(String[] args)
   {
       new ClienteUDP();
   }
public ClienteUDP()
   {
       try
       { DatagramSocket socket = new DatagramSocket(
                    Constantes.PUERTO_DEL_CLIENTE, InetAddress.getByName("localhost"));
                    DatoUdp elDato = new DatoUdp("hola");
                    byte[] elDatoEnBytes = elDato.toByteArray();
                    DatagramPacket dato = new DatagramPacket(elDatoEnBytes,
                    elDatoEnBytes.length, InetAddress.getByName("localhost"),5557);
                   for (int i = 0; i < 10; i++)
                     {
                        System.out.println("Envio dato " + i);
                        socket.send(dato);
                        Thread.sleep(1000);
                        }
        } catch (Exception e)
            
       {
       }
   }
}


