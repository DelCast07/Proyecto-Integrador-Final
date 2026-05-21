package controlador.cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import controlador.ControladorLogin;
import controlador.ControladorMenuMaestro; 
import modelo.Modelo;
import vista.VentanaCrearCliente;
import vista.VentanaGestionCliente;
import vista.VentanaLogin;
import vista.VentanaMaestro;
import vista.VentanaModificarCliente;

//Controlador principal de la ventana donde se listan y gestionan los clientes
public class ControladorMenuCliente implements ActionListener {

	// Variables para la vista actual, el modelo (BBDD) y los datos del usuario logueado
	private VentanaGestionCliente vGestionClientes;
	private Modelo m;
	private String rangoUsuario;
	private int idUsuario;

	// Constructor: vincula la ventana actual y guarda los datos del usuario al abrir la pantalla
	public ControladorMenuCliente(VentanaGestionCliente v, String rango, int id) {
		vGestionClientes = v;
		m = new Modelo();
		this.rangoUsuario = rango;
		this.idUsuario = id;
	}

	// Método que "escucha" y ejecuta acciones dependiendo del botón que pulse el usuario
		@Override
		public void actionPerformed(ActionEvent e) {

			// ------------------ BOTÓN ELIMINAR ------------------
			if (e.getSource().equals(vGestionClientes.getBtnEliminar())) {
				// Obtenemos el ID de la fila seleccionada en la tabla
				int idCliente = vGestionClientes.getIdClienteSeleccionado();
				
				// Si es -1, el usuario no hizo clic en ningún cliente de la tabla
				if (idCliente == -1) {
					JOptionPane.showMessageDialog(vGestionClientes, "Selecciona el cliente que desea eliminar");
				} else {
					// Pedimos confirmación para evitar borrar un cliente por accidente
					int confirmacion = JOptionPane.showConfirmDialog(vGestionClientes,
							"¿Seguro que deseas eliminar el cliente con ID " + idCliente + "?", "Confirmar",
							JOptionPane.YES_NO_OPTION);
					
					// Si dice que sí, procedemos a borrar en la base de datos
					if (confirmacion == JOptionPane.YES_OPTION) {
						if (m.eliminarCliente(idCliente)) {
							JOptionPane.showMessageDialog(vGestionClientes, "Cliente eliminado");
							// Recargamos la tabla para que el cliente borrado desaparezca visualmente
							vGestionClientes.cargarDatosClientes(m.recuperarClientes());
						} else {
							JOptionPane.showMessageDialog(vGestionClientes, "Error al eliminar el cliente");
						}
					}
				}
			}
		// ------------------ BOTÓN CREAR ------------------
		else if (e.getSource().equals(vGestionClientes.getBtnCrear())) {
			// Abre la ventana para registrar un nuevo cliente
			VentanaCrearCliente vCrear = new VentanaCrearCliente(vGestionClientes.getRangoUsuario(), idUsuario, "Gestion");
			ControladorCrearCliente cCrear = new ControladorCrearCliente(vCrear, idUsuario);
			vCrear.setControlador(cCrear); 
			
			// Muestra la ventana nueva y cierra la actual
			vCrear.setVisible(true);
			vGestionClientes.dispose();
			
		} 
			// ------------------ BOTÓN ATRÁS ------------------
		else if (e.getSource().equals(vGestionClientes.getBtnAtras())) {
			// Devuelve al usuario (que en este caso será un Maestro) a su menú principal
			VentanaMaestro vMaestro = new VentanaMaestro("Menú Principal", rangoUsuario, idUsuario);
			ControladorMenuMaestro cMaestro = new ControladorMenuMaestro(vMaestro, rangoUsuario, idUsuario);
			vMaestro.setControlador(cMaestro);
			vMaestro.setVisible(true);
			vGestionClientes.dispose();
            
		} 
		// ------------------ BOTÓN MODIFICAR ------------------
		else if (e.getSource().equals(vGestionClientes.getBtnModificar())) {
			// Primero verificamos que haya seleccionado a alguien
			int idCliente = vGestionClientes.getIdClienteSeleccionado();
			if (idCliente == -1) {
				JOptionPane.showMessageDialog(vGestionClientes, "Selecciona un cliente para modificar");
			} else {
				// Rescatamos todos los datos del cliente desde la tabla seleccionada
				String nombre = vGestionClientes.getNombreClienteSeleccionado();
				String superpoder = vGestionClientes.getSuperpoderClienteSeleccionado();
				String colores = vGestionClientes.getColoresClienteSeleccionado();
				
				// Preparamos la ventana de modificación y le pasamos los datos que acabamos de rescatar
				// para que los campos de texto aparezcan ya rellenos
				VentanaModificarCliente vModificarCliente = new VentanaModificarCliente(vGestionClientes.getRangoUsuario(), idCliente, nombre, superpoder, colores);
				ControladorModificarCliente cModificarCliente = new ControladorModificarCliente(vModificarCliente, idUsuario);
				vModificarCliente.setControladorModificar(cModificarCliente);
				// Mostramos la ventana de modificación y cerramos la lista
				vModificarCliente.setVisible(true);
				vGestionClientes.dispose();
			}
            
		} 
			// ------------------ BOTÓN CERRAR SESIÓN ------------------
					else if (e.getSource().equals(vGestionClientes.getBtnCerrarSesion())) {
						// Cierra la ventana actual y nos devuelve a la pantalla de Login
						VentanaLogin vLogin = new VentanaLogin("Inicio de Sesión");
						ControladorLogin c = new ControladorLogin(vLogin);
						vLogin.setControlador(c);
						
						vLogin.setVisible(true);
						vGestionClientes.dispose();
					}
				}
			}