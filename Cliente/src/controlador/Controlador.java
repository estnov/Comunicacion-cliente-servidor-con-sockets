package controlador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import modelo.Cliente;
import vista.Ventana;

public class Controlador {
    private Cliente cliente;
    private Ventana v;
    private final String terminarPrograma = "terminar()";

    public Controlador(Ventana v) {
        cliente = new Cliente();
        this.v = v;
    }

    public void iniciarConexion(int puerto) {
        try {
            cliente.setSocket(new Socket("Localhost", puerto));
            v.mostrarAlerta("Conexión establecida con: " + cliente.getSocket().getInetAddress().getHostName());
            v.habilitarChat();
        } catch (Exception e) {
            v.mostrarAlerta("Error al conectar: \n" + e.getMessage());
            System.exit(0);
        }

    }

    public void establecerFlujo() {
        try {
            cliente.setBufferEntrada(new DataInputStream(cliente.getSocket().getInputStream()));
            cliente.setBufferSalida(new DataOutputStream(cliente.getSocket().getOutputStream()));
            cliente.getBufferSalida().flush();
        } catch (Exception e) {
            v.mostrarAlerta("Error al establecer un flujo de datos: \n" + e.getMessage());
        }
    }

    public void enviar(String s) {
        try {
            cliente.getBufferSalida().writeUTF(s);
            cliente.getBufferSalida().flush();
        } catch (Exception e) {
            v.mostrarAlerta("Error al enviar mensaje\n\n" + e.getMessage());
        }
    }

    public void terminarConexion() {
        try {
            cliente.getBufferEntrada().close();
            cliente.getBufferSalida().close();
            cliente.getSocket().close();
        } catch (Exception e) {
            v.mostrarAlerta("No se pudo cerrar la conexion: \n\n" + e.getMessage());
        } finally {
            v.mostrarAlerta("Se ha finalizado la conexión de forma exitosa");
        }
    }

    public void recibirDatos() {
        String s = "";
        try {
            do {
                s = (String) cliente.getBufferEntrada().readUTF();
                v.mostrarMensaje("\n[Remoto]: " + s);
                // v.mostrarMensaje("\n[Tú]: ");
            } while (!s.equals(this.terminarPrograma));
        } catch (Exception e) {
            terminarConexion();
        }
    }

    public void escribirDatos(String s) {
        v.mostrarMensaje("\n[Tú]: " + s);
        enviar(s);
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
