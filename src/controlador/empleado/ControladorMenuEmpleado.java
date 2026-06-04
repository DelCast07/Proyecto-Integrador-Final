package controlador.empleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import modelo.Empleado;
import modelo.Modelo;
import vista.VentanaGestionEmpleado;
import vista.VentanaLogin;
import vista.VentanaMaestro;
import controlador.ControladorLogin;
import controlador.ControladorMenuMaestro;

public class ControladorMenuEmpleado implements ActionListener {

	private VentanaGestionEmpleado vGestionEmpleados;
	private Modelo m;
	private String rangoUsuario;
	private int idUsuario;

	public ControladorMenuEmpleado(VentanaGestionEmpleado v, String rango, int id) {
		this.vGestionEmpleados = v;
		this.m = new Modelo();
		this.rangoUsuario = rango;
		this.idUsuario = id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// ACCIÓN ATRÁS
		if (e.getSource().equals(vGestionEmpleados.getBtnAtras())) {
			VentanaMaestro vMaestro = new VentanaMaestro("Menú Maestro", rangoUsuario, idUsuario);
			ControladorMenuMaestro cMaestro = new ControladorMenuMaestro(vMaestro, rangoUsuario, idUsuario);
			vMaestro.setControlador(cMaestro);
			vMaestro.setVisible(true);
			vGestionEmpleados.dispose();
			
		// ACCIÓN CERRAR SESIÓN
		} else if (e.getSource().equals(vGestionEmpleados.getBtnCerrarSesion())) {
			VentanaLogin vLogin = new VentanaLogin("Inicio de Sesión");
			vLogin.setControlador(new ControladorLogin(vLogin));
			vLogin.setVisible(true);
			vGestionEmpleados.dispose();

		// ACCIÓN CREAR EMPLEADO
		} else if (e.getSource().equals(vGestionEmpleados.getBtnCrearEmpleado())) {
			String nombre = JOptionPane.showInputDialog(null, "Escribe el nombre completo del empleado:", "Nuevo Empleado", JOptionPane.QUESTION_MESSAGE);
			if (nombre == null || nombre.trim().isEmpty()) return;
			
			String apodo = JOptionPane.showInputDialog(null, "Escribe su apodo / nombre de usuario:", "Nuevo Empleado", JOptionPane.QUESTION_MESSAGE);
			if (apodo == null || apodo.trim().isEmpty()) return;
			
			String[] categorias = {"Aprendiz", "Oficial", "Maestro"};
			// CORREGIDO: categorias[0] en lugar de categories[0]
			String categoria = (String) JOptionPane.showInputDialog(null, "Selecciona el rango:", "Nuevo Empleado", JOptionPane.QUESTION_MESSAGE, null, categorias, categorias[0]);
			if (categoria == null) return;
			
			String contrasena = JOptionPane.showInputDialog(null, "Asigna una contraseña inicial:", "Nuevo Empleado", JOptionPane.QUESTION_MESSAGE);
			if (contrasena == null || contrasena.trim().isEmpty()) return;

			Empleado nuevo = new Empleado(0, nombre, apodo, categoria, contrasena);
			if (m.crearEmpleado(nuevo)) {
				JOptionPane.showMessageDialog(null, "Empleado creado con éxito.");
				vGestionEmpleados.cargarDatosEmpleados(m.recuperarEmpleados());
			}

		// ACCIÓN MODIFICAR EMPLEADO
		} else if (e.getSource().equals(vGestionEmpleados.getBtnModificarEmpleado())) {
			int idSel = vGestionEmpleados.getIdEmpleadoSeleccionado();
			if (idSel == -1) {
				JOptionPane.showMessageDialog(null, "Por favor, selecciona un empleado de la tabla.", "Atención", JOptionPane.WARNING_MESSAGE);
				return;
			}

			String nombre = JOptionPane.showInputDialog(null, "Modificar nombre:", vGestionEmpleados.getNombreEmpleadoSeleccionado());
			if (nombre == null || nombre.trim().isEmpty()) return;
			
			String apodo = JOptionPane.showInputDialog(null, "Modificar apodo:", vGestionEmpleados.getApodoEmpleadoSeleccionado());
			if (apodo == null || apodo.trim().isEmpty()) return;
			
			String[] categorias = {"Aprendiz", "Oficial", "Maestro"};
			String categoria = (String) JOptionPane.showInputDialog(null, "Modificar rango:", "Actualizar rango", JOptionPane.QUESTION_MESSAGE, null, categorias, vGestionEmpleados.getCategoriaEmpleadoSeleccionado());
			if (categoria == null) return;
			
			String contrasena = JOptionPane.showInputDialog(null, "Modificar contraseña:", vGestionEmpleados.getContrasenaEmpleadoSeleccionado());
			if (contrasena == null || contrasena.trim().isEmpty()) return;

			Empleado modificado = new Empleado(idSel, nombre, apodo, categoria, contrasena);
			if (m.modificarEmpleado(modificado)) {
				JOptionPane.showMessageDialog(null, "Empleado modified correctamente.");
				vGestionEmpleados.cargarDatosEmpleados(m.recuperarEmpleados());
			}

		// ACCIÓN ELIMINAR EMPLEADO + CONTROL DE BORRADO DE CITAS
		} else if (e.getSource().equals(vGestionEmpleados.getBtnEliminarEmpleado())) {
			int idSel = vGestionEmpleados.getIdEmpleadoSeleccionado();
			if (idSel == -1) {
				JOptionPane.showMessageDialog(null, "Selecciona primero qué empleado deseas eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (m.tieneCitasAsociadas(idSel)) {
				JOptionPane.showMessageDialog(null, 
					"No se puede borrar a este empleado mientras sea responsable de citas.\n" +
					"Cambia los responsables primero.", 
					"Error de Eliminación", 
					JOptionPane.ERROR_MESSAGE);
				return;
			}

			int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres eliminar a " + vGestionEmpleados.getNombreEmpleadoSeleccionado() + "?", "Confirmar baja", JOptionPane.YES_NO_OPTION);
			if (confirmacion == JOptionPane.YES_OPTION) {
				if (m.eliminarEmpleado(idSel)) {
					JOptionPane.showMessageDialog(null, "Empleado dado de baja correctamente.");
					vGestionEmpleados.cargarDatosEmpleados(m.recuperarEmpleados());
				}
			}
		}
	}
}