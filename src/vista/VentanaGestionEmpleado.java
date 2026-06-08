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
	private int idUsuario;
	
	private JButton btnEliminar;
	private JButton btnCrear;
	private JButton btnModificar;
	private JButton btnCerrarSesion;
	private JButton btnAtras;
	private DefaultTableModel modeloTabla;
	private JTable table;

	public VentanaGestionEmpleado(String rango, int id) {
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

		btnCerrarSesion = new JButton("Cerrar sesión");
		btnCerrarSesion.setBounds(5, 211, 140, 30);
		btnCerrarSesion.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnCerrarSesion.setBackground(new Color(165, 191, 201));
		pnlBarraHorizontal.add(btnCerrarSesion);

		JLabel lblTitulo = new JLabel("Gestión de Empleados");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblTitulo.setBounds(22, 63, 400, 40);
		getContentPane().add(lblTitulo);

		btnCrear = new JButton("Crear");
		btnCrear.setBackground(new Color(165, 191, 201));
		btnCrear.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnCrear.setBounds(22, 25, 109, 30);
		pnlBarraHorizontal.add(btnCrear);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBackground(new Color(165, 191, 201));
		btnEliminar.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnEliminar.setBounds(22, 63, 109, 30);
		pnlBarraHorizontal.add(btnEliminar);

		btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(165, 191, 201));
		btnModificar.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnModificar.setBounds(22, 101, 109, 30);
		pnlBarraHorizontal.add(btnModificar);

		btnAtras = new JButton("");
		ImageIcon iconoAtras = new ImageIcon("img\\flecha_izq.png");
		Image imgAtras = iconoAtras.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnAtras.setIcon(new ImageIcon(imgAtras));
		btnAtras.setBackground(new Color(165, 191, 201));
		btnAtras.setBounds(22, 11, 30, 30);
		getContentPane().add(btnAtras);

		JPanel pnlBarraHorizontal_1 = new JPanel();
		pnlBarraHorizontal_1.setBorder(new LineBorder(new Color(68, 68, 68), 1, true));
		pnlBarraHorizontal_1.setBackground(new Color(165, 191, 201));
		pnlBarraHorizontal_1.setBounds(150, 25, 782, 236);
		pnlBarraHorizontal_1.setLayout(null);
		pnlBarraHorizontal.add(pnlBarraHorizontal_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 762, 216);
		pnlBarraHorizontal_1.add(scrollPane);

		// Dejamos la JTable inicializada vacía
		table = new JTable();
		scrollPane.setViewportView(table);

		JLabel lblFondo = new JLabel(new ImageIcon("img\\fondo.jpeg"));
		lblFondo.setBounds(0, 0, 944, 501);
		getContentPane().add(lblFondo);
	}

	public void setControlador(ControladorMenuEmpleado c) {
		btnCrear.addActionListener(c);
		btnEliminar.addActionListener(c);
		btnModificar.addActionListener(c);
		btnCerrarSesion.addActionListener(c);
		btnAtras.addActionListener(c);
	}

	public void cargarDatosEmpleados(ArrayList<Empleado> datos) {
		String[] columnas = {"ID", "NOMBRE", "APODO", "CATEGORÍA", "CONTRASEÑA"};
		
		// Estilo idéntico a Trajes: Hacemos que la tabla no sea editable directamente
		modeloTabla = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		for (Empleado e : datos) {
			Object[] fila = {
				e.getId_empleado(),
				e.getNombre(),
				e.getApodo(),
				e.getCategoría(),
				e.getContraseña()
			};
			modeloTabla.addRow(fila);
		}
		
		table.setModel(modeloTabla);
		
		// Ocultar columna ID (columna 0) para que funcione en segundo plano de forma invisible
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
	}

	// GETTERS
	public JButton getBtnEliminar() { return btnEliminar; }
	public JButton getBtnCrear() { return btnCrear; }
	public JButton getBtnModificar() { return btnModificar; }
	public JButton getBtnCerrarSesion() { return btnCerrarSesion; }
	public JButton getBtnAtras() { return btnAtras; }
	public String getRangoUsuario() { return rangoUsuario; }
	public int getIdUsuario() { return idUsuario; }

	// Métodos para obtener los datos de la fila seleccionada en segundo plano
	public int getIdEmpleadoSeleccionado() {
		int fila = table.getSelectedRow();
		return (fila == -1) ? -1 : (int) table.getValueAt(fila, 0);
	}
	
	public String getNombreSeleccionado() { return table.getValueAt(table.getSelectedRow(), 1).toString(); }
	public String getApodoSeleccionado() { return table.getValueAt(table.getSelectedRow(), 2).toString(); }
	public String getCategoriaSeleccionada() { return table.getValueAt(table.getSelectedRow(), 3).toString(); }
	public String getContrasenaSeleccionada() { return table.getValueAt(table.getSelectedRow(), 4).toString(); }
}