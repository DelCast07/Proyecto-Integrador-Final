package controlador.taller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import controlador.ControladorLogin;
import controlador.ControladorMenuMaestro;
import modelo.Modelo;
import vista.VentanaCrearTaller;
import vista.VentanaGestionTalleres;
import vista.VentanaLogin;
import vista.VentanaMaestro;
import vista.VentanaModificarTaller;

// Controlador principal de la ventana donde se listan y gestionan los talleres
public class ControladorMenuTaller implements ActionListener {

	// Variables para la vista actual, el modelo (BBDD) y los datos del usuario logueado
	private VentanaGestionTalleres vGestionTalleres;
	private Modelo m;
	private String rangoUsuario;
	private int idUsuario;

	// Constructor: vincula la ventana actual y guarda los datos del usuario al abrir la pantalla
	public ControladorMenuTaller(VentanaGestionTalleres v, String rango, int id) {
		vGestionTalleres = v;
		m = new Modelo();
		rangoUsuario = rango;
		idUsuario = id;
	}
	// Método que "escucha" y ejecuta acciones dependiendo del botón que pulse el usuario
	public void actionPerformed(ActionEvent e) {

		// ------------------ BOTÓN ELIMINAR ------------------
		if (e.getSource().equals(vGestionTalleres.getBtnEliminar())) {
			// Obtenemos el ID del taller seleccionado en la tabla
			int idTaller = vGestionTalleres.getIdTallerSeleccionado();
			
			// Si es -1, el usuario no hizo clic en ninguna fila
			if (idTaller == -1) {
				JOptionPane.showMessageDialog(vGestionTalleres, "Selecciona el taller que desea eliminar");
			} else {
				// Pedimos confirmación para evitar borrados accidentales
				int confirmacion = JOptionPane.showConfirmDialog(vGestionTalleres,
						"¿Seguro que deseas eliminar el taller con ID " + idTaller + "?", "Confirmar",
						JOptionPane.YES_NO_OPTION);
						
				// Si el usuario acepta, procedemos a borrar
				if (confirmacion == JOptionPane.YES_OPTION) {
					Modelo modelo = new Modelo();
					// Intentamos eliminar el taller de la base de datos
					if (m.eliminarTaller(idTaller)) {
						JOptionPane.showMessageDialog(vGestionTalleres, "Taller eliminado");
						// Recargamos la tabla para que el taller desaparezca visualmente al instante
						vGestionTalleres.cargarDatosTalleres(m.recuperarTalleres());
					} else {
						JOptionPane.showMessageDialog(vGestionTalleres, "Error al eliminar el taller");
					}
				}
			}
			
		// ------------------ BOTÓN CREAR ------------------
		} else if (e.getSource().equals(vGestionTalleres.getBtnCrear())) {
			// Preparamos y abrimos la ventana para registrar un nuevo taller
			VentanaCrearTaller vCrearTaller = new VentanaCrearTaller(vGestionTalleres.getRangoUsuario(), idUsuario);
			ControladorCrearTaller cCrearTaller = new ControladorCrearTaller(vCrearTaller, rangoUsuario, idUsuario);
			vCrearTaller.setControladorGuardar(cCrearTaller);
			
			// Mostramos la ventana de creación y cerramos la lista actual
			vCrearTaller.setVisible(true);
			vGestionTalleres.dispose();
			
		// ------------------ BOTÓN ATRÁS ------------------
		} else if (e.getSource().equals(vGestionTalleres.getBtnAtras())) {
			// Devuelve al usuario (Maestro) a su menú principal
			VentanaMaestro vMaestro = new VentanaMaestro("Gestion de citas", rangoUsuario, idUsuario);
			ControladorMenuMaestro cMaestro = new ControladorMenuMaestro(vMaestro, rangoUsuario, idUsuario);
			vMaestro.setControlador(cMaestro);
			
			// Mostramos el menú principal y cerramos esta ventana
			vMaestro.setVisible(true);
			vGestionTalleres.dispose();
			
		// ------------------ BOTÓN MODIFICAR ------------------
		} else if (e.getSource().equals(vGestionTalleres.getBtnModificar())) {
			// Verificamos qué taller ha seleccionado el usuario en la tabla
			int idTaller = vGestionTalleres.getIdTallerSeleccionado();
			if (idTaller == -1) {
				JOptionPane.showMessageDialog(vGestionTalleres, "Selecciona un taller para modificar");
			} else {
				// Rescatamos los datos de ese taller directamente desde la tabla visual
				String nombreTaller = vGestionTalleres.getNombreTallerSeleccionado();
				String tipoTaller = vGestionTalleres.getTipoTallerSeleccionado();
				
				// 3. Abrimos la ventana de Modificar pasándole el rango Y el ID del taller, 
				// además de los datos actuales para que los campos de texto aparezcan ya rellenos
				VentanaModificarTaller vModificarTaller = new VentanaModificarTaller(rangoUsuario, idTaller, nombreTaller, tipoTaller, idUsuario);
				ControladorModificarTaller cModificarTaller = new ControladorModificarTaller(vModificarTaller, rangoUsuario, idUsuario, idTaller);
				vModificarTaller.setControladorModificar(cModificarTaller);
				
				// Mostramos la ventana de modificación y cerramos la lista
				vModificarTaller.setVisible(true);
				vGestionTalleres.dispose();
			}
			
		// ------------------ BOTÓN CERRAR SESIÓN ------------------
		} else if (e.getSource().equals(vGestionTalleres.getBtnCerrarSesion())) {
			// Cierra la ventana actual y nos devuelve a la pantalla de inicio de sesión
			VentanaLogin vLogin = new VentanaLogin("Inicio de Sesión");
			ControladorLogin c = new ControladorLogin(vLogin);
			vLogin.setControlador(c);
			
			vLogin.setVisible(true);
			vGestionTalleres.dispose();
		}
	}

}