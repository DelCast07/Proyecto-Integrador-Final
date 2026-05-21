package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controlador.cita.ControladorMenuCita;
import modelo.Cita;
import modelo.Modelo;
import vista.VentanaGestionCita;
import vista.VentanaLogin;
import vista.VentanaMaestro;
import vista.aprendiz.VentanaAprendiz;

// Clase que controla las acciones de la ventana de inicio de sesión
public class ControladorLogin implements ActionListener {

	private String categoriaEmpleado;
	private VentanaLogin vLogin;

	// Constructor que vincula este controlador con la ventana de Login
	public ControladorLogin(VentanaLogin vLogin) {
		this.vLogin = vLogin;
	}

	// Método que se ejecuta automáticamente cuando el usuario hace clic en el botón de entrar
	public void actionPerformed(ActionEvent e) {
		// Guardamos lo introducido en el textField en una variable
		String usuario = vLogin.getUsuario();
		
		// Convertimos el campo de contraseña en un texto normal para poder compararlo
		String contraseña = new String(vLogin.getContrasena());

		int idEmpleado;
		// Intentamos convertir el usuario escrito (texto) a un número entero (ID)
		try {
			idEmpleado = Integer.parseInt(usuario);
		} catch(NumberFormatException nfe) {
			// Si el usuario escribió letras en lugar de números, mostramos un error y detenemos el código aquí
			JOptionPane.showMessageDialog(null, "El usuario debe ser un numero (ID)");
			return;
		}
		
		// Creamos una instancia del Modelo para conectarnos a la base de datos
		Modelo modelo = new Modelo();
		
		// Preguntamos a la base de datos a qué categoría (rango) pertenece este usuario con esa contraseña
		String categoria = modelo.obtenerCategoria(idEmpleado, contraseña);
		
		// Si 'categoria' tiene algún valor, significa que el usuario y contraseña son correctos
		if(categoria != null) {
			idEmpleado = Integer.parseInt(vLogin.getUsuario());
			
			// Cerramos (destruimos) la ventana de login porque ya vamos a entrar al programa
			vLogin.dispose();
			
			// Dependiendo de si es Aprendiz, Maestro u Oficial, hacemos cosas diferentes
			switch(categoria) {
			case "Aprendiz":
				// Creamos la ventana y el controlador específicos para el Aprendiz
				VentanaAprendiz vGestionCitasAprendiz = new VentanaAprendiz("Menu Aprendiz", categoria, idEmpleado);
				ControladorMenuAprendiz cMenuAprendiz = new ControladorMenuAprendiz(vGestionCitasAprendiz, categoria, idEmpleado);
				vGestionCitasAprendiz.setControlador(cMenuAprendiz);
				
				// A los aprendices solo les cargamos sus propias citas (las que tienen asignadas)
				ArrayList<Cita> citasAprendiz = modelo.recuperarCitasPropias(idEmpleado);
				vGestionCitasAprendiz.cargarDatosCitas(citasAprendiz);
				
				// Hacemos visible su ventana
				vGestionCitasAprendiz.setVisible(true);
				break;
			case "Maestro":
				// Creamos la ventana y el controlador específicos para el Maestro
				VentanaMaestro vMaestro = new VentanaMaestro("Menu Maestro", categoria, idEmpleado);
				ControladorMenuMaestro cMenuMaestro = new ControladorMenuMaestro(vMaestro, categoria, idEmpleado);
				vMaestro.setControlador(cMenuMaestro);
				
				// Hacemos visible su ventana
				vMaestro.setVisible(true);
				break;
			case "Oficial":
				// Creamos la ventana y el controlador específicos para el Oficial
				VentanaGestionCita vGestionCitasOficial = new VentanaGestionCita(categoria, idEmpleado);
				ControladorMenuCita cOficial = new ControladorMenuCita(vGestionCitasOficial, categoria, idEmpleado);
				vGestionCitasOficial.setControlador(cOficial);
				
				// A los oficiales les cargamos TODAS las citas disponibles en la base de datos
				ArrayList<Cita> citasOficial = modelo.recuperarCitas();
				vGestionCitasOficial.cargarDatosCitas(citasOficial);
				
				// Hacemos visible su ventana
				vGestionCitasOficial.setVisible(true);
				break;
			}
		}else {
			// Si 'categoria' es null, el login ha fallado (datos incorrectos o usuario no existe)
			JOptionPane.showMessageDialog(null, "Acceso denegado: Contraseña o Usuario incorrectos");
		}
	}
}
