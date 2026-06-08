package controlador.empleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Modelo;
import modelo.Empleado;
import vista.VentanaCrearEmpleado;
import vista.VentanaGestionEmpleado;

public class ControladorCrearEmpleado implements ActionListener {

	private VentanaCrearEmpleado vista;
	private int idUsuario;
	private boolean oculto = true; // Controla si la clave se muestra


	public ControladorCrearEmpleado(VentanaCrearEmpleado vista, int id) {
		this.vista = vista;
		this.idUsuario = id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Modelo m = new Modelo();

		// Si se pulsa volver atrás
		if (e.getSource().equals(vista.getBtnAtras())) {
			volver(m);
			
		// Si se pulsa el botón del para cambiar el enmascaramiento
		} else if (e.getSource().equals(vista.getBtnVerContraseña())) {
			if (oculto) {
				vista.cambiarMascaraContraseña((char) 0); // Limpia la ocultación, mostrando texto 
				oculto = false;
			} else {
				vista.cambiarMascaraContraseña(vista.getEcoPorDefecto()); // Restaura los puntos originales del Login
				oculto = true;
			}
			
		// Si se pulsa registrar y guardar nuevo empleado
		} else if (e.getSource().equals(vista.getBtnGuardarCambios())) {
			
			// Validamos que ningún campo del formulario esté en blanco
			if(vista.getNombre().isEmpty() || vista.getApodo().isEmpty() || vista.getContraseña().isEmpty()) {
				JOptionPane.showMessageDialog(vista, "Rellena todos los campos.");
				return;
			}

			// Creamos la instancia Entidad con el ID en 0
			Empleado emp = new Empleado(0, vista.getNombre(), vista.getApodo(), vista.getCategoria(), vista.getContraseña());

			if (m.crearEmpleado(emp)) {
				JOptionPane.showMessageDialog(vista, "Empleado creado correctamente.");
				volver(m);
			} else {
				JOptionPane.showMessageDialog(vista, "Error al crear empleado. Comprueba que el apodo no esté repetido.");
			}
		}
	}

	// Cierre de ventana y refresco del listado general en la tabla de gestión de empleados
	private void volver(Modelo m) {
		VentanaGestionEmpleado vG = new VentanaGestionEmpleado(vista.getRangoUsuario(), idUsuario);
		ControladorMenuEmpleado cG = new ControladorMenuEmpleado(vG, vista.getRangoUsuario(), idUsuario);
		vG.setControlador(cG);
		vG.cargarDatosEmpleados(m.recuperarEmpleados());
		vG.setVisible(true);
		vista.dispose();
	}
}