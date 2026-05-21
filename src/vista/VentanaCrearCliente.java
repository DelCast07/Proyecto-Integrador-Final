package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class VentanaCrearCliente extends JFrame {

	private String rangoUsuario;
	private int idUsuario;
	private JTextField txtNombreCliente;
	private JTextField txtSuperpoder;
	private JTextField txtColores;
	private JButton btnGuardarCambios;
	private JButton btnAtras;
	private JButton btnCerrarSesion;
	private String ventanaOrigen;
	private JComboBox cmbNombreTraje;

	public VentanaCrearCliente(String rango, int id, String origen) {
		this.rangoUsuario = rango;
		this.idUsuario = id;
		this.ventanaOrigen = origen;
		inicializarComponentes();
		configInicial();
	}

	private void configInicial() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setSize(960, 540);
		setLocationRelativeTo(null);
		setTitle("Crear Nuevo Cliente");
	}

	/**
	 * Método para la configuracion de los componentes de la ventana de creacion de clientes
	 */
	private void inicializarComponentes() {
		// footer franja inferior
		JPanel pnlFooter = new JPanel();
		pnlFooter.setBackground(new Color(72, 119, 109));
		pnlFooter.setBounds(0, 481, 944, 20);
		getContentPane().add(pnlFooter);

		JLabel lblCopyright = new JLabel("© 2026 Payo-Vallecano, Inc. Todos los derechos reservados");
		lblCopyright.setHorizontalAlignment(SwingConstants.CENTER);
		lblCopyright.setForeground(Color.WHITE);
		lblCopyright.setFont(new Font("Verdana", Font.PLAIN, 10));
		pnlFooter.add(lblCopyright);

		// panel gris central
		JPanel pnlBarraHorizontal = new JPanel();
		pnlBarraHorizontal.setBackground(new Color(196, 204, 203));
		pnlBarraHorizontal.setBounds(0, 111, 944, 282);
		pnlBarraHorizontal.setLayout(null);
		getContentPane().add(pnlBarraHorizontal);

		// boton para cerrar sesion
		btnCerrarSesion = new JButton("Cerrar sesión");
		btnCerrarSesion.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnCerrarSesion.setBackground(new Color(165, 191, 201));
		btnCerrarSesion.setBounds(787, 68, 135, 30);
		getContentPane().add(btnCerrarSesion);

		// titulo de la ventana
		JLabel lblTitulo = new JLabel("Crear Cliente");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblTitulo.setBounds(22, 63, 333, 40);
		getContentPane().add(lblTitulo);
		
		modelo.Modelo m = new modelo.Modelo();
		String nombreUsuario = m.obtenerNombreEmpleado(idUsuario);
		
		JLabel lblUsuarioLogueado = new JLabel(nombreUsuario + " (" + rangoUsuario + ")");
		lblUsuarioLogueado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuarioLogueado.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblUsuarioLogueado.setForeground(new Color(68, 68, 68));
		lblUsuarioLogueado.setBounds(622, 13, 310, 30);
		getContentPane().add(lblUsuarioLogueado);

		// boton para guardar los datos del nuevo cliente
		btnGuardarCambios = new JButton("Guardar");
		btnGuardarCambios.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnGuardarCambios.setBackground(new Color(165, 191, 201));
		btnGuardarCambios.setBounds(22, 231, 109, 30);
		pnlBarraHorizontal.add(btnGuardarCambios);

		// boton para volver atras flecha
		btnAtras = new JButton("");
		ImageIcon iconoAtras = new ImageIcon("img\\flecha_izq.png");
		java.awt.Image imgAtras = iconoAtras.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		btnAtras.setIcon(new ImageIcon(imgAtras));
		btnAtras.setBackground(new Color(165, 191, 201));
		btnAtras.setBounds(22, 11, 30, 30);
		getContentPane().add(btnAtras);

		// panel contenedor del formulario
		JPanel pnlFormulario = new JPanel();
		pnlFormulario.setBorder(new LineBorder(new Color(68, 68, 68), 1, true));
		pnlFormulario.setLayout(null);
		pnlFormulario.setBackground(new Color(165, 191, 201));
		pnlFormulario.setBounds(139, 25, 782, 236);
		pnlBarraHorizontal.add(pnlFormulario);

		// etiqueta y campo para el nombre
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNombre.setBounds(50, 45, 150, 30);
		pnlFormulario.add(lblNombre);

		txtNombreCliente = new JTextField();
		txtNombreCliente.setBounds(200, 45, 180, 30);
		pnlFormulario.add(txtNombreCliente);

		// etiqueta y campo para el superpoder
		JLabel lblSuperpoder = new JLabel("Superpoder:");
		lblSuperpoder.setFont(new Font("Verdana", Font.BOLD, 14));
		lblSuperpoder.setBounds(50, 105, 150, 30);
		pnlFormulario.add(lblSuperpoder);

		txtSuperpoder = new JTextField();
		txtSuperpoder.setBounds(200, 105, 180, 30);
		pnlFormulario.add(txtSuperpoder);
		
		JLabel lblNombreTraje = new JLabel("Nombre del Traje:");
		lblNombreTraje.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNombreTraje.setBounds(408, 45, 158, 30);
		pnlFormulario.add(lblNombreTraje);
		cmbNombreTraje = new JComboBox();
		cmbNombreTraje.setFont(new Font("Verdana", Font.PLAIN, 14));
		cmbNombreTraje.setBounds(589, 45, 150, 30);
		pnlFormulario.add(cmbNombreTraje);

		// etiqueta y campo para los colores
		JLabel lblColores = new JLabel("Colores:");
		lblColores.setFont(new Font("Verdana", Font.BOLD, 14));
		lblColores.setBounds(50, 165, 150, 30);
		pnlFormulario.add(lblColores);

		txtColores = new JTextField();
		txtColores.setBounds(200, 165, 180, 30);
		pnlFormulario.add(txtColores);

		// imagen de fondo
		JLabel lblFondo = new JLabel("");
		lblFondo.setBounds(0, 0, 944, 501);
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpeg"));
		getContentPane().add(lblFondo);
	}

	/**
	 * Método para conectar el controlador con los botones
	 * 
	 * @param c
	 */
	public void setControlador(ActionListener c) {
		btnGuardarCambios.addActionListener(c);
		btnAtras.addActionListener(c);
		btnCerrarSesion.addActionListener(c);
		cmbNombreTraje.addActionListener(c);
	}
	
	/**
	 * Metodo para rellenar el combobox de trajes
	 * @param trajes
	 */
	public void rellenarComboBoxTrajes(ArrayList<String> trajes) {
		cmbNombreTraje.removeAllItems();  
		cmbNombreTraje.addItem("- Crear nuevo traje -");
	    
	    for (String t : trajes) {
	    	cmbNombreTraje.addItem(t);
	    }
	}

	public String getNombreCliente() {
		return txtNombreCliente.getText();
	}

	public String getSuperpoderCliente() {
		return txtSuperpoder.getText();
	}

	public String getColorCliente() {
		return txtColores.getText();
	}

	public JButton getBtnGuardarCambios() {
		return btnGuardarCambios;
	}

	public JButton getBtnAtras() {
		return btnAtras;
	}

	public JButton getBtnCerrarSesion() {
		return btnCerrarSesion;
	}

	public String getVentanaOrigen() {
		return ventanaOrigen;
	}

	public String getRangoUsuario() {
		return rangoUsuario;
	}
	
	public JComboBox<String> getNombreTraje() {
	    return cmbNombreTraje;
	}
}