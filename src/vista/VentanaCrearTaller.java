package vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.LineBorder;

import controlador.taller.ControladorCrearTaller;

public class VentanaCrearTaller extends JFrame {

	private String rangoUsuario;
	private int idUsuario;
	private JTextField txtIdTaller;
	private JTextField txtNombreTaller;
	private JComboBox<String> cmbTipoSala; // AHORA ES UN JCOMBOBOX
	private JButton btnGuardarCambios;
	private JButton btnAtras;
	private JButton btnCerrarSesion;
	
	public VentanaCrearTaller(String rango, int id) {
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
		JLabel lblNewLabel_1 = new JLabel("© 2026 Payo-Vallecano, Inc. Todos los derechos reservados");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 10));
		pnlFooter.add(lblNewLabel_1);

		// Panel horizontal principal
		JPanel pnlBarraHorizontal = new JPanel();
		pnlBarraHorizontal.setForeground(new Color(196, 204, 203));
		pnlBarraHorizontal.setBackground(new Color(196, 204, 203));
		pnlBarraHorizontal.setBounds(0, 111, 944, 282);
		getContentPane().add(pnlBarraHorizontal);
		pnlBarraHorizontal.setLayout(null);

		// Boton Cerrar Sesion
		btnCerrarSesion = new JButton("Cerrar sesión");
		btnCerrarSesion.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnCerrarSesion.setBackground(new Color(165, 191, 201));
		btnCerrarSesion.setBounds(787, 68, 135, 30);
		getContentPane().add(btnCerrarSesion);

		// Titulo Pagina
		JLabel lblTitulo = new JLabel("Crear Taller");
		lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
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

		// Botón Guardar Cambios
		btnGuardarCambios = new JButton("Guardar");
		btnGuardarCambios.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnGuardarCambios.setBackground(new Color(165, 191, 201));
		btnGuardarCambios.setBounds(22, 231, 109, 30);
		pnlBarraHorizontal.add(btnGuardarCambios);
		
		// Botón Atrás
		btnAtras = new JButton("");
		ImageIcon iconoAtras = new ImageIcon("img\\flecha_izq.png");
		java.awt.Image imgAtras = iconoAtras.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		btnAtras.setIcon(new ImageIcon(imgAtras));
		btnAtras.setBackground(new Color(165, 191, 201));
		btnAtras.setBounds(22, 11, 30, 30);
		getContentPane().add(btnAtras);

		// Panel con informacion (EL FORMULARIO)
		JPanel pnlFormulario = new JPanel();
		pnlFormulario.setBorder(new LineBorder(new Color(68, 68, 68), 1, true));
		pnlFormulario.setLayout(null);
		pnlFormulario.setBackground(new Color(165, 191, 201));
		pnlFormulario.setBounds(139, 25, 782, 236);
		pnlBarraHorizontal.add(pnlFormulario);

		// --- COMPONENTES DEL FORMULARIO ---
		JLabel lblNombreTaller = new JLabel("Nombre:");
		lblNombreTaller.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNombreTaller.setBounds(50, 71, 150, 30);
		pnlFormulario.add(lblNombreTaller);
		txtNombreTaller = new JTextField();
		txtNombreTaller.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtNombreTaller.setBounds(200, 71, 255, 30);
		pnlFormulario.add(txtNombreTaller);

		JLabel lblTipoSala = new JLabel("Tipo de Sala:");
		lblTipoSala.setFont(new Font("Verdana", Font.BOLD, 14));
		lblTipoSala.setBounds(50, 131, 150, 30);
		pnlFormulario.add(lblTipoSala);
		String[] opcionesSala = {"Diseño", "Costura", "Pruebas"};
		cmbTipoSala = new JComboBox<>(opcionesSala);
		cmbTipoSala.setFont(new Font("Verdana", Font.PLAIN, 14));
		cmbTipoSala.setBounds(200, 131, 255, 30);
		cmbTipoSala.setBackground(Color.WHITE); 
		pnlFormulario.add(cmbTipoSala);

		// FONDO
		JLabel lblFondo = new JLabel("");
		lblFondo.setBounds(0, 0, 944, 501);
		getContentPane().add(lblFondo);
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpeg"));
	}
	
	/**
	 * Metodo que llamaremos desde el controlador
	 * @param c
	 */
	public void setControladorGuardar(ControladorCrearTaller c) {
	    btnGuardarCambios.addActionListener(c);
	    btnAtras.addActionListener(c);
	    btnCerrarSesion.addActionListener(c);
	}

	public String getRangoUsuario() {
		return rangoUsuario;
	}

	public String getNombreTaller() {
		return txtNombreTaller.getText();
	}

	public String getTipoSala() {
		return cmbTipoSala.getSelectedItem().toString();
	}

	public JButton getBtnAtras() {
		return btnAtras;
	}

	public JButton getBtnCerrarSesion() {
		return btnCerrarSesion;
	}

	public JButton getBtnGuardarCambios() {
		return btnGuardarCambios;
	}
	
	
}
