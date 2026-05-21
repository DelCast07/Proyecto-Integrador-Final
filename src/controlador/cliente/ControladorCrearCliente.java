package controlador.cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import controlador.cita.ControladorCrearCita;
import controlador.traje.ControladorCrearTraje; // Importamos el de trajes
import modelo.Modelo;
import modelo.Cliente;
import vista.VentanaCrearCita;
import vista.VentanaCrearCliente;
import vista.VentanaGestionCliente;
import vista.VentanaCrearTraje; // Importamos la ventana de trajes

//Controlador encargado de gestionar la pantalla donde se registran nuevos clientes
public class ControladorCrearCliente implements ActionListener {

	// Variables para la ventana actual y los datos del usuario logueado
	private VentanaCrearCliente vista;
	private int idUsuario;
	private String rangoUsuario;

	// Constructor: se ejecuta al abrir la ventana de crear cliente
	public ControladorCrearCliente(VentanaCrearCliente vista, int id) {
		this.vista = vista;
		this.idUsuario = id;
		this.rangoUsuario = vista.getRangoUsuario();

		// Conectamos con la base de datos para rellenar la lista desplegable de trajes
		Modelo m = new Modelo();
		vista.rellenarComboBoxTrajes(m.recuperarNombresTrajes());
	}

	// Método que detecta cualquier interacción del usuario (botones o selecciones)
		@Override
		public void actionPerformed(ActionEvent e) {
			Modelo m = new Modelo();

			// --- 1. SI EL USUARIO INTERACTÚA CON EL DESPLEGABLE DE TRAJES ---
			if (e.getSource().equals(vista.getNombreTraje())) {
				// Obtenemos qué opción ha seleccionado en el desplegable
				String seleccionado = vista.getNombreTraje().getSelectedItem().toString();

				// Si elige la opción especial para crear un traje nuevo en ese mismo instante
				if (seleccionado.equals("- Crear nuevo traje -")) {

					// Guardamos los datos uqe esten introducidos en los campos de texto
					String nombreCliente = vista.getNombreCliente();
					String superpoder = vista.getSuperpoderCliente();
					String colores = vista.getColorCliente();

					// Comprobamos que dichos datos no esten vacios (no podemos crear un traje a un cliente "fantasma")
					if (nombreCliente.isEmpty() || superpoder.isEmpty() || colores.isEmpty()) {
						JOptionPane.showMessageDialog(vista,
								"Debes rellenar todos los datos del cliente antes de crearle un traje.");
						vista.getNombreTraje().setSelectedIndex(0); // Devolvemos el desplegable a su posición inicial
						return; // Detenemos la ejecución
					}

					// Guardamos el cliente en un objeto
					Cliente c = new Cliente();
					c.setNombre(nombreCliente);
					c.setSuperpoder(superpoder);
					c.setColores(colores);

					// Intentamos crear el cliente en la base de datos
				if (m.crearCliente(c)) {
					// Si se crea bien, abrimos automáticamente la ventana para crear el traje
				    VentanaCrearTraje vCrearTraje = new VentanaCrearTraje(rangoUsuario, "Crear Cliente", idUsuario);
				    ControladorCrearTraje cCrearTraje = new ControladorCrearTraje(vCrearTraje, rangoUsuario, idUsuario);
				    vCrearTraje.setControladorGuardar(cCrearTraje); 
				    
				 // Pre-seleccionamos al cliente que acabamos de crear para ahorrarle trabajo al usuario
				    vCrearTraje.seleccionarCliente(nombreCliente); 
				    
				    vCrearTraje.setVisible(true);
				    vista.dispose();
				} else {
					JOptionPane.showMessageDialog(vista, "Error al guardar el cliente en la base de datos.");
				}
			}
		// --- 2. SI EL USUARIO PULSA EL BOTÓN ATRÁS ---
		} else if (e.getSource().equals(vista.getBtnAtras())) {
			volver(m); // Llamamos al método auxiliar que decide a qué pantalla regresar
			
		// --- 3. SI EL USUARIO PULSA GUARDAR CAMBIOS (Flujo normal) ---
		} else if (e.getSource().equals(vista.getBtnGuardarCambios())) {
			String nombre = vista.getNombreCliente();

			// Preparamos el objeto cliente con los datos introducidos
			Cliente c = new Cliente();
			c.setNombre(nombre);
			c.setSuperpoder(vista.getSuperpoderCliente());
			c.setColores(vista.getColorCliente());

			// Intentamos insertarlo en la base de datos
			if (m.crearCliente(c)) {
				// Preguntamos al usuario si quiere aprovechar para crearle un traje de una vez (Acción Rápida)
				int respuesta = JOptionPane.showConfirmDialog(vista,
						"Cliente creado. ¿Deseas crear un traje para " + nombre + " ahora?", "Acción Rápida",
						JOptionPane.YES_NO_OPTION);

				if (respuesta == JOptionPane.YES_OPTION) {
					// Si dice que sí, abrimos la ventana de trajes y cerramos esta
					VentanaCrearTraje vCrearTraje = new VentanaCrearTraje(rangoUsuario, "Crear Cliente", idUsuario);
					ControladorCrearTraje cCrearTraje = new ControladorCrearTraje(vCrearTraje, rangoUsuario, idUsuario);
					vCrearTraje.setControladorGuardar(cCrearTraje);
					vCrearTraje.seleccionarCliente(nombre);
					vCrearTraje.setVisible(true);
					vista.dispose();
				} else {
					// Si dice que no, vuelve a la pantalla anterior
					volver(m);
				}
			} else {
				JOptionPane.showMessageDialog(vista, "Error al crear cliente");
			}
		}
	}

	// Método auxiliar para gestionar el regreso a la pantalla correcta
	private void volver(Modelo m) {
		// Comprobamos desde qué ventana vino el usuario antes de llegar aquí
		String origen = vista.getVentanaOrigen();
		
		// Si el usuario estaba creando una cita y se dio cuenta de que faltaba el cliente...
		if (origen != null && origen.equals("Crear Cita")) {
			// ...lo devolvemos a la ventana de Crear Cita
			VentanaCrearCita vCrearCita = new VentanaCrearCita(rangoUsuario, idUsuario);
			ControladorCrearCita cCrearCita = new ControladorCrearCita(vCrearCita, rangoUsuario, idUsuario);
			vCrearCita.setControlador(cCrearCita);

			// Refrescamos los combos de la cita para que salga el nuevo cliente
			vCrearCita.rellenarComboBox(m.recuperarNombresClientes(), m.recuperarNombresTalleres(),
					m.recuperarNombresEmpleados(), m.recuperarNombresTrajes());

			vCrearCita.setVisible(true);
			vista.dispose();
		} else {
			// Si vino desde el menú normal de Clientes, lo devolvemos allí y recargamos la tabla
			VentanaGestionCliente vG = new VentanaGestionCliente(rangoUsuario, idUsuario);
			ControladorMenuCliente cG = new ControladorMenuCliente(vG, rangoUsuario, idUsuario);
			vG.setControlador(cG);
			vG.cargarDatosClientes(m.recuperarClientes());
			vG.setVisible(true);
			vista.dispose();
		}
	}
}