package vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import controlador.cita.ControladorMenuCita;
import modelo.Cita;

import java.util.ArrayList;

public class VentanaGestionCita extends JFrame {

	// Declaracion de variables
	private String rangoUsuario;
	private int idEmpleadoLogin;
	private JButton btnEliminarCita;
	private JButton btnCrearCita;
	private JButton btnModificarCita;
	private DefaultTableModel modeloTabla;
	private JTable table;
	private JLabel lblTitulo;
	private JButton btnCerrarSesion;
	private JButton btnAtras;
	private JButton btnVerCitasPropias;
	private String nombreUsuario;
	private JButton btnVerTodas;
	

	public VentanaGestionCita(String rango, int idEmpleado) {
		this.rangoUsuario = rango;
		this.idEmpleadoLogin = idEmpleado;
		inicializarComponentes();
		configInicial();
		configurarPermisos();
	}

	/**
	 * Metodo para determinar la configuracion inicial de la ventana.
	 */
	private void configInicial() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setSize(960, 540);
		setLocationRelativeTo(null);
	}

	/**
	 * Metodo para que dependiendo de que tipo de empleado haya ingresado, se
	 * muestren unas cosas u otras
	 */
	private void configurarPermisos() {
		if (rangoUsuario.equals("Maestro")) {
			lblTitulo.setText("Gestion de Citas");
		} else if (rangoUsuario.equals("Oficial")) {
			btnCrearCita.setVisible(true);
			btnEliminarCita.setVisible(true);
			btnModificarCita.setVisible(true);
			lblTitulo.setText("Gestion de citas");
		} else if (rangoUsuario.equals("Aprendiz")) {
			btnCrearCita.setVisible(false);
			btnEliminarCita.setVisible(false);
			btnModificarCita.setVisible(false);
			lblTitulo.setText("Mis Citas");
		}

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

		// Panel horizontal
		JPanel pnlBarraHorizontal = new JPanel();
		pnlBarraHorizontal.setForeground(new Color(196, 204, 203));
		pnlBarraHorizontal.setBackground(new Color(196, 204, 203));
		pnlBarraHorizontal.setBounds(0, 111, 944, 282);
		getContentPane().add(pnlBarraHorizontal);
		pnlBarraHorizontal.setLayout(null);

		// Boton Cerrar Sesion
		btnCerrarSesion = new JButton("Cerrar sesión");
		btnCerrarSesion.setBounds(5, 230, 140, 30);
		pnlBarraHorizontal.add(btnCerrarSesion);
		btnCerrarSesion.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnCerrarSesion.setBackground(new Color(165, 191, 201));
		btnCerrarSesion.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnCerrarSesion.setBackground(new Color(165, 191, 201));

		// Titulo Pagina
		lblTitulo = new JLabel("Gestión de Citas");
		lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblTitulo.setBounds(22, 63, 298, 40);
		getContentPane().add(lblTitulo);
		
		modelo.Modelo m = new modelo.Modelo();
		nombreUsuario = m.obtenerNombreEmpleado(idEmpleadoLogin);
		
		JLabel lblUsuarioLogueado = new JLabel(nombreUsuario + " (" + rangoUsuario + ")");
		lblUsuarioLogueado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuarioLogueado.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblUsuarioLogueado.setForeground(new Color(68, 68, 68));
		lblUsuarioLogueado.setBounds(622, 13, 310, 30);
		getContentPane().add(lblUsuarioLogueado);

		// BOTONES
		btnCrearCita = new JButton("Crear");
		btnCrearCita.setBackground(new Color(165, 191, 201));
		btnCrearCita.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnCrearCita.setBounds(22, 25, 109, 30);
		pnlBarraHorizontal.add(btnCrearCita);

		btnEliminarCita = new JButton("Eliminar");
		btnEliminarCita.setBackground(new Color(165, 191, 201));
		btnEliminarCita.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnEliminarCita.setBounds(22, 63, 109, 30);
		pnlBarraHorizontal.add(btnEliminarCita);

		btnModificarCita = new JButton("Modificar");
		btnModificarCita.setBackground(new Color(165, 191, 201));
		btnModificarCita.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnModificarCita.setBounds(22, 101, 109, 30);
		pnlBarraHorizontal.add(btnModificarCita);

		btnAtras = new JButton("");
		ImageIcon iconoAtras = new ImageIcon("img\\flecha_izq.png");
		java.awt.Image imgAtras = iconoAtras.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		btnAtras.setIcon(new ImageIcon(imgAtras));
		btnAtras.setBackground(new Color(165, 191, 201));
		btnAtras.setFont(new Font("Verdana", Font.PLAIN, 5));
		btnAtras.setBounds(22, 11, 30, 30); // Posición arriba a la izquierda
		getContentPane().add(btnAtras);
		
		btnVerCitasPropias = new JButton("Citas Propias");
		btnVerCitasPropias.setBackground(new Color(165, 191, 201));
		btnVerCitasPropias.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnVerCitasPropias.setBounds(5, 195, 140, 30);
		pnlBarraHorizontal.add(btnVerCitasPropias);
		
		btnVerTodas = new JButton("Todas las citas");
		btnVerTodas.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnVerTodas.setBackground(new Color(165, 191, 201));
		btnVerTodas.setBounds(5, 160, 140, 30);
		pnlBarraHorizontal.add(btnVerTodas);

		// Panel con informacion
		JPanel pnlBarraHorizontal_1 = new JPanel();
		pnlBarraHorizontal_1.setBorder(new LineBorder(new Color(68, 68, 68), 1, true));
		pnlBarraHorizontal_1.setLayout(null);
		pnlBarraHorizontal_1.setForeground(new Color(196, 204, 203));
		pnlBarraHorizontal_1.setBackground(new Color(165, 191, 201));
		pnlBarraHorizontal_1.setBounds(150, 25, 782, 236);
		pnlBarraHorizontal.add(pnlBarraHorizontal_1);

		// ScrollPane para la tabla
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 762, 216);
		pnlBarraHorizontal_1.add(scrollPane);

		// Tabla
		table = new JTable();
		scrollPane.setViewportView(table);
		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("ID");
		modeloTabla.addColumn("DÍA");
		modeloTabla.addColumn("HORA");
		modeloTabla.addColumn("DURACIÓN");
		modeloTabla.addColumn("CLIENTE");
		modeloTabla.addColumn("TALLER");
		modeloTabla.addColumn("RESPONSABLE");
		modeloTabla.addColumn("TRAJE");

		table = new JTable(modeloTabla);
		scrollPane.setViewportView(table);

		// Hacemos esto para poder utilizar el id pero tenerlo oculto de la tabla,
		// hacemos que la anchura sea de 0 y asi se "oculta"
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		

		// FONDO
		JLabel lblFondo = new JLabel("");
		lblFondo.setBounds(0, 0, 944, 501);
		getContentPane().add(lblFondo);
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpeg"));
	}

	public int getIdCitaSeleccionado() {
		int filaSeleccionada = table.getSelectedRow();
		if (filaSeleccionada == -1)
			return -1;
		return (int) modeloTabla.getValueAt(filaSeleccionada, 0);
	}

	public void setControlador(ControladorMenuCita c) {
		btnEliminarCita.addActionListener(c);
		btnCrearCita.addActionListener(c);
		btnModificarCita.addActionListener(c);
		btnAtras.addActionListener(c);
		btnCerrarSesion.addActionListener(c);
		btnVerCitasPropias.addActionListener(c);
		btnVerTodas.addActionListener(c);
	}

	public void cargarDatosCitas(ArrayList<Cita> datosCitas) {
		modeloTabla.setRowCount(0);
		for (Cita c : datosCitas) {
			Object[] fila = new Object[8]; 	        
			fila[0] = c.getId_cita();      	        
			fila[1] = c.getDia();
	        fila[2] = c.getHora();
	        fila[3] = c.getDuracion();
	        fila[4] = c.getNombreCliente();
	        fila[5] = c.getNombreTaller();
	        fila[6] = c.getNombreEmpleadoResponsable();
	        fila[7] = c.getNombreTraje();

			modeloTabla.addRow(fila);
		}
	}

	/**
	 * @return the btnEliminarCita
	 */
	public JButton getBtnEliminarCita() {
		return btnEliminarCita;
	}

	/**
	 * @return the btnCrearCita
	 */
	public JButton getBtnCrearCita() {
		return btnCrearCita;
	}

	/**
	 * @return the btnModificarCita
	 */
	public JButton getBtnModificarCita() {
		return btnModificarCita;
	}

	/**
	 * @return the btnCerrarSesion
	 */
	public JButton getBtnCerrarSesion() {
		return btnCerrarSesion;
	}

	/**
	 * @return the btnAtras
	 */
	public JButton getBtnAtras() {
		return btnAtras;
	}

	/**
	 * @return the rangoUsuario
	 */
	public String getRangoUsuario() {
		return rangoUsuario;
	}

	/**
	 * @return the modeloTabla
	 */
	public DefaultTableModel getModeloTabla() {
		return modeloTabla;
	}

	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * @return the btnVerCitasPropias
	 */
	public JButton getBtnVerCitasPropias() {
		return btnVerCitasPropias;
	}

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @return the btnVerTodasLasCitas
	 */
	public JButton getBtnVerTodas() {
		return btnVerTodas;
	}
}
