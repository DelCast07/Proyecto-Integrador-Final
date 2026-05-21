package controlador.taller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modelo.Modelo;
import vista.VentanaGestionTalleres;

// Controlador exclusivo para manejar la acción de eliminar un taller de la base de datos
public class ControladorEliminarTaller implements ActionListener {

	// Variable para guardar la ventana de gestión de talleres desde donde se ejecuta la acción
	private VentanaGestionTalleres vista;

	// Constructor: vincula este controlador con la ventana en el momento en que se crea
	public ControladorEliminarTaller(VentanaGestionTalleres vista) {
		this.vista = vista;
	}

	// Método que se activa automáticamente al hacer clic en el botón de eliminar
	public void actionPerformed(ActionEvent e) {
		// 1. Obtenemos el ID numérico del taller seleccionado en la tabla de la vista
		int idTaller = vista.getIdTallerSeleccionado();

		// Si el ID es -1, significa que el usuario no ha hecho clic en ninguna fila de la tabla
		if (idTaller == -1) {
			// Mostramos un mensaje de advertencia pidiendo que elija uno primero
			JOptionPane.showMessageDialog(vista, "Por favor, selecciona un taller de la tabla para eliminarlo.",
					"Aviso", JOptionPane.WARNING_MESSAGE);
			return; // Detenemos la ejecución del código aquí para no intentar borrar la nada
		}

		// 2. Pedimos confirmación al usuario antes de borrar para prevenir clics accidentales
		int confirmacion = JOptionPane.showConfirmDialog(vista,
				"¿Estás seguro de que deseas eliminar el taller con ID " + idTaller + "?", "Confirmar eliminación",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		// Verificamos si el usuario pulsó el botón "Sí" en la ventana de confirmación
		if (confirmacion == JOptionPane.YES_OPTION) {
			// Preparamos la conexión a la base de datos
			Modelo modelo = new Modelo();
			// Intentamos eliminar el taller usando su ID y guardamos el resultado (true/false)
			boolean exito = modelo.eliminarTaller(idTaller);

			// Evaluamos cómo salió la operación
			if (exito) {
				// Si se eliminó correctamente, avisamos al usuario
				JOptionPane.showMessageDialog(vista, "Taller eliminado correctamente.");
				// Refrescamos la tabla recargando los datos desde la BBDD para que el taller borrado desaparezca visualmente
				vista.cargarDatosTalleres(modelo.recuperarTalleres());
			} else {
				// Si hubo un error (por ejemplo, si el taller está siendo usado en alguna cita y la BBDD bloquea el borrado por seguridad), avisamos
				JOptionPane.showMessageDialog(vista, "Error: No se pudo eliminar el taller.");
			}
		}
	}
}