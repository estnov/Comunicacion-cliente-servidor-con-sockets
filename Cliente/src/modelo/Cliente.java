package modelo;

import java.net.*;
import java.io.*;

public class Cliente {
    private Socket socket;
    private DataInputStream bufferEntrada;
    private DataOutputStream bufferSalida;

    public Cliente() {
        bufferEntrada = null;
        bufferSalida = null;
    }

    public DataInputStream getBufferEntrada() {
        return bufferEntrada;
    }

    public DataOutputStream getBufferSalida() {
        return bufferSalida;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setBufferEntrada(DataInputStream bufferEntrada) {
        this.bufferEntrada = bufferEntrada;
    }

    public void setBufferSalida(DataOutputStream bufferSalida) {
        this.bufferSalida = bufferSalida;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}