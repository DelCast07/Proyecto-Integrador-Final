package vista.aprendiz;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import javax.swing.table.DefaultTableModel;

import controlador.ControladorMenuAprendiz;
import modelo.Cita;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class VentanaAprendiz extends JFrame {

	private DefaultTableModel modeloTabla;
	private JTable table;
	private JButton btnCerrarSesion;
	private int idUsuario;
	private String rangoUsuario;

	public VentanaAprendiz(String titulo, String rango, int id) {
		super(titulo);
		this.rangoUsuario = rango;
		this.idUsuario = id;
		configInicial();
		inicializarComponentes();
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

		// Panel horizontal
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

		// Titulo Página
		JLabel lblTitulo = new JLabel("Menú Aprendiz");
		lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblTitulo.setBounds(22, 63, 298, 40);
		getContentPane().add(lblTitulo);
		
		modelo.Modelo m = new modelo.Modelo();
		String nombreUsuario = m.obtenerNombreEmpleado(idUsuario);
		
		JLabel lblUsuarioLogueado = new JLabel(nombreUsuario + " (" + rangoUsuario + ")");
		lblUsuarioLogueado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuarioLogueado.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblUsuarioLogueado.setForeground(new Color(68, 68, 68));
		lblUsuarioLogueado.setBounds(622, 13, 310, 30);
		getContentPane().add(lblUsuarioLogueado);

		// Panel con informacion
		JPanel pnlBarraHorizontal_1 = new JPanel();
		pnlBarraHorizontal_1.setBorder(new LineBorder(new Color(68, 68, 68), 1, true));
		pnlBarraHorizontal_1.setLayout(null);
		pnlBarraHorizontal_1.setForeground(new Color(196, 204, 203));
		pnlBarraHorizontal_1.setBackground(new Color(165, 191, 201));
		pnlBarraHorizontal_1.setBounds(80, 25, 782, 236);
		pnlBarraHorizontal.add(pnlBarraHorizontal_1);

		// ScrollPane para la tabla
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 762, 216);
		pnlBarraHorizontal_1.add(scrollPane);

		// Tabla
		table = new JTable();
		scrollPane.setViewportView(table);
		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("DÍA");
		modeloTabla.addColumn("HORA");
		modeloTabla.addColumn("DURACIÓN");
		modeloTabla.addColumn("CLIENTE");
		modeloTabla.addColumn("TALLER");
		modeloTabla.addColumn("RESPONSABLE");
		modeloTabla.addColumn("TRAJE");

		table = new JTable(modeloTabla);
		scrollPane.setViewportView(table);

		// FONDO
		JLabel lblFondo = new JLabel("");
		lblFondo.setBounds(0, 0, 944, 501);
		getContentPane().add(lblFondo);
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpeg"));

	}
	
	public void setControlador(ControladorMenuAprendiz c) {
	    btnCerrarSesion.addActionListener(c);
	}
	
	public void cargarDatosCitas(ArrayList<Cita> citas) {
	    modeloTabla.setRowCount(0);
	    for (modelo.Cita c : citas) {
	        Object[] fila = {c.getDia(), c.getHora(), c.getDuracion(), c.getNombreCliente(), 
	                         c.getNombreTaller(), c.getNombreEmpleadoResponsable(), c.getNombreTraje()};
	        modeloTabla.addRow(fila);
	    }
	}

	public JButton getBtnCerrarSesion() { return btnCerrarSesion; }
	
}
