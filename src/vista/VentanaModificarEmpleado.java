package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import controlador.empleado.ControladorModificarEmpleado;

public class VentanaModificarEmpleado extends JFrame {

	private String rangoUsuario;
	private int idEmpleado; // El empleado que estamos editando
	private String contrasenaOriginal; // Guardamos la contraseña actual para comprobaciones
	
	private JTextField txtNombre;
	private JTextField txtApodo;
	private JComboBox<String> cbCategoria;
	
	// Campos nuevos de contraseña protegidos
	private JPasswordField txtContrasenaAnterior;
	private JPasswordField txtContrasenaNueva;
	private JPasswordField txtContrasenaRepetir;
	private JButton btnVerContrasena;
	private boolean mostrandoContrasena = false;
	
	private JButton btnGuardarCambios;
	private JButton btnAtras;

	public VentanaModificarEmpleado(String rango, int idEmp, String nombre, String apodo, String categoria, String contrasena) {
		this.rangoUsuario = rango;
		this.idEmpleado = idEmp;
		this.contrasenaOriginal = contrasena;
		
		inicializarComponentes();
		configInicial();
		
		// Rellenamos los campos con los datos actuales
		txtNombre.setText(nombre);
		txtApodo.setText(apodo);
		cbCategoria.setSelectedItem(categoria);
		txtContrasenaAnterior.setText(contrasena);
	}

