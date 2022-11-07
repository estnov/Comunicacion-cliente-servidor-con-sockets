package modelo;

import java.net.*;
import java.io.*;

public class Servidor {

    private Socket socket;
    private ServerSocket serverSocket;
    private DataInputStream bufferEntrada;
    private DataOutputStream bufferSalida;

    public Servidor() {
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

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setBufferEntrada(DataInputStream bufferEntrada) {
        this.bufferEntrada = bufferEntrada;
    }

    public void setBufferSalida(DataOutputStream bufferSalida) {
        this.bufferSalida = bufferSalida;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
