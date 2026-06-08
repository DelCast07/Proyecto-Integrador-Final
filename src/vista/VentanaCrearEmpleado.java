package vista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.LineBorder;
import controlador.empleado.ControladorCrearEmpleado;

public class VentanaCrearEmpleado extends JFrame {

	private String rangoUsuario;
	private int idUsuario;
	private JTextField txtNombre;
	private JTextField txtApodo;
	private JComboBox cbCategoria;
	private JPasswordField txtContraseña;
	
	private char ecoPorDefecto;
	
	private JButton btnVerContraseña;
	private JButton btnGuardarCambios;
	private JButton btnAtras;

	public VentanaCrearEmpleado(String rango, int id) {
		this.rangoUsuario = rango;
		this.idUsuario = id;
		inicializarComponentes();
		configInicial();
	}

	private void configInicial() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setSize(960, 540);
		setLocationRelativeTo(null);
	}

	private void inicializarComponentes() {
		// Footer
		JPanel pnlFooter = new JPanel();
		pnlFooter.setBackground(new Color(72, 119, 109));
		pnlFooter.setBounds(0, 481, 944, 20);
		getContentPane().add(pnlFooter);

		// Copyright
		JLabel lblCopyright = new JLabel("© 2026 Payo-Vallecano, Inc. Todos los derechos reservados");
		lblCopyright.setHorizontalAlignment(SwingConstants.CENTER);
		lblCopyright.setForeground(Color.WHITE);
		lblCopyright.setFont(new Font("Verdana", Font.PLAIN, 10));
		pnlFooter.add(lblCopyright);

		// Panel horizontal principal (decoración de fondo)
		JPanel pnlBarraHorizontal = new JPanel();
		pnlBarraHorizontal.setBackground(new Color(196, 204, 203));
		pnlBarraHorizontal.setBounds(0, 111, 944, 282);
		pnlBarraHorizontal.setLayout(null);
		getContentPane().add(pnlBarraHorizontal);

		// TÍTULO DE LA PANTALLA
		JLabel lblTitulo = new JLabel("Crear Empleado");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblTitulo.setBounds(22, 63, 333, 40);
		getContentPane().add(lblTitulo);

		// BOTÓN GUARDAR
		btnGuardarCambios = new JButton("Guardar");
		btnGuardarCambios.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnGuardarCambios.setBackground(new Color(165, 191, 201));
		btnGuardarCambios.setBounds(22, 231, 109, 30);
		pnlBarraHorizontal.add(btnGuardarCambios);

		// BOTÓN ATRÁS
		btnAtras = new JButton("");
		ImageIcon iconoAtras = new ImageIcon("img\\flecha_izq.png");
		java.awt.Image imgAtras = iconoAtras.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		btnAtras.setIcon(new ImageIcon(imgAtras));
		btnAtras.setBackground(new Color(165, 191, 201));
		btnAtras.setBounds(22, 11, 30, 30);
		getContentPane().add(btnAtras);

		// EL FORMULARIO RECUADRADO
		JPanel pnlFormulario = new JPanel();
		pnlFormulario.setBorder(new LineBorder(new Color(68, 68, 68), 1, true));
		pnlFormulario.setBackground(new Color(165, 191, 201));
		pnlFormulario.setBounds(139, 25, 782, 236);
		pnlFormulario.setLayout(null);
		pnlBarraHorizontal.add(pnlFormulario);

		// NOMBRE
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNombre.setBounds(50, 40, 100, 30);
		pnlFormulario.add(lblNombre);
		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtNombre.setBounds(160, 40, 200, 30);
		pnlFormulario.add(txtNombre);

		// APODO
		JLabel lblApodo = new JLabel("Apodo:");
		lblApodo.setFont(new Font("Verdana", Font.BOLD, 14));
		lblApodo.setBounds(420, 40, 100, 30);
		pnlFormulario.add(lblApodo);
		txtApodo = new JTextField();
		txtApodo.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtApodo.setBounds(530, 40, 200, 30);
		pnlFormulario.add(txtApodo);

		// CATEGORÍA
		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setFont(new Font("Verdana", Font.BOLD, 14));
		lblCategoria.setBounds(50, 120, 100, 30);
		pnlFormulario.add(lblCategoria);
		cbCategoria = new JComboBox(new String[]{"Aprendiz", "Oficial", "Maestro"});
		cbCategoria.setFont(new Font("Verdana", Font.PLAIN, 14));
		cbCategoria.setBounds(160, 120, 200, 30);
		pnlFormulario.add(cbCategoria);

		// CONTRASEÑA
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setFont(new Font("Verdana", Font.BOLD, 14));
		lblContraseña.setBounds(420, 120, 100, 30);
		pnlFormulario.add(lblContraseña);
		
		txtContraseña = new JPasswordField(); 
		txtContraseña.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtContraseña.setBounds(530, 120, 150, 30);
		pnlFormulario.add(txtContraseña);
		
		// Guardamos el carácter original de ocultación de Java (puntos del Login) antes de manipularlo
		ecoPorDefecto = txtContraseña.getEchoChar();

		// BOTÓN OJO INTEGRADO
		btnVerContraseña = new JButton("o");
		btnVerContraseña.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
		btnVerContraseña.setBounds(685, 120, 45, 30);
		btnVerContraseña.setBackground(new Color(210, 225, 230));
		pnlFormulario.add(btnVerContraseña);

		// IMAGEN DE FONDO
		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpeg"));
		lblFondo.setBounds(0, 0, 944, 501);
		getContentPane().add(lblFondo);
	}

	// Método para registrar la vinculación de eventos con el controlador correspondiente
	public void setControlador(ControladorCrearEmpleado c) {
		btnGuardarCambios.addActionListener(c);
		btnAtras.addActionListener(c);
		btnVerContraseña.addActionListener(c);
	}

	// Método expuesto para cambiar la máscara de ocultación
	public void cambiarMascaraContraseña(char caracter) {
		txtContraseña.setEchoChar(caracter);
	}

	// --- GETTERS ESTÁNDAR PARA EL CONTROLADOR ---
	public char getEcoPorDefecto() { return ecoPorDefecto; }
	public JButton getBtnGuardarCambios() { return btnGuardarCambios; }
	public JButton getBtnAtras() { return btnAtras; }
	public JButton getBtnVerContraseña() { return btnVerContraseña; }
	public String getRangoUsuario() { return rangoUsuario; }
	public int getIdUsuario() { return idUsuario; }

	public String getNombre() { return txtNombre.getText(); }
	public String getApodo() { return txtApodo.getText(); }
	public String getCategoria() { return cbCategoria.getSelectedItem().toString(); }
	public String getContraseña() { return new String(txtContraseña.getPassword()); }
}