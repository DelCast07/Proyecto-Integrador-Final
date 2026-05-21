package controlador.cita;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import controlador.ControladorLogin;
import controlador.ControladorMenuMaestro;
import modelo.Modelo;
import vista.VentanaCrearCita;
import vista.VentanaGestionCita;
import vista.VentanaLogin;
import vista.VentanaMaestro;
import vista.VentanaModificarCitas;

//Controlador principal de la ventana donde se listan y gestionan las citas
public class ControladorMenuCita implements ActionListener {

	// Variables para la vista actual, el modelo (BBDD) y los datos del usuario logueado
	private VentanaGestionCita vGestionCita;
	private Modelo m;
	private String rangoUsuario;
	private int idUsuario;

	// Constructor: se ejecuta al abrir la ventana de gestión de citas
	public ControladorMenuCita(VentanaGestionCita v, String rango, int id) {
		vGestionCita = v;
		m = new Modelo();
		rangoUsuario = rango;
		idUsuario = id;
	}

	public void actionPerformed(ActionEvent e) {

		// ELIMINAR
		if (e.getSource().equals(vGestionCita.getBtnEliminarCita())) {
			// Obtenemos qué fila de la tabla ha seleccionado el usuario
		    int filaSeleccionada = vGestionCita.getTable().getSelectedRow();
		    if (filaSeleccionada == -1) {
		    	// Si es -1, no ha seleccionado nada
		        JOptionPane.showMessageDialog(vGestionCita, "Selecciona una cita");
		        return;
		    }
		    // Si el usuario es un "Oficial", solo puede borrar sus propias citas
		    if (rangoUsuario.equals("Oficial")) {
		    	// Sacamos el nombre del responsable de esa cita (columna 6 de la tabla)
		        String responsableCita = (String) vGestionCita.getModeloTabla().getValueAt(filaSeleccionada, 6);
		        // Obtenemos el nombre del oficial que está usando el programa
		        String nombreEmpleado = m.obtenerNombreEmpleado(idUsuario);
		        // Si los nombres no coinciden, le denegamos el permiso
		        if (!responsableCita.equalsIgnoreCase(nombreEmpleado)) {
		            JOptionPane.showMessageDialog(vGestionCita, "Solo puedes eliminar tus propias citas.");
		            return;
		        }
		    }
		    // Si llegamos aquí, sí tiene permiso. Obtenemos el ID de la cita (columna 0)
		    int idCita = (int) vGestionCita.getModeloTabla().getValueAt(filaSeleccionada, 0); 
		    
		    // Pedimos confirmación para no borrar por accidente
		    int confirm = JOptionPane.showConfirmDialog(vGestionCita, "¿Eliminar cita con ID " + idCita + "?");
		    if (confirm == JOptionPane.YES_OPTION) {
		    	// Si acepta, la borramos de la BBDD y recargamos la tabla para que desaparezca visualmente
		        if (m.eliminarCita(idCita)) {
		            vGestionCita.cargarDatosCitas(m.recuperarCitas());
		        }
		    }
		// CREAR
		} else if (e.getSource().equals(vGestionCita.getBtnCrearCita())) {
			// Abre la ventana para rellenar los datos de una nueva cita y cierra la actual
			VentanaCrearCita vCrearCita = new VentanaCrearCita(rangoUsuario, idUsuario);
			ControladorCrearCita cCrearCita = new ControladorCrearCita(vCrearCita, rangoUsuario, idUsuario);
			vCrearCita.setControlador(cCrearCita);
			vCrearCita.setVisible(true);
			vGestionCita.dispose();
		// ATRAS
		} else if (e.getSource().equals(vGestionCita.getBtnAtras())) {
			// Verificamos si es Maestro para devolverlo a su menú principal
		    if (rangoUsuario.equalsIgnoreCase("Maestro")) {
		        VentanaMaestro vMaestro = new VentanaMaestro("Menú Maestro", rangoUsuario, idUsuario);
		        ControladorMenuMaestro cMaestro = new ControladorMenuMaestro(vMaestro, rangoUsuario, idUsuario);
		        vMaestro.setControlador(cMaestro);
		        vMaestro.setVisible(true); 
		    } else {
		    	// Si es aprendiz u oficial, como no tienen menú intermedio, los devolvemos al login
		        VentanaLogin vLogin = new VentanaLogin("Inicio de Sesión");
		        ControladorLogin c = new ControladorLogin(vLogin);
		        vLogin.setControlador(c);
		        vLogin.setVisible(true);
		    }
		    vGestionCita.dispose();
		// MODIFICAR
		} else if (e.getSource().equals(vGestionCita.getBtnModificarCita())) {
			// Obtenemos la fila seleccionada
			int filaSeleccionada = vGestionCita.getTable().getSelectedRow();
			
			if (filaSeleccionada == -1) {
				JOptionPane.showMessageDialog(vGestionCita, "Seleccione una cita para modificar");
			} else {
				// Un Oficial solo puede modificar sus propias citas
				if (rangoUsuario.equals("Oficial")) {
			        String responsableCita = (String) vGestionCita.getModeloTabla().getValueAt(filaSeleccionada, 6);
			        String nombreUsuarioActual = m.obtenerNombreEmpleado(idUsuario);

			        if (!responsableCita.equalsIgnoreCase(nombreUsuarioActual)) {
			            JOptionPane.showMessageDialog(vGestionCita, "Acceso denegado: Solo puedes modificar tus propias citas.");
			            return;
			        }
			    }
				try {
					// Extraemos absolutamente todos los datos de la fila seleccionada en la tabla					
					int idCita = (int) vGestionCita.getModeloTabla().getValueAt(filaSeleccionada, 0);
			        java.util.Date fecha = (java.util.Date) vGestionCita.getModeloTabla().getValueAt(filaSeleccionada, 1);
			        java.sql.Time hora = (java.sql.Time) vGestionCita.getModeloTabla().getValueAt(filaSeleccionada, 2);
			        int duracion = (int) vGestionCita.getModeloTabla().getValueAt(filaSeleccionada, 3);
			        String cliente = (String) vGestionCita.getModeloTabla().getValueAt(filaSeleccionada, 4);
			        String taller = (String) vGestionCita.getModeloTabla().getValueAt(filaSeleccionada, 5);
			        String empleado = (String) vGestionCita.getModeloTabla().getValueAt(filaSeleccionada, 6);
			        String traje = (String) vGestionCita.getModeloTabla().getValueAt(filaSeleccionada, 7);
			        
			        // Preparamos la ventana de modificación
			        VentanaModificarCitas vModificarCitas = new VentanaModificarCitas(rangoUsuario, idUsuario);
			        ControladorModificarCita cModificarCita = new ControladorModificarCita(vModificarCitas, rangoUsuario, idUsuario);
			        vModificarCitas.setControlador(cModificarCita);
			        // Le pasamos a la nueva ventana todos los datos que sacamos de la tabla para que los precargue en los campos de texto
			        vModificarCitas.cargarDatosEnFormulario(idCita, fecha, hora, duracion, cliente, taller, empleado, traje);
			        vModificarCitas.setVisible(true);
			        vGestionCita.dispose();
				} catch (Exception ex) {
					// Por si ocurre algún error de conversión de datos (ej. un texto donde va un número)
					JOptionPane.showMessageDialog(vGestionCita, "Error en la modificacion del la cita.");
					ex.printStackTrace();
				}    
			}
		// CERRAR SESION
		} else if (e.getSource().equals(vGestionCita.getBtnCerrarSesion())) {
			// Nos lleva a la pantalla de login y cierra esta
			VentanaLogin vLogin = new VentanaLogin("Inicio de Sesión");
			vLogin.setControlador(new ControladorLogin(vLogin));
			vLogin.setVisible(true);
			vGestionCita.dispose();
		// VER CITAS PROPIAS	
		} else if (e.getSource().equals(vGestionCita.getBtnVerCitasPropias())) {
			// Filtra la tabla mostrando solo las citas asociadas al ID de este usuario
			vGestionCita.cargarDatosCitas(m.recuperarCitasPropias(idUsuario));
			JOptionPane.showMessageDialog(vGestionCita, "Cargando citas de " + vGestionCita.getNombreUsuario());
		// VER TODAS
		} else if (e.getSource().equals(vGestionCita.getBtnVerTodas())) {
			// Quita los filtros y vuelve a mostrar la lista completa de citas
			vGestionCita.cargarDatosCitas(m.recuperarCitas());
			JOptionPane.showMessageDialog(vGestionCita, "Cargando todas las citas.");
		}
	}

}