	private void configInicial() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setSize(960, 540);
		setLocationRelativeTo(null);
	}

	private void inicializarComponentes() {
		JPanel pnlFooter = new JPanel();
		pnlFooter.setBackground(new Color(72, 119, 109));
		pnlFooter.setBounds(0, 481, 944, 20);
		getContentPane().add(pnlFooter);

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

		JLabel lblTitulo = new JLabel("Modificar Empleado");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblTitulo.setBounds(22, 63, 400, 40);
		getContentPane().add(lblTitulo);

		btnGuardarCambios = new JButton("Guardar");
		btnGuardarCambios.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnGuardarCambios.setBackground(new Color(165, 191, 201));
		btnGuardarCambios.setBounds(22, 231, 109, 30);
		pnlBarraHorizontal.add(btnGuardarCambios);

		btnAtras = new JButton("");
		ImageIcon iconoAtras = new ImageIcon("img\\flecha_izq.png");
		Image imgAtras = iconoAtras.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnAtras.setIcon(new ImageIcon(imgAtras));
		btnAtras.setBackground(new Color(165, 191, 201));
		btnAtras.setBounds(22, 11, 30, 30);
		getContentPane().add(btnAtras);

		// Formulario
		JPanel pnlFormulario = new JPanel();
		pnlFormulario.setBorder(new LineBorder(new Color(68, 68, 68), 1, true));
		pnlFormulario.setBackground(new Color(165, 191, 201));
		pnlFormulario.setBounds(139, 25, 782, 236);
		pnlFormulario.setLayout(null);
		pnlBarraHorizontal.add(pnlFormulario);

		//  COLUMNA IZQUIERDA 
		// Nombre
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNombre.setBounds(30, 30, 100, 30);
		pnlFormulario.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtNombre.setBounds(140, 30, 200, 30);
		pnlFormulario.add(txtNombre);

		// Apodo
		JLabel lblApodo = new JLabel("Apodo:");
		lblApodo.setFont(new Font("Verdana", Font.BOLD, 14));
		lblApodo.setBounds(30, 90, 100, 30);
		pnlFormulario.add(lblApodo);
		
		txtApodo = new JTextField();
		txtApodo.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtApodo.setBounds(140, 90, 200, 30);
		pnlFormulario.add(txtApodo);

		// Categoría
		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setFont(new Font("Verdana", Font.BOLD, 14));
		lblCategoria.setBounds(30, 150, 100, 30);
		pnlFormulario.add(lblCategoria);
		
		cbCategoria = new JComboBox<>(new String[]{"Aprendiz", "Oficial", "Maestro"});
		cbCategoria.setFont(new Font("Verdana", Font.PLAIN, 14));
		cbCategoria.setBounds(140, 150, 200, 30);
		pnlFormulario.add(cbCategoria);

		// COLUMNA DERECHA
		// Contraseña Anterior
		JLabel lblContrasenaAnterior = new JLabel("pass Anterior:");
		lblContrasenaAnterior.setFont(new Font("Verdana", Font.BOLD, 13));
		lblContrasenaAnterior.setBounds(380, 30, 130, 30);
		pnlFormulario.add(lblContrasenaAnterior);
		
		txtContrasenaAnterior = new JPasswordField();
		txtContrasenaAnterior.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtContrasenaAnterior.setBounds(520, 30, 180, 30);
		pnlFormulario.add(txtContrasenaAnterior);

		// Botón ojo para ver/ocultar contraseña
		btnVerContrasena = new JButton("👁");
		btnVerContrasena.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
		btnVerContrasena.setBounds(705, 30, 45, 30);
		btnVerContrasena.setBackground(new Color(210, 225, 230));
		pnlFormulario.add(btnVerContrasena);
		
		btnVerContrasena.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mostrandoContrasena) {
					txtContrasenaAnterior.setEchoChar('●');
					txtContrasenaNueva.setEchoChar('●');
					txtContrasenaRepetir.setEchoChar('●');
					mostrandoContrasena = false;
				} else {
					txtContrasenaAnterior.setEchoChar((char) 0);
					txtContrasenaNueva.setEchoChar((char) 0);
					txtContrasenaRepetir.setEchoChar((char) 0);
					mostrandoContrasena = true;
				}
			}
		});

		// Contraseña Nueva
		JLabel lblContrasenaNueva = new JLabel("pass Nueva:");
		lblContrasenaNueva.setFont(new Font("Verdana", Font.BOLD, 13));
		lblContrasenaNueva.setBounds(380, 90, 130, 30);
		pnlFormulario.add(lblContrasenaNueva);
		
		txtContrasenaNueva = new JPasswordField();
		txtContrasenaNueva.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtContrasenaNueva.setBounds(520, 90, 180, 30);
		pnlFormulario.add(txtContrasenaNueva);

		// Repetir Contraseña
		JLabel lblContrasenaRepetir = new JLabel("Repetir pass:");
		lblContrasenaRepetir.setFont(new Font("Verdana", Font.BOLD, 13));
		lblContrasenaRepetir.setBounds(380, 150, 130, 30);
		pnlFormulario.add(lblContrasenaRepetir);
		
		txtContrasenaRepetir = new JPasswordField();
		txtContrasenaRepetir.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtContrasenaRepetir.setBounds(520, 150, 180, 30);
		pnlFormulario.add(txtContrasenaRepetir);

		// Fondo de la ventana
		JLabel lblFondo = new JLabel(new ImageIcon("img\\fondo.jpeg"));
		lblFondo.setBounds(0, 0, 944, 501);
		getContentPane().add(lblFondo);
	}

	public void setControlador(ControladorModificarEmpleado c) {
		btnGuardarCambios.addActionListener(c);
		btnAtras.addActionListener(c);
	}

	// GETTERS estándar
	public JButton getBtnGuardarCambios() { return btnGuardarCambios; }
	public JButton getBtnAtras() { return btnAtras; }
	public String getRangoUsuario() { return rangoUsuario; }
	public int getIdEmpleado() { return idEmpleado; }
	public String getContrasenaOriginal() { return contrasenaOriginal; }

	// Métodos mapeados para los campos de texto normales
	public String getNombre() { return txtNombre.getText(); }
	public String getApodo() { return txtApodo.getText(); }
	public String getCategoria() { return cbCategoria.getSelectedItem().toString(); }
	
	// Getters nuevos para manejar contraseñas seguras
	public String getContrasenaAnterior() { return new String(txtContrasenaAnterior.getPassword()); }
	public String getContrasenaNueva() { return new String(txtContrasenaNueva.getPassword()); }
	public String getContrasenaRepetir() { return new String(txtContrasenaRepetir.getPassword()); }
}