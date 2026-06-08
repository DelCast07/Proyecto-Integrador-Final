package controlador.empleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Modelo;
import modelo.Empleado;
import vista.VentanaModificarEmpleado;
import vista.VentanaGestionEmpleado;

public class ControladorModificarEmpleado implements ActionListener {

	private VentanaModificarEmpleado vista;
	private int idUsuario;
	private boolean oculto = true; // Variable para controlar si la contraseña está tapada o visible

	public ControladorModificarEmpleado(VentanaModificarEmpleado vista, int id) {
		this.vista = vista;
		this.idUsuario = id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Modelo m = new Modelo();

		// Si el usuario pulsa el botón de ir atrás
		if (e.getSource().equals(vista.getBtnAtras())) {
			volver(m);
			
		// Si el usuario pulsa el botón para mostrar/ocultar contraseñas
		} else if (e.getSource().equals(vista.getBtnVerContraseña())) {
			if (oculto) {
				vista.cambiarMascaraContraseñas((char) 0); // Muestra el texto plano en las casillas
				oculto = false;
			} else {
				vista.cambiarMascaraContraseñas(vista.getEcoPorDefecto()); // Restaura los puntos originales
				oculto = true;
			}
			
		// Si el usuario pulsa el botón de guardar cambios
		} else if (e.getSource().equals(vista.getBtnGuardarCambios())) {
			
			// Validar que los campos obligatorios de texto base no estén vacíos
			if (vista.getNombre().isEmpty() || vista.getApodo().isEmpty() || vista.getContraseñaAnterior().isEmpty()) {
				JOptionPane.showMessageDialog(vista, "Rellena todos los campos obligatorios (Nombre, Apodo y Contraseña Anterior).");
				return;
			}

			// Comprobar que la contraseña anterior introducida coincida con la registrada en la base de datos
			if (!vista.getContraseñaAnterior().equals(vista.getContraseñaOriginal())) {
				JOptionPane.showMessageDialog(vista, "La contraseña anterior introducida no es correcta.");
				return;
			}

			// Determinar qué contraseña se va a registrar (mantiene la original por defecto)
			String contraseñaFinal = vista.getContraseñaOriginal(); 
			String nueva = vista.getContraseñaNueva();
			String repetir = vista.getContraseñaRepetir();

			// Si el usuario ha rellenado las casillas para cambiar la clave
			if (!nueva.isEmpty() || !repetir.isEmpty()) {
				// Validamos que ambas casillas sean idénticas
				if (!nueva.equals(repetir)) {
					JOptionPane.showMessageDialog(vista, "Las contraseñas no coinciden.");
					return;
				}
				// Si coinciden correctamente, asignamos la nueva clave
				contraseñaFinal = nueva;
			}

			// Se crea el objeto Empleado con la clave validada
			Empleado emp = new Empleado(
				vista.getIdEmpleado(), 
				vista.getNombre(), 
				vista.getApodo(), 
				vista.getCategoria(), 
				contraseñaFinal
			);

			// Se actualizan los datos en el modelo de la base de datos
			if (m.modificarEmpleado(emp)) {
				JOptionPane.showMessageDialog(vista, "Empleado modificado correctamente.");
				volver(m);
			} else {
				JOptionPane.showMessageDialog(vista, "Error al modificar empleado.");
			}
		}
	}

	private void volver(Modelo m) {
		VentanaGestionEmpleado vG = new VentanaGestionEmpleado(vista.getRangoUsuario(), idUsuario);
		ControladorMenuEmpleado cG = new ControladorMenuEmpleado(vG, vista.getRangoUsuario(), idUsuario);
		vG.setControlador(cG);
		vG.cargarDatosEmpleados(m.recuperarEmpleados());
		vG.setVisible(true);
		vista.dispose();
	}
}