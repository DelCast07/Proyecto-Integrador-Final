package controlador.cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import controlador.ControladorLogin;
import modelo.Modelo;
import modelo.Cliente;
import vista.VentanaModificarCliente;
import vista.VentanaGestionCliente;
import vista.VentanaLogin;

//Controlador que se encarga de gestionar la pantalla para editar la información de un cliente que ya existe
public class ControladorModificarCliente implements ActionListener {

	// Variable para guardar la ventana actual y el ID del usuario que está usando el programa
	private VentanaModificarCliente vista;
	private int idUsuario;

	// Constructor: se ejecuta al abrir la ventana y guarda los datos básicos
	public ControladorModificarCliente(VentanaModificarCliente vista, int id) {
		this.vista = vista;
		this.idUsuario = id;
	}

	// Método que detecta los clics en los botones de la ventana
	@Override
	public void actionPerformed(ActionEvent e) {
		// Preparamos la conexión a la base de datos
		Modelo modelo = new Modelo();

		// --- 1. BOTÓN ATRÁS ---
		// si se pulsa el boton de atrás
		if (e.getSource().equals(vista.getBtnAtras())) {
			// Llamamos al método auxiliar de abajo para volver a la lista de clientes
			regresarGestion(modelo);

		// --- 2. BOTÓN CERRAR SESIÓN ---
		// si se pulsa cerrar sesión
		} else if (e.getSource().equals(vista.getBtnCerrarSesion())) {
			VentanaLogin vLogin = new VentanaLogin("Inicio de Sesión");
			ControladorLogin cLogin = new ControladorLogin(vLogin);
			vLogin.setControlador(cLogin);
			vLogin.setVisible(true);
			vista.dispose();

		// --- 3. BOTÓN GUARDAR CAMBIOS ---
		// si se pulsa el botón de guardar cambios
		} else if (e.getSource().equals(vista.getBtnGuardarCambios())) {
			// Nos lleva directamente a la pantalla de Login y cierra esta
			String idStr = vista.getIdCliente();
			String nombre = vista.getNombreCliente();
			String superpoder = vista.getSuperpoderCliente();
			String colores = vista.getColorCliente();

			// Comprobamos que no haya borrado nada dejándolo en blanco
			if (nombre.isEmpty() || superpoder.isEmpty() || colores.isEmpty()) {
				JOptionPane.showMessageDialog(vista, "Por favor, rellena todos los campos.");
				return;
			}

			// Intentamos convertir el ID del cliente (que viene como texto) a número entero
			int idCliente;
			try {
				idCliente = Integer.parseInt(idStr);
			} catch (NumberFormatException nfe) {
				// Si por algún motivo el ID estuviera corrupto y tuviera letras, lanzamos error
				JOptionPane.showMessageDialog(vista, "Error: El ID del cliente es inválido.");
				return;
			}

			// creamos el objeto cliente con los datos modificados
			Cliente clienteModificado = new Cliente();
			clienteModificado.setId(idCliente);
			clienteModificado.setNombre(nombre);
			clienteModificado.setSuperpoder(superpoder);
			clienteModificado.setColores(colores);

			// llamamos al metodo del modelo para que haga el UPDATE en la BBDD
			boolean exito = modelo.modificarCliente(clienteModificado);

			// Evaluamos si la actualización funcionó
			if (exito) {
				// Avisamos de que todo fue bien y volvemos a la lista de clientes
				JOptionPane.showMessageDialog(vista, "Cliente actualizado correctamente.");
				regresarGestion(modelo);
			} else {
				// Si falló algo en la BBDD, mostramos error
				JOptionPane.showMessageDialog(vista, "Error: No se pudo actualizar el cliente.");
			}
		}
	}

	// metodo para volver a la gestion con el controlador activado
	private void regresarGestion(Modelo modelo) {
		VentanaGestionCliente vG = new VentanaGestionCliente(vista.getRangoUsuario(), idUsuario);
		ControladorMenuCliente cG = new ControladorMenuCliente(vG, vista.getRangoUsuario(), idUsuario);
		vG.setControlador(cG); // esto es lo que hace que botones funcionen al volver
		
		// Recargamos la tabla de clientes para que ya muestre los datos modificados
		vG.cargarDatosClientes(modelo.recuperarClientes());
		vG.setVisible(true); // Mostramos la lista
		vista.dispose(); // Cerramos la ventana de modificación
	}
}