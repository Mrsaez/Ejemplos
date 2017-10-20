package EjUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

/**
 *
 * @author MarielaRoxaxa
 */
public class ServerUDP {
    public static void main(String[] args) throws IOException {
        int PUERTO=8050;
        byte msg[]=new byte[1024];
        DatagramSocket s = new DatagramSocket(PUERTO);
        System.out.println("Servidor Activo");
        while (true) {
            DatagramPacket recibido = new DatagramPacket(new byte [1024],1024);
            s.receive(recibido);
            System.out.println("Ha llegado una peticion \n");
            System.out.println("Procedente de :" + recibido.getAddress());
            System.out.println("En el puerto :" + recibido.getPort());
            String message="HORA DEL SERVIDOR " + new Date();
            msg=message.getBytes();
            DatagramPacket paquete=new DatagramPacket(msg,msg.length,recibido.getAddress(),recibido.getPort());
            s.send(paquete);
        }
} 
}
