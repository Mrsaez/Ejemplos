/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerAhorcado {
    //DECLARACIONES

    private static final int INACTIVO = 0;
    private static final int JUGANDO = 1;
    private static final int GANO = 2;
    private static final int PERDIO = 3;
    private static final int SOLICITANDO_JUEGO_NUEVO = 4;
    //ATRIBUTOS DEL SERVIDOR   
    private static final int nroPuerto = 6112;
    private static boolean SocketDisponible = true;
    private static int conexionesRealizadas = 0;
    private static Socket con;
    //ATRIBUTOS DEL JUEGO
    private static int estadoJuego = INACTIVO;
    private static char letra = '?';
    private static String palabra = "?";
    private static String palabraEnProgreso = "?";
    private static int nroIntentos = 6;
    //PALABRAS EN EL SERVIDOR
    static private final String[] palabrasAhorcado = {"reingenieria", "cubeta",
        "tunelizacion", "protocolo", "puertos", "conexion", "broadcasting", "direccion",
        "internet", "router", "switch", "wifi", "estandar", "socket", "transporte",
        "enlace", "capas", "arquitectura", "cliente", "servidor", "proxy", "firewall",
        "redes", "LAN", "WAN", "MAN", "hub", "concentrador", "datagrama", "puente",
        "fibra", "TCP", "UDP", "mascara", "gateway", "servidor", "DNS", "cliente",
        "conmutacion", "circuito", "satelite", "coaxial", "microondas", "señal",
        "ingrarrojos", "token", "anillo", "bus", "control", "flujo", "congestion",
        "enrutamiento", "aplicacion", "correo", "peertopeer", "reingenieria", "cubeta",
        "tunelizacion", "protocolo", "puertos", "conexion", "broadcasting", "direccion",
        "internet", "router", "switch", "wifi", "estandar", "socket", "transporte",
        "enlace", "capas", "arquitectura", "cliente", "servidor", "proxy", "firewall",
        "redes", "LAN", "WAN", "MAN", "hub", "concentrador", "datagrama", "puente",
        "fibra", "TCP", "UDP", "mascara", "gateway", "servidor", "DNS", "cliente",
        "conmutacion", "circuito", "satelite", "coaxial", "microondas", "señal",
        "ingrarrojos", "token", "anillo", "bus", "control", "flujo", "congestion",
        "enrutamiento", "aplicacion", "correo", "peertopeer"};
    private static int nroMensaje = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket socketDeServicio = null;
        try {
            socketDeServicio = new ServerSocket(nroPuerto);

            BufferedReader entrada;
            DataOutputStream salida;
            while (true) {
                try {


                    if (SocketDisponible) {
                        //EL SOCKET ESTA DISPONIBLE, POR LO TANTO ACA NO SE ESTA EN JUEGO
                        if (estadoJuego == INACTIVO) {
                            System.out.println("\nesperando cliente...");
                            con = socketDeServicio.accept();
                            System.out.println("conexion aceptada...\n");
                            salida = new DataOutputStream(con.getOutputStream());
                            entrada = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            conexionesRealizadas++;
                            System.out.println("SERVIDOR : conexion aceptada a cliente " + conexionesRealizadas);
                            /*leer primer mensaje, peticion de inicio de juego desde el 
                             *cliente.*/
                            leerMensaje(entrada.readLine());
//                      imprimirEntrada();
                            procesarMensaje();
                            salida.writeBytes(responderMensaje());
//                      imprimirSalida();
                        }
                    } else {
                        entrada = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        salida = new DataOutputStream(con.getOutputStream());
                        //EL SOCKET ESTA OCUPADO, POR LO TANTO ACA SE ESTA EN JUEGO
                        if (estadoJuego == JUGANDO) {
                            leerMensaje(entrada.readLine());
//                      imprimirEntrada();
                            procesarMensaje();
                            salida.writeBytes(responderMensaje());
//                      imprimirSalida();
                            if (estadoJuego == GANO || estadoJuego == PERDIO) {
                                estadoJuego = INACTIVO;
                                SocketDisponible = true;
                                System.out.println("JUEGO NRO " + conexionesRealizadas + " TERMINADO...");

                            }
                        }
                    }
                } catch (java.net.SocketException e) {
                    System.out.println("TERMINO ABRUPTO DE LA CONEXION CON EL CLIENTE.");
                    estadoJuego = INACTIVO;
                    SocketDisponible = true;
                    System.out.println("JUEGO NRO " + conexionesRealizadas + " TERMINADO...");
                }
            }
        } catch (IOException BindException) {
            System.out.println("La maquina virtual de java ya esta ocupando el socket"
                    + "en ese puerto, intente iniciar el servicio con otro puerto");
        }
    }

    private static void leerMensaje(String mensaje) {
        StringTokenizer stk = new StringTokenizer(mensaje, "#");
        while (stk.hasMoreTokens()) {
            estadoJuego = Integer.valueOf(stk.nextToken());
            nroIntentos = Integer.valueOf(stk.nextToken());
            letra = stk.nextToken().toUpperCase().charAt(0);
            palabraEnProgreso = stk.nextToken().toUpperCase();
            nroMensaje = Integer.valueOf(stk.nextToken());
        }
        nroMensaje++;
    }

    private static void procesarMensaje() {

        if (estadoJuego == SOLICITANDO_JUEGO_NUEVO) {
            setSocketDisponible(false);
            setEstadoJuego(JUGANDO);
            setNroIntentos(6);
            setLetra('?');
            setPalabra(escojerPalabraJuegoNuevo());
            setPalabraEnProgreso();
        } else {
            if (estadoJuego == JUGANDO) {
                if (huboAcierto()) {
                    reemplazarLetra();
                    if (ganoJuego()) {
                        estadoJuego = GANO;
                        System.out.println("SERVIDOR : CLIENTE HA GANADO JUEGO");

                    } else {
                        System.out.println("SERVIDOR : CLIENTE HA ACERTADO PALABRA");
                    }
                } else {
                    nroIntentos--;
                    System.out.println("SERVIDOR : SE LE HA DISMINUIDO UN INTENTO AL CLIENTE POR NO HABER ACERTADO");
                    if (nroIntentos == 0) {
                        estadoJuego = PERDIO;
                        System.out.println("SERVIDOR : CLIENTE HA PERDIDO JUEGO");
                    }
                }
            } else {
                try {
                    System.out.println("SERVIDOR : cerrando conexion...");
                    con.shutdownOutput();
                    SocketDisponible = true;
                    System.out.println("SERVIDOR : conexion finalizada.");
                } catch (IOException ex) {
                    Logger.getLogger(ServerAhorcado.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private static String responderMensaje() {
        String a = estadoJuego + "#" + nroIntentos + "#" + letra + "#" + palabraEnProgreso + "#" + nroMensaje + "\n";
        return a;
    }

    public static void setSocketDisponible(boolean SocketDisponible) {
        ServerAhorcado.SocketDisponible = SocketDisponible;
    }

    public static void setConexionesRealizadas(int conexionesRealizadas) {
        ServerAhorcado.conexionesRealizadas = conexionesRealizadas;
    }

    public static void setEstadoJuego(int estadoJuego) {
        ServerAhorcado.estadoJuego = estadoJuego;
    }

    public static void setLetra(char letra) {
        ServerAhorcado.letra = letra;
    }

    public static void setNroIntentos(int nroIntentos) {
        ServerAhorcado.nroIntentos = nroIntentos;
    }

    public static void setPalabra(String palabra) {
        ServerAhorcado.palabra = palabra;
    }

    public static void setPalabraEnProgreso(String palabraEnProgreso) {
        ServerAhorcado.palabraEnProgreso = palabraEnProgreso;
    }

    private static String escojerPalabraJuegoNuevo() {
        return palabrasAhorcado[(int) (Math.random() * palabrasAhorcado.length)];
    }

    private static void setPalabraEnProgreso() {
        String p = "";

        for (int i = 0; i < palabra.length(); i++) {
            p += "_";
        }
        palabraEnProgreso = p;
    }

    private static boolean huboAcierto() {
        boolean tuvoAcierto = true;
        //PRIMERO DEBEMOS COMPROBAR QUE LA LETRA NO SE PREPITA CON LO QUE 
        //YA TENEMOS COMPLETADO ACTUALMENTE
        tuvoAcierto = !seRepite(letra, palabraEnProgreso) && esParteDeLaPalabra(letra, palabra);
        return tuvoAcierto;
    }

    private static boolean seRepite(char l, String enProgreso) {
        boolean repite = false;
        char[] prog = enProgreso.toCharArray();
        for (int i = 0; i < prog.length; i++) {
            if (l == prog[i]) {
                repite = true;
            }
        }
        return repite;
    }

    private static boolean esParteDeLaPalabra(char letra, String palabra) {
        boolean esParte = false;
        char[] pa = palabra.toUpperCase().toCharArray();
        for (int i = 0; i < pa.length; i++) {
            if (letra == pa[i]) {
                esParte = true;
            }
        }
        return esParte;
    }

    private static void reemplazarLetra() {
        String[] enProg = palabraEnProgreso.split("");
        String[] pal = palabra.split("");
        String reemplazada = "";
        for (int i = 0; i < pal.length; i++) {
            if (String.valueOf(letra).equalsIgnoreCase(pal[i])) {
                enProg[i] = String.valueOf(letra);
            }
            reemplazada += enProg[i];
        }
        palabraEnProgreso = reemplazada;
    }

    private static boolean ganoJuego() {
        if (palabraEnProgreso.equalsIgnoreCase(palabra)) {
            return true;
        } else {
            return false;
        }
    }

    private static String mostrarEstado() {
        if (estadoJuego == 0) {
            return "INACTIVO";
        } else {
            if (estadoJuego == 1) {
                return "JUGANDO";
            } else {
                if (estadoJuego == 2) {
                    return "GANO";
                } else {
                    if (estadoJuego == 3) {
                        return "PERDIO";
                    } else {
                        if (estadoJuego == 4) {
                            return "SOLICITANDO_JUEGO_NUEVO";
                        } else {
                            return "JUEGO_TERMINADO";
                        }
                    }
                }
            }
        }
    }

    private static void imprimirEntrada() {
        String a = estadoJuego + "#" + nroIntentos + "#" + letra + "#" + palabraEnProgreso + "#" + nroMensaje;
        System.out.println("\nLEIDO POR SERVIDOR: " + a + "\n" + mostrarEstado());

    }

    private static void imprimirSalida() {
        String a = estadoJuego + "#" + nroIntentos + "#" + letra + "#" + palabraEnProgreso + "#" + nroMensaje;
        System.out.println("\nENVIADO POR SERVIDOR: " + a + "\n" + mostrarEstado());
    }
}