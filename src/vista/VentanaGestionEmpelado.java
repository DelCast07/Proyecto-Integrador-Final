package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import controlador.empleado.ControladorMenuEmpleado;
import modelo.Empleado;

public class VentanaGestionEmpleado extends JFrame {

	private String rangoUsuario;
	private int idEmpleadoLogin;
	private JButton btnEliminarEmpleado;
	private JButton btnCrearEmpleado;
	private JButton btnModificarEmpleado;
	private DefaultTableModel modeloTabla;
	private JTable table;
	private JLabel lblTitulo;
	private JButton btnCerrarSesion;
	private JButton btnAtras;
	private String nombreUsuario;

	public VentanaGestionEmpleado(String rango, int idEmpleado) {
		this.rangoUsuario = rango;
		this.idEmpleadoLogin = idEmpleado;
		inicializarComponentes();
		configInicial();
		configurarPermisos();
	}

	private void configInicial() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setSize(960, 540);
		setLocationRelativeTo(null);
	}

	private void configurarPermisos() {
		if (rangoUsuario.equals("Maestro")) {
			lblTitulo.setText("Gestion de Empleados");
		} else if (rangoUsuario.equals("Oficial")) {
			btnCrearEmpleado.setVisible(true);
			btnEliminarEmpleado.setVisible(true);
			btnModificarEmpleado.setVisible(true);
			lblTitulo.setText("Gestion de empleados");
		} else if (rangoUsuario.equals("Aprendiz")) {
			btnCrearEmpleado.setVisible(false);
			btnEliminarEmpleado.setVisible(false);
			btnModificarEmpleado.setVisible(false);
			lblTitulo.setText("Sección Empleados");
		}
	}

	private void inicializarComponentes() {
		// Footer
		JPanel pnlFooter = new JPanel();
		pnlFooter.setBackground(new Color(72, 119, 109));
		pnlFooter.setBounds(0, 481, 944, 20);
		getContentPane().add(pnlFooter);

		// el textito de abajo
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

		// Botón Cerrar Sesión
		btnCerrarSesion = new JButton("Cerrar sesión");
		btnCerrarSesion.setBounds(5, 230, 140, 30);
		pnlBarraHorizontal.add(btnCerrarSesion);
		btnCerrarSesion.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnCerrarSesion.setBackground(new Color(165, 191, 201));

		// Titulo Pagina
		lblTitulo = new JLabel("Gestión de Empleados");
		lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblTitulo.setBounds(22, 63, 400, 40);
		getContentPane().add(lblTitulo);
		
		modelo.Modelo m = new modelo.Modelo();
		nombreUsuario = m.obtenerNombreEmpleado(idEmpleadoLogin);
		
		JLabel lblUsuarioLogueado = new JLabel(nombreUsuario + " (" + rangoUsuario + ")");
		lblUsuarioLogueado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuarioLogueado.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblUsuarioLogueado.setForeground(new Color(68, 68, 68));
		lblUsuarioLogueado.setBounds(622, 13, 310, 30);
		getContentPane().add(lblUsuarioLogueado);

		// Botones
		btnCrearEmpleado = new JButton("Crear");
		btnCrearEmpleado.setBackground(new Color(165, 191, 201));
		btnCrearEmpleado.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnCrearEmpleado.setBounds(22, 25, 109, 30);
		pnlBarraHorizontal.add(btnCrearEmpleado);

		btnEliminarEmpleado = new JButton("Eliminar");
		btnEliminarEmpleado.setBackground(new Color(165, 191, 201));
		btnEliminarEmpleado.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnEliminarEmpleado.setBounds(22, 63, 109, 30);
		pnlBarraHorizontal.add(btnEliminarEmpleado);

		btnModificarEmpleado = new JButton("Modificar");
		btnModificarEmpleado.setBackground(new Color(165, 191, 201));
		btnModificarEmpleado.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnModificarEmpleado.setBounds(22, 101, 109, 30);
		pnlBarraHorizontal.add(btnModificarEmpleado);

		// Botón Atrás
		btnAtras = new JButton("");
		ImageIcon iconoAtras = new ImageIcon("img\\flecha_izq.png");
		Image imgAtras = iconoAtras.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnAtras.setIcon(new ImageIcon(imgAtras));
		btnAtras.setBackground(new Color(165, 191, 201));
		btnAtras.setBounds(22, 11, 30, 30);
		getContentPane().add(btnAtras);

		// Panel con información
		JPanel pnlBarraHorizontal_1 = new JPanel();
		pnlBarraHorizontal_1.setBorder(new LineBorder(new Color(68, 68, 68), 1, true));
		pnlBarraHorizontal_1.setLayout(null);
		pnlBarraHorizontal_1.setForeground(new Color(196, 204, 203));
		pnlBarraHorizontal_1.setBackground(new Color(165, 191, 201));
		pnlBarraHorizontal_1.setBounds(150, 25, 782, 236);
		pnlBarraHorizontal.add(pnlBarraHorizontal_1);

		// ScrollPanell
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 762, 216);
		pnlBarraHorizontal_1.add(scrollPane);

		// Tabla
		table = new JTable();
		scrollPane.setViewportView(table);
		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("ID");
		modeloTabla.addColumn("NOMBRE");
		modeloTabla.addColumn("APODO");
		modeloTabla.addColumn("CATEGORÍA");
		modeloTabla.addColumn("CONTRASEÑA");

		table = new JTable(modeloTabla);
		scrollPane.setViewportView(table);

		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);

		// Fondo
		JLabel lblFondo = new JLabel("");
		lblFondo.setBounds(0, 0, 944, 501);
		getContentPane().add(lblFondo);
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpeg"));
	}

	public int getIdEmpleadoSeleccionado() {
		int filaSeleccionada = table.getSelectedRow();
		if (filaSeleccionada == -1)
			return -1;
		return (int) modeloTabla.getValueAt(filaSeleccionada, 0);
	}

	public String getNombreEmpleadoSeleccionado() {
		int filaSeleccionada = table.getSelectedRow();
		return (filaSeleccionada != -1) ? modeloTabla.getValueAt(filaSeleccionada, 1).toString() : null;
	}

	public String getApodoEmpleadoSeleccionado() {
		int filaSeleccionada = table.getSelectedRow();
		return (filaSeleccionada != -1) ? modeloTabla.getValueAt(filaSeleccionada, 2).toString() : null;
	}

	public String getCategoriaEmpleadoSeleccionado() {
		int filaSeleccionada = table.getSelectedRow();
		return (filaSeleccionada != -1) ? modeloTabla.getValueAt(filaSeleccionada, 3).toString() : null;
	}

	public String getContrasenaEmpleadoSeleccionado() {
		int filaSeleccionada = table.getSelectedRow();
		return (filaSeleccionada != -1) ? modeloTabla.getValueAt(filaSeleccionada, 4).toString() : null;
	}

	public void setControlador(ControladorMenuEmpleado c) {
		btnEliminarEmpleado.addActionListener(c);
		btnCrearEmpleado.addActionListener(c);
		btnModificarEmpleado.addActionListener(c);
		btnAtras.addActionListener(c);
		btnCerrarSesion.addActionListener(c);
	}

	public void cargarDatosEmpleados(ArrayList<Empleado> datosEmpleados) {
		modeloTabla.setRowCount(0);
		for (Empleado emp : datosEmpleados) {
			Object[] fila = new Object[5];
			fila[0] = emp.getId_empleado();
			fila[1] = emp.getNombre();
			fila[2] = emp.getApodo();
			fila[3] = emp.getCategoría();
			fila[4] = emp.getContraseña();
			modeloTabla.addRow(fila);
		}
	}

	public JButton getBtnEliminarEmpleado() { return btnEliminarEmpleado; }
	public JButton getBtnCrearEmpleado() { return btnCrearEmpleado; }
	public JButton getBtnModificarEmpleado() { return btnModificarEmpleado; }
	public JButton getBtnCerrarSesion() { return btnCerrarSesion; }
	public JButton getBtnAtras() { return btnAtras; }
	public String getRangoUsuario() { return rangoUsuario; }
	public DefaultTableModel getModeloTabla() { return modeloTabla; }
	public JTable getTable() { return table; }
	public String getNombreUsuario() { return nombreUsuario; }
}