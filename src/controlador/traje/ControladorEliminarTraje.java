package controlador.traje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modelo.Modelo;
import vista.VentanaGestionTrajes;

public class ControladorEliminarTraje implements ActionListener {

	private VentanaGestionTrajes vista;

	public ControladorEliminarTraje(VentanaGestionTrajes vista) {
		this.vista = vista;
	}

	public void actionPerformed(ActionEvent e) {
		// 1. Obtenemos el ID del taller seleccionado en la vista
		int idTraje = vista.getIdTrajeSeleccionado();

		// Si es -1, significa que no hay ninguna fila seleccionada
		if (idTraje == -1) {
			JOptionPane.showMessageDialog(vista, "Por favor, selecciona un traje de la tabla para eliminarlo.",
					"Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// 2. Pedimos confirmación al usuario antes de borrar
		int confirmacion = JOptionPane.showConfirmDialog(vista,
				"¿Estás seguro de que deseas eliminar el traje con ID " + idTraje + "?", "Confirmar eliminación",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (confirmacion == JOptionPane.YES_OPTION) {
			Modelo modelo = new Modelo();
			boolean exito = modelo.eliminarTaller(idTraje);

			if (exito) {
				JOptionPane.showMessageDialog(vista, "Traje eliminado correctamente.");
				vista.cargarDatosTrajes(modelo.recuperarTrajes());
			} else {
				JOptionPane.showMessageDialog(vista, "Error: No se pudo eliminar el traje.");
			}
		}
	}
}