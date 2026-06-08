package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.LineBorder;
import controlador.empleado.ControladorModificarEmpleado;

public class VentanaModificarEmpleado extends JFrame {

	private String rangoUsuario;
	private int idEmpleado; 
	private String contraseñaOriginal; 
	
	private JTextField txtNombre;
	private JTextField txtApodo;
	private JComboBox<String> cbCategoria;
	//esto es para las contraseñas
	private JPasswordField txtContraseñaAnterior;
	private JPasswordField txtContraseñaNueva;
	private JPasswordField txtContraseñaRepetir;
	private JButton btnVerContraseña;
	

	private char ecoPorDefecto;
	
	private JButton btnGuardarCambios;
	private JButton btnAtras;

	public VentanaModificarEmpleado(String rango, int idEmp, String nombre, String apodo, String categoria, String contraseña) {
		this.rangoUsuario = rango;
		this.idEmpleado = idEmp;
		this.contraseñaOriginal = contraseña;
		
		inicializarComponentes();
		configInicial();
		
		// Rellenamos los campos con los datos actuales del empleado
		txtNombre.setText(nombre);
		txtApodo.setText(apodo);
		cbCategoria.setSelectedItem(categoria);
		txtContraseñaAnterior.setText(contraseña);
	}

	// Configuración de las propiedades base de la ventana
	private void configInicial() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setSize(960, 540);
		setLocationRelativeTo(null);
	}

	// Inicialización y posicionamiento de todos los elementos visuales en el contenedor
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

		JPanel pnlBarraHorizontal = new JPanel();
		pnlBarraHorizontal.setBackground(new Color(196, 204, 203));
		pnlBarraHorizontal.setBounds(0, 111, 944, 282);
		pnlBarraHorizontal.setLayout(null);
		getContentPane().add(pnlBarraHorizontal);

		// TÍTULO DE LA PANTALLA
		JLabel lblTitulo = new JLabel("Modificar Empleado");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblTitulo.setBounds(22, 63, 400, 40);
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
		Image imgAtras = iconoAtras.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
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
		
		// TEXTO DE INFO SUPERIOR (Nombre e ID dinámicos)
		JLabel lblEmpleadoInfo = new JLabel("", SwingConstants.CENTER);
		lblEmpleadoInfo.setFont(new Font("Verdana", Font.BOLD, 16));
		lblEmpleadoInfo.setForeground(new Color(50, 50, 50));
		lblEmpleadoInfo.setBounds(10, 2, 762, 25); 
		pnlFormulario.add(lblEmpleadoInfo);
		
		Timer t = new Timer(1, e -> lblEmpleadoInfo.setText(txtNombre.getText() + " - " + idEmpleado));
		t.setRepeats(false);
		t.start();

		// COLUMNA IZQUIERDA (DATOS BASE)
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNombre.setBounds(30, 30, 100, 30);
		pnlFormulario.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtNombre.setBounds(140, 30, 200, 30);
		pnlFormulario.add(txtNombre);

		JLabel lblApodo = new JLabel("Apodo:");
		lblApodo.setFont(new Font("Verdana", Font.BOLD, 14));
		lblApodo.setBounds(30, 90, 100, 30);
		pnlFormulario.add(lblApodo);
		
		txtApodo = new JTextField();
		txtApodo.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtApodo.setBounds(140, 90, 200, 30);
		pnlFormulario.add(txtApodo);

		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setFont(new Font("Verdana", Font.BOLD, 14));
		lblCategoria.setBounds(30, 150, 100, 30);
		pnlFormulario.add(lblCategoria);
		
		cbCategoria = new JComboBox<>(new String[]{"Aprendiz", "Oficial", "Maestro"});
		cbCategoria.setFont(new Font("Verdana", Font.PLAIN, 14));
		cbCategoria.setBounds(140, 150, 200, 30);
		pnlFormulario.add(cbCategoria);

		// COLUMNA DERECHA (CONTRASEÑAS GESTIONADAS)
		JLabel lblContraseñaAnterior = new JLabel("pass Anterior:");
		lblContraseñaAnterior.setFont(new Font("Verdana", Font.BOLD, 13));
		lblContraseñaAnterior.setBounds(380, 30, 130, 30);
		pnlFormulario.add(lblContraseñaAnterior);
		
		txtContraseñaAnterior = new JPasswordField();
		txtContraseñaAnterior.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtContraseñaAnterior.setBounds(520, 30, 180, 30);
		pnlFormulario.add(txtContraseñaAnterior);

		// Guardamos el formato de ocultación nativo por defecto (los puntos)
		ecoPorDefecto = txtContraseñaAnterior.getEchoChar();

		// BOTÓN OJO INTEGRADO
		btnVerContraseña = new JButton("o");
		btnVerContraseña.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
		btnVerContraseña.setBounds(705, 30, 45, 30);
		btnVerContraseña.setBackground(new Color(210, 225, 230));
		pnlFormulario.add(btnVerContraseña);

		JLabel lblContraseñaNueva = new JLabel("pass Nueva:");
		lblContraseñaNueva.setFont(new Font("Verdana", Font.BOLD, 13));
		lblContraseñaNueva.setBounds(380, 90, 130, 30);
		pnlFormulario.add(lblContraseñaNueva);
		
		txtContraseñaNueva = new JPasswordField();
		txtContraseñaNueva.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtContraseñaNueva.setBounds(520, 90, 180, 30);
		pnlFormulario.add(txtContraseñaNueva);

		JLabel lblContraseñaRepetir = new JLabel("Repetir pass:");
		lblContraseñaRepetir.setFont(new Font("Verdana", Font.BOLD, 13));
		lblContraseñaRepetir.setBounds(380, 150, 130, 30);
		pnlFormulario.add(lblContraseñaRepetir);
		
		txtContraseñaRepetir = new JPasswordField();
		txtContraseñaRepetir.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtContraseñaRepetir.setBounds(520, 150, 180, 30);
		pnlFormulario.add(txtContraseñaRepetir);

		// IMAGEN DE FONDO
		JLabel lblFondo = new JLabel(new ImageIcon("img\\fondo.jpeg"));
		lblFondo.setBounds(0, 0, 944, 501);
		getContentPane().add(lblFondo);
	}

	// Método para vincular los escuchadores con los botones desde el controlador externo
	public void setControlador(ControladorModificarEmpleado c) {
		btnGuardarCambios.addActionListener(c);
		btnAtras.addActionListener(c);
		btnVerContraseña.addActionListener(c); 
	}

	// Método para cambiar simultáneamente la máscara de las tres casillas de contraseñas
	public void cambiarMascaraContraseñas(char caracter) {
		txtContraseñaAnterior.setEchoChar(caracter);
		txtContraseñaNueva.setEchoChar(caracter);
		txtContraseñaRepetir.setEchoChar(caracter);
	}

	// --- GETTERS ESTÁNDAR PARA EL CONTROLADOR ---
	public char getEcoPorDefecto() { return ecoPorDefecto; }
	public JButton getBtnGuardarCambios() { return btnGuardarCambios; }
	public JButton getBtnAtras() { return btnAtras; }
	public JButton getBtnVerContraseña() { return btnVerContraseña; }
	public String getRangoUsuario() { return rangoUsuario; }
	public int getIdEmpleado() { return idEmpleado; }
	public String getContraseñaOriginal() { return contraseñaOriginal; }

	public String getNombre() { return txtNombre.getText(); }
	public String getApodo() { return txtApodo.getText(); }
	public String getCategoria() { return cbCategoria.getSelectedItem().toString(); }
	
	public String getContraseñaAnterior() { return new String(txtContraseñaAnterior.getPassword()); }
	public String getContraseñaNueva() { return new String(txtContraseñaNueva.getPassword()); }
	public String getContraseñaRepetir() { return new String(txtContraseñaRepetir.getPassword()); }
}