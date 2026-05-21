package controlador.cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import modelo.Modelo;
import vista.VentanaGestionCliente;

// Controlador exclusivo para manejar la acción de eliminar a un cliente de la base de datos
public class ControladorEliminarCliente implements ActionListener {

	// Variable para guardar la ventana desde la cual se está intentando eliminar al cliente
	private VentanaGestionCliente vista;

	// Constructor: vincula el controlador con la ventana en cuanto se crea
	public ControladorEliminarCliente(VentanaGestionCliente vista) {
		this.vista = vista;
	}

	// Método que se activa automáticamente al hacer clic en el botón de eliminar
	@Override
	public void actionPerformed(ActionEvent e) {
		// 1. Obtenemos el ID numérico del cliente seleccionado en la tabla de la vista
		int idCliente = vista.getIdClienteSeleccionado();

		// Si el resultado es -1, significa que no hay ninguna fila seleccionada en la tabla
		if (idCliente == -1) {
			// Mostramos un aviso pidiendo que elija uno primero
			JOptionPane.showMessageDialog(vista, "Por favor, selecciona un cliente de la tabla para eliminarlo.",
					"Aviso", JOptionPane.WARNING_MESSAGE);
			return; // Detenemos la ejecución aquí para evitar errores
		}

		// 2. Pedimos confirmación al usuario antes de borrar para prevenir clics accidentales
		int confirmacion = JOptionPane.showConfirmDialog(vista,
				"¿Estás seguro de que deseas eliminar el cliente con ID " + idCliente + "?", "Confirmar eliminación",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		// Verificamos si el usuario pulsó el botón "Sí" en la ventana de confirmación
		if (confirmacion == JOptionPane.YES_OPTION) {
			// Preparamos la conexión a la base de datos
			Modelo modelo = new Modelo();
			// Intentamos borrar al cliente enviando su ID y guardamos si tuvo éxito o no (true/false)
			boolean exito = modelo.eliminarCliente(idCliente);

			// Evaluamos el resultado
			if (exito) {
				// Si salió bien, avisamos al usuario
				JOptionPane.showMessageDialog(vista, "Cliente eliminado correctamente.");
				// Refrescamos la tabla de clientes recargando los datos desde la BBDD 
				// para que el cliente borrado desaparezca visualmente al instante
				vista.cargarDatosClientes(modelo.recuperarClientes());
			} else {
				// Si falla (por ejemplo, si el cliente tiene citas asociadas y la BBDD no deja borrarlo por seguridad), mostramos un error
				JOptionPane.showMessageDialog(vista, "Error: No se pudo eliminar el cliente.");
			}
		}
	}
}