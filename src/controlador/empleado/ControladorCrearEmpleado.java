package controlador.empleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Modelo;
import modelo.Empleado; // Importa tu clase empleado
import vista.VentanaCrearEmpleado;
import vista.VentanaGestionEmpleado;

public class ControladorCrearEmpleado implements ActionListener {

	private VentanaCrearEmpleado vista;
	private int idUsuario;

	public ControladorCrearEmpleado(VentanaCrearEmpleado vista, int id) {
		this.vista = vista;
		this.idUsuario = id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Modelo m = new Modelo();

		if (e.getSource().equals(vista.getBtnAtras())) {
			volver(m);
		} else if (e.getSource().equals(vista.getBtnGuardarCambios())) {
			
			// Validamos que se rellenó todo
			if(vista.getNombre().isEmpty() || vista.getApodo().isEmpty() || vista.getContrasena().isEmpty()) {
				JOptionPane.showMessageDialog(vista, "Rellena todos los campos.");
				return;
			}

			// Creamos el empleado (0 es un ID temporal, MySQL pone el autoincrementado)
			Empleado emp = new Empleado(0, vista.getNombre(), vista.getApodo(), vista.getCategoria(), vista.getContrasena());

			if (m.crearEmpleado(emp)) {
				JOptionPane.showMessageDialog(vista, "Empleado creado correctamente.");
				volver(m);
			} else {
				JOptionPane.showMessageDialog(vista, "Error al crear empleado. Comprueba que el apodo no esté repetido.");
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