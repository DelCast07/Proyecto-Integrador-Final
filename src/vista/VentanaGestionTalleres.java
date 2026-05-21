package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import controlador.taller.ControladorMenuTaller;
import modelo.Taller;

public class VentanaGestionTalleres extends JFrame {

	private String rangoUsuario;
	private int idUsuario;
	private JButton btnEliminar;
	private JButton btnCrear;
	private JButton btnModificar;
	private JButton btnAtras;
	private JButton btnCerrarSesion;
	private DefaultTableModel modeloTabla;
	private JTable table;

	public VentanaGestionTalleres(String rango, int id) {
		this.rangoUsuario = rango;
		this.idUsuario = id;
		configInicial();
		inicializarComponentes();
	}

	private void configInicial() {
		setTitle("Gestión de Talleres");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setSize(960, 540);
		setLocationRelativeTo(null);
	}

	private void inicializarComponentes() {
		ControladorMenuTaller controlador = new ControladorMenuTaller(this, rangoUsuario, idUsuario);

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

		// Panel horizontal central
		JPanel pnlBarraHorizontal = new JPanel();
		pnlBarraHorizontal.setBackground(new Color(196, 204, 203));
		pnlBarraHorizontal.setBounds(0, 111, 944, 282);
		pnlBarraHorizontal.setLayout(null);
		getContentPane().add(pnlBarraHorizontal);

		// Titulo Pagina
		JLabel lblTitulo = new JLabel("Gestión de Talleres");
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

		// Botones de acción
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

		btnCerrarSesion = new JButton("Cerrar sesión");
		btnCerrarSesion.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnCerrarSesion.setBackground(new Color(165, 191, 201));
		btnCerrarSesion.setBounds(787, 68, 135, 30);
		getContentPane().add(btnCerrarSesion);

		btnAtras = new JButton("");
		ImageIcon iconoAtras = new ImageIcon("img\\flecha_izq.png");
		Image imgAtras = iconoAtras.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnAtras.setIcon(new ImageIcon(imgAtras));
		btnAtras.setBackground(new Color(165, 191, 201));
		btnAtras.setBounds(22, 11, 30, 30);
		getContentPane().add(btnAtras);

		// Tabla
		JPanel pnlTablaContainer = new JPanel();
		pnlTablaContainer.setBorder(new LineBorder(new Color(68, 68, 68), 1, true));
		pnlTablaContainer.setBackground(new Color(165, 191, 201));
		pnlTablaContainer.setBounds(150, 25, 782, 236);
		pnlTablaContainer.setLayout(null);
		pnlBarraHorizontal.add(pnlTablaContainer);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 762, 216);
		pnlTablaContainer.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("ID");     
		modeloTabla.addColumn("NOMBRE"); 
		modeloTabla.addColumn("TIPO");   

		table = new JTable(modeloTabla);
		scrollPane.setViewportView(table);
		
		// Hacemos esto para poder utilizar el id pero tenerlo oculto de la tabla,
		// hacemos que la anchura sea de 0 y asi se "oculta" 
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);

		// Fondo
		JLabel lblFondo = new JLabel("");
		lblFondo.setBounds(0, 0, 944, 501);
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpeg"));
		getContentPane().add(lblFondo);
	}

	public void setControlador(ControladorMenuTaller c) {
		btnCrear.addActionListener(c);
		btnEliminar.addActionListener(c);
		btnModificar.addActionListener(c);
		btnCerrarSesion.addActionListener(c);
		btnAtras.addActionListener(c);
	}

	public void cargarDatosTalleres(ArrayList<Taller> datosTaller) {
		modeloTabla.setRowCount(0);
		for (Taller t : datosTaller) {
			modeloTabla.addRow(new Object[] { 
				t.getId_taller(), 
				t.getNombre(), 
				t.getTipo_sala() 
			});
		}
	}

	public int getIdTallerSeleccionado() {
	    int filaSeleccionada = table.getSelectedRow();
	    if (filaSeleccionada != -1) {
	        return (int) modeloTabla.getValueAt(filaSeleccionada, 0);
	    } else {
	        return -1;
	    }
	}

	public String getNombreTallerSeleccionado() {
	    int fila = table.getSelectedRow();
	    if (fila != -1) {
	        return modeloTabla.getValueAt(fila, 1).toString();
	    } else {
	        return null;
	    }
	}

	public String getTipoTallerSeleccionado() {
	    int fila = table.getSelectedRow();
	    if (fila != -1) {
	        return modeloTabla.getValueAt(fila, 2).toString();
	    } else {
	        return null;
	    }
	}

	public JButton getBtnEliminar() { 
		return btnEliminar; 
	}
	public JButton getBtnCrear() { 
		return btnCrear; 
	}
	public JButton getBtnModificar() { 
		return btnModificar; 
	}
	public JButton getBtnAtras() { 
		return btnAtras; 
	}
	public JButton getBtnCerrarSesion() { 
		return btnCerrarSesion; 
	}
	public String getRangoUsuario() { 
		return rangoUsuario; 
	}
}