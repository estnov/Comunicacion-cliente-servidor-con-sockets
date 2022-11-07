package vista;

import controlador.Controlador;

public class App {
    public static void main(String[] args) throws Exception {
        Ventana v = new Ventana();
        Controlador c = new Controlador(v);
        v.setControlador(c);
        v.setVisible(true);
        System.out.println(0);
    }
}
