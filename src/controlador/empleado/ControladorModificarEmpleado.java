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
			
			if(vista.getNombre().isEmpty() || vista.getApodo().isEmpty() || vista.getContrasena().isEmpty()) {
				JOptionPane.showMessageDialog(vista, "Rellena todos los campos.");
				return;
			}

			Empleado emp = new Empleado(vista.getIdEmpleado(), vista.getNombre(), vista.getApodo(), vista.getCategoria(), vista.getContrasena());

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