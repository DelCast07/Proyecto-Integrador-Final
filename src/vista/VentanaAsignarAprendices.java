package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class VentanaAsignarAprendices extends JDialog {

    private JComboBox<String> cmbAprendiz1;
    private JComboBox<String> cmbAprendiz2;
    private JButton btnAceptar;
    private JButton btnCancelar;

    // Con Frame Parent sabe que ventana lanza
    public VentanaAsignarAprendices(Frame parent) {
        super(parent, "Asignar Aprendices", true);
        configInicial();
        inicializarComponentes();
    }

    private void configInicial() {
        setSize(450, 300);
        setLocationRelativeTo(getOwner());
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245));
    }

    private void inicializarComponentes() {
        JPanel pnlPrincipal = new JPanel();
        pnlPrincipal.setBorder(new LineBorder(new Color(165, 191, 201), 2));
        pnlPrincipal.setBackground(Color.WHITE);
        pnlPrincipal.setBounds(10, 10, 414, 241);
        pnlPrincipal.setLayout(null);
        getContentPane().add(pnlPrincipal);

        JLabel lblTitulo = new JLabel("Selección de Aprendices");
        lblTitulo.setFont(new Font("Verdana", Font.BOLD, 16));
        lblTitulo.setBounds(90, 20, 250, 25);
        pnlPrincipal.add(lblTitulo);

        // Aprendiz 1
        JLabel lblAprendiz1 = new JLabel("Aprendiz 1:");
        lblAprendiz1.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblAprendiz1.setBounds(30, 70, 100, 25);
        pnlPrincipal.add(lblAprendiz1);

        cmbAprendiz1 = new JComboBox<>();
        cmbAprendiz1.setBounds(140, 70, 230, 25);
        pnlPrincipal.add(cmbAprendiz1);

        // Aprendiz 2
        JLabel lblAprendiz2 = new JLabel("Aprendiz 2:");
        lblAprendiz2.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblAprendiz2.setBounds(30, 115, 100, 25);
        pnlPrincipal.add(lblAprendiz2);

        cmbAprendiz2 = new JComboBox<>();
        cmbAprendiz2.setBounds(140, 115, 230, 25);
        pnlPrincipal.add(cmbAprendiz2);

        // Botones
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBackground(new Color(165, 191, 201));
        btnAceptar.setFont(new Font("Verdana", Font.BOLD, 12));
        btnAceptar.setBounds(80, 180, 110, 35);
        pnlPrincipal.add(btnAceptar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(165, 191, 201));
        btnCancelar.setFont(new Font("Verdana", Font.BOLD, 12));
        btnCancelar.setBounds(220, 180, 110, 35);
        pnlPrincipal.add(btnCancelar);
    }

    public void rellenarAprendices(ArrayList<String> nombres) {
    	cmbAprendiz1.removeAllItems();
    	cmbAprendiz2.removeAllItems();
        cmbAprendiz1.addItem("-- Ninguno --");
        cmbAprendiz2.addItem("-- Ninguno --");
        for (String n : nombres) {
        	cmbAprendiz1.addItem(n);
        	cmbAprendiz2.addItem(n);
        }
    }

    public boolean validarSeleccion() {
        String seleccion1 = cmbAprendiz1.getSelectedItem().toString();
        String seleccion2 = cmbAprendiz2.getSelectedItem().toString();

        // Logica de aprendices repetidos
        if (!seleccion1.equals("-- Ninguno --") && seleccion1.equals(seleccion2)) {
            JOptionPane.showMessageDialog(this, "No puedes seleccionar al mismo aprendiz dos veces.");
            return false;
        }
        return true;
    }

    public void setControlador(ActionListener c) {
        btnAceptar.addActionListener(c);
        btnCancelar.addActionListener(c);
    }

    // Getters
    public String getAprendiz1() { return cmbAprendiz1.getSelectedItem().toString(); }
    public String getAprendiz2() { return cmbAprendiz2.getSelectedItem().toString(); }
    public JButton getBtnAceptar() { return btnAceptar; }
    public JButton getBtnCancelar() { return btnCancelar; }
}