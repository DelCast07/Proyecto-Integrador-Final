package main;

import controlador.ControladorLogin;
import vista.VentanaLogin;

public class ProyInt_Main {

	public static void main(String[] args) {
		VentanaLogin vLogin = new VentanaLogin("Ventana Login");
		// Creamos el controlador y le pasamos la ventana de Login
		ControladorLogin c = new ControladorLogin(vLogin);
		vLogin.setControlador(c);
		vLogin.setVisible(true);
	}

}