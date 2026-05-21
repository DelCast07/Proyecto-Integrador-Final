package controlador.cita;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modelo.Modelo;
import vista.VentanaGestionTalleres;
//Controlador encargado de manejar la acción de eliminar
public class ControladorEliminarCita implements ActionListener {
	// Variable para guardar la referencia a la ventana de gestión de talleres
	private VentanaGestionTalleres vista;
	// Constructor que vincula este controlador con la ventana al instanciarlo
	public ControladorEliminarCita(VentanaGestionTalleres vista) {
		this.vista = vista;
	}
	// Método que se ejecuta automáticamente cuando se pulsa el botón de eliminar
	public void actionPerformed(ActionEvent e) {
		// 1. Obtenemos el ID del taller seleccionado en la vista
		int idTaller = vista.getIdTallerSeleccionado();

		// Si es -1, significa que no hay ninguna fila seleccionada
		if (idTaller == -1) {
			// Mostramos un mensaje de advertencia pidiendo que seleccione uno
			JOptionPane.showMessageDialog(vista, "Por favor, selecciona un taller de la tabla para eliminarlo.",
					"Aviso", JOptionPane.WARNING_MESSAGE);
			return;// Detenemos la ejecución del código aquí para que no intente borrar nada
		}

		// 2. Pedimos confirmación al usuario antes de borrar
		int confirmacion = JOptionPane.showConfirmDialog(vista,
				"¿Estás seguro de que deseas eliminar el taller con ID " + idTaller + "?", "Confirmar eliminación",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		// Comprobamos si el usuario hizo clic en el botón "Sí"
		if (confirmacion == JOptionPane.YES_OPTION) {
			// Creamos una instancia del modelo para conectarnos a la base de datos
			Modelo modelo = new Modelo();
			// Intentamos eliminar el taller de la BBDD y guardamos el resultado (true si sale bien, false si falla)
			boolean exito = modelo.eliminarTaller(idTaller);

			// Evaluamos el resultado de la operación
			if (exito) {
				// Si se eliminó correctamente, avisamos al usuario
				JOptionPane.showMessageDialog(vista, "Taller eliminado correctamente.");
				// Y muy importante: recargamos la tabla de talleres para que el registro borrado desaparezca visualmente
				vista.cargarDatosTalleres(modelo.recuperarTalleres());
			} else {
				// Si hubo un error (por ejemplo, restricciones en la BBDD porque el taller está en uso), avisamos
				JOptionPane.showMessageDialog(vista, "Error: No se pudo eliminar el taller.");
			}
		}
	}
}