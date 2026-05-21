package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Modelo;
import vista.VentanaLogin;
import vista.aprendiz.VentanaAprendiz;

//Clase que controla las acciones de la ventana principal del Aprendiz
public class ControladorMenuAprendiz implements ActionListener {
	// Variables para guardar la ventana, la conexión a la base de datos y la información del usuario
	private VentanaAprendiz vMaestro;
	private Modelo m;
	private String rangoUsuario;
	private int idUsuario;

	// Constructor que vincula este controlador con la ventana del aprendiz al abrirse
	public ControladorMenuAprendiz(VentanaAprendiz v, String rango, int id) {
		vMaestro = v;
		m = new Modelo();
		rangoUsuario = rango;
		idUsuario = id;
	}
	// Método que "escucha" y se ejecuta cada vez que el usuario hace clic en un botón
	public void actionPerformed(ActionEvent e) {
		// Comprueba si el botón que se pulsó fue el de "Cerrar Sesión"
	    if (e.getSource().equals(vMaestro.getBtnCerrarSesion())) {
	    	
	    	// Crea una nueva ventana de Login para que otro usuario (o el mismo) pueda entrar
	        VentanaLogin vLogin = new VentanaLogin("Inicio de Sesión");
	        
	        // Le asigna el controlador al Login para que sus botones funcionen
	        vLogin.setControlador(new ControladorLogin(vLogin));
	        
	        // Hace visible la pantalla de inicio de sesión
	        vLogin.setVisible(true);
	        
	        // Cierra la ventana actual del aprendiz para no dejar ventanas basura abiertas
	        vMaestro.dispose();
	    }
	}
}
