package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controlador.Controlador;

import java.awt.*;
import java.awt.event.*;

public class Ventana extends JFrame {
    private JTextArea txtChat;
    private JTextField txtMensaje;
    private JTextField txtPuerto;
    private JButton btnEnviar;
    private JButton btnOk;
    private JButton btnFinalizar;
    private JPanel pnlSuperior;
    private JPanel pnlInferior;
    private JPanel pnlInferiorContainer;
    private JPanel pnlN;
    private JPanel pnlS;
    private JPanel pnlE;
    private JPanel pnlW;
    private JPanel pnlC;
    private JLabel lblPuerto;

    private Controlador controlador;

    public Ventana() {

        // se declaran las propiedades de la ventana

        setLayout(new BorderLayout(0, 0));
        setSize(300, 400);
        setLocationRelativeTo(null);
        setTitle("Chat Servidor");
        setResizable(false);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // inicialización de todos los componentes

        txtChat = new JTextArea();
        txtMensaje = new JTextField();
        txtPuerto = new JTextField("5050");
        txtChat.setEditable(false);

        lblPuerto = new JLabel("Puerto");

        btnEnviar = new JButton("Enviar");
        btnOk = new JButton("OK");
        btnFinalizar = new JButton("Finalizar");

        pnlSuperior = new JPanel();
        pnlSuperior.setLayout(new BorderLayout(10, 0));
        pnlSuperior.setBackground(Color.WHITE);

        pnlInferior = new JPanel();
        pnlInferior.setLayout(new BorderLayout(10, 0));
        pnlInferior.setBackground(Color.WHITE);

        pnlInferiorContainer = new JPanel();
        pnlInferiorContainer.setLayout(new BorderLayout(10, 10));
        pnlInferiorContainer.setBackground(Color.WHITE);

        pnlN = new JPanel();
        pnlN.setBackground(Color.WHITE);
        pnlS = new JPanel();
        pnlS.setBackground(Color.WHITE);
        pnlW = new JPanel();
        pnlW.setBackground(Color.WHITE);
        pnlE = new JPanel();
        pnlE.setBackground(Color.WHITE);

        pnlC = new JPanel();
        pnlC.setBackground(Color.WHITE);
        pnlC.setLayout(new BorderLayout(10, 0));

        // Se crea el panel superior
        pnlSuperior.add(lblPuerto, BorderLayout.WEST);
        pnlSuperior.add(txtPuerto, BorderLayout.CENTER);
        pnlSuperior.add(btnOk, BorderLayout.EAST);

        // Se crea el panel inferior
        pnlInferior.add(txtMensaje, BorderLayout.CENTER);
        pnlInferior.add(btnEnviar, BorderLayout.EAST);

        pnlInferiorContainer.add(pnlInferior, BorderLayout.NORTH);
        pnlInferiorContainer.add(btnFinalizar, BorderLayout.SOUTH);

        // Se arma la ventana
        pnlC.add(pnlSuperior, BorderLayout.NORTH);
        pnlC.add(txtChat, BorderLayout.CENTER);
        pnlC.add(pnlInferiorContainer, BorderLayout.SOUTH);

        this.add(pnlN, BorderLayout.NORTH);
        this.add(pnlE, BorderLayout.EAST);
        this.add(pnlS, BorderLayout.SOUTH);
        this.add(pnlW, BorderLayout.WEST);
        this.add(pnlC, BorderLayout.CENTER);

        deshabilitarChat();

        // Se construyen los listener
        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador.ejecutarConexion(Integer.parseInt(txtPuerto.getText()));
            }
        });

        btnFinalizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador.terminarConexion();
                deshabilitarChat();
            }
        });

        btnEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlador.escribirDatos(txtMensaje.getText());
                txtMensaje.setText("");
            }
        });
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void habilitarChat() {
        btnFinalizar.setEnabled(true);
        btnEnviar.setEnabled(true);
        btnOk.setEnabled(false);

        txtMensaje.setEnabled(true);
        txtPuerto.setEditable(false);
    }

    public void deshabilitarChat() {
        btnFinalizar.setEnabled(false);
        btnEnviar.setEnabled(false);
        btnOk.setEnabled(true);

        txtMensaje.setEnabled(false);
        txtPuerto.setEditable(true);
    }

    public void mostrarMensaje(String s) {
        txtChat.append(s);
        pnlC.updateUI();
    }

    public void mostrarAlerta(String s) {
        JOptionPane.showMessageDialog(null, s);
    }
}
