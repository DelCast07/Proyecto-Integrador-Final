package controlador.empleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import controlador.ControladorLogin;
import controlador.ControladorMenuMaestro;
import modelo.Modelo;
import vista.VentanaCrearEmpleado;
import vista.VentanaGestionEmpleado;
import vista.VentanaLogin;
import vista.VentanaMaestro;
import vista.VentanaModificarEmpleado;

public class ControladorMenuEmpleado implements ActionListener {

	private VentanaGestionEmpleado vista;
	private Modelo m;
	private String rangoUsuario;
	private int idUsuario;

	public ControladorMenuEmpleado(VentanaGestionEmpleado v, String rango, int id) {
		this.vista = v;
		this.m = new Modelo();
		this.rangoUsuario = rango;
		this.idUsuario = id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// BOTÓN ELIMINAR
		if (e.getSource().equals(vista.getBtnEliminar())) {
			int idEmpleado = vista.getIdEmpleadoSeleccionado(); 
			
			if (idEmpleado == -1) {
				JOptionPane.showMessageDialog(vista, "Selecciona el empleado que deseas eliminar.");
			} else {
				// Si tiene citas, salta advertencia y BLOQUEA
				if (m.tieneCitasAsociadas(idEmpleado)) {
					JOptionPane.showMessageDialog(vista, 
							"❌ No se puede borrar a este empleado.\nActualmente es responsable (o aprendiz) en una o más citas.\nPor favor, cambia a los responsables de esas citas primero.", 
							"Borrado Bloqueado", 
							JOptionPane.ERROR_MESSAGE);
				} else {
					// Si no tiene citas, borramos normal
					int confirmacion = JOptionPane.showConfirmDialog(vista,
							"¿Estás seguro de que deseas eliminar a este empleado del sistema?", 
							"Confirmar eliminación",
							JOptionPane.YES_NO_OPTION);
					
					if (confirmacion == JOptionPane.YES_OPTION) {
						if (m.eliminarEmpleado(idEmpleado)) {
							JOptionPane.showMessageDialog(vista, "Empleado eliminado correctamente.");
							vista.cargarDatosEmpleados(m.recuperarEmpleados());
						} else {
							JOptionPane.showMessageDialog(vista, "Error al eliminar el empleado.");
						}
					}
				}
			}
			
		// BOTÓN CREAR
		} else if (e.getSource().equals(vista.getBtnCrear())) {
			VentanaCrearEmpleado vCrear = new VentanaCrearEmpleado(rangoUsuario, idUsuario);
			ControladorCrearEmpleado cCrear = new ControladorCrearEmpleado(vCrear, idUsuario);
			vCrear.setControlador(cCrear); 
			vCrear.setVisible(true);
			vista.dispose();
			
		// BOTÓN MODIFICAR 
		} else if (e.getSource().equals(vista.getBtnModificar())) {
			int idEmpleado = vista.getIdEmpleadoSeleccionado();
			if (idEmpleado == -1) {
				JOptionPane.showMessageDialog(vista, "Selecciona un empleado para modificar");
			} else {
				// Extraemos los datos de la fila
				String nombre = vista.getNombreSeleccionado();
				String apodo = vista.getApodoSeleccionado();
				String categoria = vista.getCategoriaSeleccionada();
				String contrasena = vista.getContrasenaSeleccionada();
				
				VentanaModificarEmpleado vMod = new VentanaModificarEmpleado(rangoUsuario, idEmpleado, nombre, apodo, categoria, contrasena);
				ControladorModificarEmpleado cMod = new ControladorModificarEmpleado(vMod, idUsuario);
				vMod.setControlador(cMod);
				vMod.setVisible(true);
				vista.dispose();
			}
			
		// BOTÓN ATRÁS 
		} else if (e.getSource().equals(vista.getBtnAtras())) {
			VentanaMaestro vMaestro = new VentanaMaestro("Menú Principal", rangoUsuario, idUsuario);
			ControladorMenuMaestro cMaestro = new ControladorMenuMaestro(vMaestro, rangoUsuario, idUsuario);
			vMaestro.setControlador(cMaestro);
			vMaestro.setVisible(true);
			vista.dispose();
			
		// BOTÓN CERRAR SESIÓN 
		} else if (e.getSource().equals(vista.getBtnCerrarSesion())) {
			VentanaLogin vLogin = new VentanaLogin("Inicio de Sesión");
			ControladorLogin c = new ControladorLogin(vLogin);
			vLogin.setControlador(c);
			vLogin.setVisible(true);
			vista.dispose();
		}
	}
}