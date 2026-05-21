package controlador.taller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import controlador.ControladorLogin;
import modelo.Modelo;
import modelo.Taller;
import vista.VentanaCrearTaller;
import vista.VentanaGestionTalleres;
import vista.VentanaLogin;
//Controlador que gestiona la pantalla para crear un nuevo taller en el sistema
public class ControladorCrearTaller implements ActionListener {

	// Variables para guardar la ventana actual, el rol del usuario y su ID
	private VentanaCrearTaller vista; 
	private String rangoUsuario;
	private int idUsuario;

	// Constructor: se ejecuta al abrir la ventana y guarda los datos básicos
	public ControladorCrearTaller(VentanaCrearTaller vista, String rango, int id) {
		this.vista = vista;
		this.rangoUsuario = rango;
		this.idUsuario = id; 
	}

	// Método que "escucha" y detecta cuándo el usuario hace clic en los botones
	public void actionPerformed(ActionEvent e) {
		// Preparamos la conexión a la base de datos
		Modelo modelo = new Modelo();

		// --- 1. BOTÓN ATRÁS ---
		if (e.getSource().equals(vista.getBtnAtras())) {
			// Preparamos la ventana principal de talleres para volver a ella
		    VentanaGestionTalleres vGestionTalleres = new VentanaGestionTalleres(vista.getRangoUsuario(), 0);
		    ControladorMenuTaller cMenuTaller = new ControladorMenuTaller(vGestionTalleres, rangoUsuario, idUsuario);
		    vGestionTalleres.setControlador(cMenuTaller);
		 // Recargamos la lista de talleres desde la BBDD
		    vGestionTalleres.cargarDatosTalleres(modelo.recuperarTalleres());
		 // Mostramos la lista de talleres y cerramos la ventana de creación
		    vGestionTalleres.setVisible(true);
		    vista.dispose();
		    return;
		    
		 // --- 2. BOTÓN CERRAR SESIÓN ---
		} else if (e.getSource().equals(vista.getBtnCerrarSesion())) {
			// Lleva al usuario directamente a la pantalla de Login y cierra esta
			VentanaLogin vLogin = new VentanaLogin("Inicio de Sesión");
			ControladorLogin cLogin = new ControladorLogin(vLogin);
			vLogin.setControlador(cLogin);
			vLogin.setVisible(true);
			vista.dispose();
			return;
			
			// --- 3. BOTÓN GUARDAR CAMBIOS ---
		} else if (e.getSource().equals(vista.getBtnGuardarCambios())) {
			String nombre = vista.getNombreTaller();
			String tipo = vista.getTipoSala();

			// Validamos que al menos el nombre no esté vacío
			if (nombre.isEmpty()) {
				JOptionPane.showMessageDialog(vista, "Por favor, rellena todos los campos.");
				return;
			}

			try {
				// Creamos un objeto Taller para agrupar los datos introducidos
				Taller taller = new Taller();
				taller.setId_taller(0);
				taller.setNombre(nombre);
				taller.setTipo_sala(tipo);

				// Intentamos crear el taller ejecutando la orden en la base de datos
				if (modelo.crearTaller(taller)) {
					// Si sale bien, avisamos
					JOptionPane.showMessageDialog(vista, "Taller creado exitosamente");
					// Preparamos el regreso a la ventana de gestión de talleres
					VentanaGestionTalleres vGestionTalleres = new VentanaGestionTalleres(vista.getRangoUsuario(), idUsuario);
				    ControladorMenuTaller cMenuTaller = new ControladorMenuTaller(vGestionTalleres, rangoUsuario, idUsuario);
				    vGestionTalleres.setControlador(cMenuTaller);
				 // Recargamos la tabla para que ya aparezca el taller nuevo
				    vGestionTalleres.cargarDatosTalleres(modelo.recuperarTalleres());
				 // Mostramos la lista y cerramos la ventana actual
				    vGestionTalleres.setVisible(true);
					vista.dispose();
				} else {
					// Si falla la BBDD, mostramos error
					JOptionPane.showMessageDialog(vista, "Error: No se pudo crear el taller");
				}
			} catch (NumberFormatException ex) {
				// Este catch atrapa errores si por accidente se intenta convertir texto a número en los campos
				JOptionPane.showMessageDialog(vista, "El ID del taller debe ser un número entero.");
			}
		}
	}
}
