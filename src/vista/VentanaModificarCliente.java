package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class VentanaModificarCliente extends JFrame {

	private String rangoUsuario;
	private int idUsuario;
	private JTextField txtIdCliente;
	private JTextField txtNombreCliente;
	private JTextField txtSuperpoder;
	private JTextField txtColores;
	private JButton btnGuardarCambios;
	private JButton btnAtras;
	private JButton btnCerrarSesion;
	
	public VentanaModificarCliente(String rango, int id, String nombre, String superpoder, String colores) {
		this.rangoUsuario = rango;
		this.idUsuario = id;
		inicializarComponentes();
		configInicial();
		
		// cargamos datos en el formulario
		txtIdCliente.setText(String.valueOf(id));
		txtIdCliente.setEditable(false); 
		txtIdCliente.setBackground(new Color(210, 210, 210));
		txtNombreCliente.setText(nombre);
		txtSuperpoder.setText(superpoder);
		txtColores.setText(colores);
	}

	private void configInicial() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setSize(960, 540);
		setLocationRelativeTo(null);
		setTitle("Modificar Cliente existente");
	}

	private void inicializarComponentes() {

		// footer franja inferior
		JPanel pnlFooter = new JPanel();
		pnlFooter.setBackground(new Color(72, 119, 109));
		pnlFooter.setBounds(0, 481, 944, 20);
		getContentPane().add(pnlFooter);

		JLabel lblCopyright = new JLabel("© 2026 Payo-Vallecano, Inc. Todos los derechos reservados");
		lblCopyright.setHorizontalAlignment(SwingConstants.CENTER);
		lblCopyright.setForeground(new Color(255, 255, 255));
		lblCopyright.setFont(new Font("Verdana", Font.PLAIN, 10));
		pnlFooter.add(lblCopyright);

		// panel horizontal principal
		JPanel pnlBarraHorizontal = new JPanel();
		pnlBarraHorizontal.setBackground(new Color(196, 204, 203));
		pnlBarraHorizontal.setBounds(0, 111, 944, 282);
		getContentPane().add(pnlBarraHorizontal);
		pnlBarraHorizontal.setLayout(null);

		// boton cerrar sesion 
		btnCerrarSesion = new JButton("Cerrar sesión");
		btnCerrarSesion.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnCerrarSesion.setBackground(new Color(165, 191, 201));
		btnCerrarSesion.setBounds(787, 68, 135, 30);
		getContentPane().add(btnCerrarSesion);

		// titulo pagina
		JLabel lblTitulo = new JLabel("Modificar Cliente");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblTitulo.setBounds(22, 63, 400, 40);
		getContentPane().add(lblTitulo);
		
		modelo.Modelo m = new modelo.Modelo();
		String nombreUsuario = m.obtenerNombreEmpleado(idUsuario);
		
		JLabel lblUsuarioLogueado = new JLabel(nombreUsuario + " (" + rangoUsuario + ")");
		lblUsuarioLogueado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuarioLogueado.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblUsuarioLogueado.setForeground(new Color(68, 68, 68));
		lblUsuarioLogueado.setBounds(622, 13, 310, 30);
		getContentPane().add(lblUsuarioLogueado);

		// boton guardar cambios
		btnGuardarCambios = new JButton("Guardar");
		btnGuardarCambios.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnGuardarCambios.setBackground(new Color(165, 191, 201));
		btnGuardarCambios.setBounds(22, 231, 109, 30);
		pnlBarraHorizontal.add(btnGuardarCambios);

		// boton atras flecha
		btnAtras = new JButton("");
		ImageIcon iconoAtras = new ImageIcon("img\\flecha_izq.png");
		java.awt.Image imgAtras = iconoAtras.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		btnAtras.setIcon(new ImageIcon(imgAtras));
		btnAtras.setBackground(new Color(165, 191, 201));
		btnAtras.setBounds(22, 11, 30, 30);
		getContentPane().add(btnAtras);

		// panel formulario
		JPanel pnlFormulario = new JPanel();
		pnlFormulario.setBorder(new LineBorder(new Color(68, 68, 68), 1, true));
		pnlFormulario.setLayout(null);
		pnlFormulario.setBackground(new Color(165, 191, 201));
		pnlFormulario.setBounds(139, 25, 782, 236);
		pnlBarraHorizontal.add(pnlFormulario);

		// etiqueta y campo id cliente
		JLabel lblIdCliente = new JLabel("ID del Cliente:");
		lblIdCliente.setFont(new Font("Verdana", Font.BOLD, 14));
		lblIdCliente.setBounds(50, 20, 150, 30);
		pnlFormulario.add(lblIdCliente);
		
		txtIdCliente = new JTextField();
		txtIdCliente.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtIdCliente.setBounds(200, 20, 255, 30);
		pnlFormulario.add(txtIdCliente);

		// etiqueta y campo nombre
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNombre.setBounds(50, 70, 150, 30);
		pnlFormulario.add(lblNombre);
		
		txtNombreCliente = new JTextField();
		txtNombreCliente.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtNombreCliente.setBounds(200, 70, 255, 30);
		pnlFormulario.add(txtNombreCliente);

		// etiqueta y campo superpoder
		JLabel lblSuperpoder = new JLabel("Superpoder:");
		lblSuperpoder.setFont(new Font("Verdana", Font.BOLD, 14));
		lblSuperpoder.setBounds(50, 120, 150, 30);
		pnlFormulario.add(lblSuperpoder);
		
		txtSuperpoder = new JTextField();
		txtSuperpoder.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtSuperpoder.setBounds(200, 120, 255, 30);
		pnlFormulario.add(txtSuperpoder);

		// etiqueta y campo colores
		JLabel lblColores = new JLabel("Colores:");
		lblColores.setFont(new Font("Verdana", Font.BOLD, 14));
		lblColores.setBounds(50, 170, 150, 30);
		pnlFormulario.add(lblColores);
		
		txtColores = new JTextField();
		txtColores.setFont(new Font("Verdana", Font.PLAIN, 14));
		txtColores.setBounds(200, 170, 255, 30);
		pnlFormulario.add(txtColores);

		// imagen de fondo
		JLabel lblFondo = new JLabel("");
		lblFondo.setBounds(0, 0, 944, 501);
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpeg"));
		getContentPane().add(lblFondo);
	}
	
	// metodo para asignar el controlador a los botones
	public void setControladorModificar(ActionListener c) {
		btnGuardarCambios.addActionListener(c);
		btnAtras.addActionListener(c);
		btnCerrarSesion.addActionListener(c);
	}

	// metodos para obtener el texto de los campos
	public String getRangoUsuario() { return rangoUsuario; }
	public String getIdCliente() { return txtIdCliente.getText(); }
	public String getNombreCliente() { return txtNombreCliente.getText(); }
	public String getSuperpoderCliente() { return txtSuperpoder.getText(); }
	public String getColorCliente() { return txtColores.getText(); }
	
	// getters para el controlador
	public JButton getBtnGuardarCambios() { return btnGuardarCambios; }
	public JButton getBtnAtras() { return btnAtras; }
	public JButton getBtnCerrarSesion() { return btnCerrarSesion; }
}