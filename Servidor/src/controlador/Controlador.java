package controlador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;

import modelo.Servidor;
import vista.Ventana;

public class Controlador {
    private Servidor servidor;
    private Ventana v;
    private final String terminarPrograma = "terminar()";

    public Controlador(Ventana v) {
        servidor = new Servidor();
        this.v = v;
    }

    public void iniciarConexion(int puerto) {
        try {
            servidor.setServerSocket(new ServerSocket(puerto));
            v.mostrarAlerta("Esperando conexión del puerto: " + puerto + " \nPresione OK para continuar:)");
            servidor.setSocket(servidor.getServerSocket().accept());
            v.mostrarAlerta("Conexión establecida con: " + servidor.getSocket().getInetAddress().getHostName());
            v.habilitarChat();
        } catch (Exception e) {
            v.mostrarAlerta("Error al conectar: \n" + e.getMessage());
            System.exit(0);
        }
    }

    public void establecerFlujo() {
        try {
            servidor.setBufferEntrada(new DataInputStream(servidor.getSocket().getInputStream()));
            servidor.setBufferSalida(new DataOutputStream(servidor.getSocket().getOutputStream()));
            servidor.getBufferSalida().flush();
        } catch (Exception e) {
            v.mostrarAlerta("Error al establecer un flujo de datos: \n" + e.getMessage());
        }
    }

    public void recibirDatos() {
        String s = "";
        try {
            do {
                s = (String) servidor.getBufferEntrada().readUTF();
                v.mostrarMensaje("\n[Remoto]: " + s);
                // v.mostrarMensaje("\n[Tú]: ");
            } while (!s.equals(this.terminarPrograma));
        } catch (Exception e) {
            terminarConexion();
        }
    }

    public void enviar(String s) {
        try {
            servidor.getBufferSalida().writeUTF(s);
            servidor.getBufferSalida().flush();
        } catch (Exception e) {
            v.mostrarAlerta("Error al enviar mensaje\n\n" + e.getMessage());
        }
    }

    public void escribirDatos(String s) {
        v.mostrarMensaje("\n[Tú]: " + s);
        enviar(s);
    }

    public void terminarConexion() {
        try {
            servidor.getBufferEntrada().close();
            servidor.getBufferSalida().close();
            servidor.getSocket().close();
        } catch (Exception e) {
            v.mostrarAlerta("No se pudo cerrar la conexion: \n\n" + e.getMessage());
        } finally {
            v.mostrarAlerta("Se ha finalizado la conexión de forma exitosa");
        }

    }

    public void ejecutarConexion(int puerto) {
        Thread h1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        iniciarConexion(puerto);
                        establecerFlujo();
                        recibirDatos();
                    } finally {
                        terminarConexion();
                    }
                }
            }
        });
        h1.start();
    }
}
