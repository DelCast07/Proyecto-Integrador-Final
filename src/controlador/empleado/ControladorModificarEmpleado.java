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

	public ControladorModificarEmpleado(VentanaModificarEmpleado vista, int id) {
		this.vista = vista;
		this.idUsuario = id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Modelo m = new Modelo();

		if (e.getSource().equals(vista.getBtnAtras())) {
			volver(m);
		} else if (e.getSource().equals(vista.getBtnGuardarCambios())) {
			
			// Validar que los campos de texto base no estén vacíos
			if (vista.getNombre().isEmpty() || vista.getApodo().isEmpty() || vista.getContrasenaAnterior().isEmpty()) {
				JOptionPane.showMessageDialog(vista, "Rellena todos los campos obligatorios (Nombre, Apodo y Contraseña Anterior).");
				return;
			}

			// Comprobar que la contraseña anterior sea la correcta para poder autorizar cambios
			if (!vista.getContrasenaAnterior().equals(vista.getContrasenaOriginal())) {
				JOptionPane.showMessageDialog(vista, "La contraseña anterior introducida no es correcta.");
				return;
			}

			// Determinar qué contraseña se va a guardar
			String contrasenaFinal = vista.getContrasenaOriginal(); // Por defecto mantiene la de siempre
			String nueva = vista.getContrasenaNueva();
			String repetir = vista.getContrasenaRepetir();

			// Si el usuario ha intentado rellenar una nueva contraseña
			if (!nueva.isEmpty() || !repetir.isEmpty()) {
				// Validamos que ambas casillas sean idénticas
				if (!nueva.equals(repetir)) {
					JOptionPane.showMessageDialog(vista, "Las contraseñas no coinciden.");
					return;
				}
				// Si coincide, asignamos la nueva clave para meterla en la base de datos
				contrasenaFinal = nueva;
			}

			// aqui se hace el objeto Empleado con la clave validada
			Empleado emp = new Empleado(
				vista.getIdEmpleado(), 
				vista.getNombre(), 
				vista.getApodo(), 
				vista.getCategoria(), 
				contrasenaFinal
			);

			// se actualizan los datos en el Modelo SQL
			if (m.modificarEmpleado(emp)) {
				JOptionPane.showMessageDialog(vista, "Empleado modificado correctamente.");
				volver(m);
			//si pasa cualquier cosa como que se repite algún caracter o demasiado largo o ya existe. pues salta error 
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