package vista;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.LineBorder;
import controlador.cita.ControladorModificarCita;

public class VentanaModificarCitas extends JFrame {

	private String rangoUsuario;
	private int idUsuario;
	private JButton btnGuardarCambios;
	private JButton btnCerrarSesion;
	private JButton btnAtras;
	private JTextField txtIdCita;
	private JComboBox cmbNombreCliente;
	private JComboBox cmbNombreTaller;
	private JComboBox cmbNumAprendices;
	private JComboBox cmbNombreTraje;
	private JComboBox cmbNombreResponsable;
	private JSpinner spinnerFecha;
	private JSpinner spinnerHora;
	private JSpinner spinnerDuracion;
	private JButton btnAsignarAprendices;

	public VentanaModificarCitas(String rango, int id) {
	    this.rangoUsuario = rango;
	    this.idUsuario = id;
	    inicializarComponentes();
	    configInicial();
	    txtIdCita.setEditable(false);
	}

	private void configInicial() {
		setTitle("Gestión de Citas");
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
		JLabel lblTitulo = new JLabel("Modificar Cita");
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

		// COMPONENTES DEL FORMULARIO
		txtIdCita = new JTextField();

		btnAsignarAprendices = new JButton("Asignar Aprendices");
		btnAsignarAprendices.setFont(new Font("Verdana", Font.BOLD, 12));
		btnAsignarAprendices.setBackground(new Color(165, 191, 201));
		btnAsignarAprendices.setBounds(484, 146, 216, 30); // Ocupa el espacio visual del ID
		pnlFormulario.add(btnAsignarAprendices);

		JLabel lblNombreCliente = new JLabel("Nombre del Cliente:");
		lblNombreCliente.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNombreCliente.setBounds(49, 23, 204, 30);
		pnlFormulario.add(lblNombreCliente);
		cmbNombreCliente = new JComboBox();
		cmbNombreCliente.setFont(new Font("Verdana", Font.PLAIN, 14));
		cmbNombreCliente.setBounds(263, 23, 150, 30);
		pnlFormulario.add(cmbNombreCliente);

		JLabel lblNombreTraje = new JLabel("Nombre del Traje:");
		lblNombreTraje.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNombreTraje.setBounds(49, 146, 204, 30);
		pnlFormulario.add(lblNombreTraje);
		cmbNombreTraje = new JComboBox();
		cmbNombreTraje.setFont(new Font("Verdana", Font.PLAIN, 14));
		cmbNombreTraje.setBounds(263, 146, 150, 30);
		pnlFormulario.add(cmbNombreTraje);

		JLabel lblNombreResponsable = new JLabel("Nombre del Responsable:");
		lblNombreResponsable.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNombreResponsable.setBounds(49, 105, 235, 30);
		pnlFormulario.add(lblNombreResponsable);
		cmbNombreResponsable = new JComboBox();
		cmbNombreResponsable.setFont(new Font("Verdana", Font.PLAIN, 14));
		cmbNombreResponsable.setBounds(263, 105, 150, 30);
		pnlFormulario.add(cmbNombreResponsable);

		JLabel lblNombreTaller = new JLabel("Nombre del Taller:");
		lblNombreTaller.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNombreTaller.setBounds(49, 64, 204, 30);
		pnlFormulario.add(lblNombreTaller);
		cmbNombreTaller = new JComboBox();
		cmbNombreTaller.setFont(new Font("Verdana", Font.PLAIN, 14));
		cmbNombreTaller.setBounds(263, 64, 150, 30);
		pnlFormulario.add(cmbNombreTaller);

		JLabel lblFecha = new JLabel("Día:");
		lblFecha.setFont(new Font("Verdana", Font.BOLD, 14));
		lblFecha.setBounds(484, 23, 37, 30);
		pnlFormulario.add(lblFecha);
		spinnerFecha = new JSpinner();
		spinnerFecha.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy");
		spinnerFecha.setEditor(dateEditor);
		spinnerFecha.setBounds(602, 27, 98, 23);
		pnlFormulario.add(spinnerFecha);

		
		JLabel lblHora = new JLabel("Hora:");
		lblHora.setFont(new Font("Verdana", Font.BOLD, 14));
		lblHora.setBounds(484, 64, 50, 30);
		pnlFormulario.add(lblHora);
		spinnerHora = new JSpinner();
		// Usamos el Objeto Calendar de Java.
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 9);
		cal.set(Calendar.MINUTE, 0);
		spinnerHora.setModel(new SpinnerDateModel(cal.getTime(), null, null, Calendar.MINUTE));
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinnerHora, "HH:mm");
		spinnerHora.setEditor(timeEditor);
		spinnerHora.setBounds(602, 68, 98, 23);
		pnlFormulario.add(spinnerHora);

		JLabel lblDuracion = new JLabel("Duración (h):");
		lblDuracion.setFont(new Font("Verdana", Font.BOLD, 14));
		lblDuracion.setBounds(484, 105, 188, 30);
		pnlFormulario.add(lblDuracion);
		spinnerDuracion = new JSpinner();
		spinnerDuracion.setModel(new SpinnerNumberModel(Integer.valueOf(1), null, null, Integer.valueOf(1)));
		spinnerDuracion.setFont(new Font("Verdana", Font.PLAIN, 12));
		spinnerDuracion.setBounds(602, 109, 98, 23);
		pnlFormulario.add(spinnerDuracion);

		// FONDO
		JLabel lblFondo = new JLabel("");
		lblFondo.setBounds(0, 0, 944, 501);
		getContentPane().add(lblFondo);
		lblFondo.setIcon(new ImageIcon("img\\fondo.jpeg"));

	}

	// METODOS

	/**
	 * Método para añadir los controladores a los listener de los botones
	 * @param c
	 */
	public void setControlador(ControladorModificarCita c) {
		btnGuardarCambios.addActionListener(c);
		btnAtras.addActionListener(c);
		btnCerrarSesion.addActionListener(c);
		btnAsignarAprendices.addActionListener(c);
	}
	
	/**
	 * Metodo para cargar los datos que ya tenemos en la cita y que aparezcan en las celdas del formulario
	 * @param id
	 * @param fecha
	 * @param hora
	 * @param duracion
	 * @param cliente
	 * @param taller
	 * @param responsable
	 * @param traje
	 */
	public void cargarDatosEnFormulario(int id, Date fecha, Date hora, int duracion, String cliente, String taller, String responsable, String traje) {
	    txtIdCita.setText(String.valueOf(id));
	    spinnerFecha.setValue(fecha);
	    spinnerHora.setValue(hora);
	    spinnerDuracion.setValue(duracion);
	    
	    cmbNombreCliente.setSelectedItem(cliente);
	    cmbNombreTaller.setSelectedItem(taller);
	    cmbNombreResponsable.setSelectedItem(responsable);
	    cmbNombreTraje.setSelectedItem(traje);
	}
	
	public void rellenarComboBox(ArrayList<String> listaClientes, ArrayList<String> listaTalleres, ArrayList<String> empleados, ArrayList<String> trajes) {
	    cmbNombreCliente.removeAllItems();
	    cmbNombreTraje.removeAllItems();
	    cmbNombreTaller.removeAllItems();
	    cmbNombreResponsable.removeAllItems();

	    for(String c : listaClientes) {
	    	cmbNombreCliente.addItem(c);
	    }
	    for(String tr : trajes) {
	    	cmbNombreTraje.addItem(tr);
	    }
	    for(String ta : listaTalleres) {
	    	cmbNombreTaller.addItem(ta);
	    }
	    for(String e : empleados) {
	    	cmbNombreResponsable.addItem(e);
	    }
	}
	
	// --- GETTERS PARA EL CONTROLADOR ---
	public JButton getBtnGuardar() {
		return btnGuardarCambios;
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

	/**
	 * @return the idUsuario
	 */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @return the btnGuardarCambios
	 */
	public JButton getBtnGuardarCambios() {
		return btnGuardarCambios;
	}

	/**
	 * @return the txtIdCita
	 */
	public JTextField getTxtIdCita() {
		return txtIdCita;
	}

	/**
	 * @return the cmbNombreCliente
	 */
	public JComboBox getCmbNombreCliente() {
		return cmbNombreCliente;
	}

	/**
	 * @return the cmbNombreTaller
	 */
	public JComboBox getCmbNombreTaller() {
		return cmbNombreTaller;
	}

	/**
	 * @return the cmbNumAprendices
	 */
	public JComboBox getCmbNumAprendices() {
		return cmbNumAprendices;
	}

	/**
	 * @return the cmbNombreTraje
	 */
	public JComboBox getCmbNombreTraje() {
		return cmbNombreTraje;
	}

	/**
	 * @return the cmbNombreResponsable
	 */
	public JComboBox getCmbNombreResponsable() {
		return cmbNombreResponsable;
	}

	/**
	 * @return the spinnerFecha
	 */
	public JSpinner getSpinnerFecha() {
		return spinnerFecha;
	}

	/**
	 * @return the spinnerHora
	 */
	public JSpinner getSpinnerHora() {
		return spinnerHora;
	}

	/**
	 * @return the spinnerDuracion
	 */
	public JSpinner getSpinnerDuracion() {
		return spinnerDuracion;
	}

	public JButton getBtnAsignarAprendices() {
		return btnAsignarAprendices;
	}

	

}