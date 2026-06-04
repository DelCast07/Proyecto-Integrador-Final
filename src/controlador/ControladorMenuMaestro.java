package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


import controlador.empleado.ControladorMenuEmpleado;
import controlador.cita.ControladorMenuCita;
import controlador.taller.ControladorMenuTaller;
import controlador.traje.ControladorMenuTraje;
import modelo.Cita;
import modelo.Modelo;
import modelo.Cliente;
import vista.VentanaGestionCita;
import vista.VentanaGestionCliente;
import vista.VentanaGestionTalleres;
import vista.VentanaGestionTrajes;
import vista.VentanaLogin;
import vista.VentanaMaestro;
import vista.VentanaGestionEmpleado;
import controlador.empleado.ControladorMenuEmpleado;

//Controlador principal para gestionar el menú del usuario con rango "Maestro"
public class ControladorMenuMaestro implements ActionListener {
	// Variables para guardar la ventana actual, la conexión a la base de datos y los datos del usuario
	private VentanaMaestro vMaestro;
	private Modelo m;
	private String rangoUsuario;
	private int idUsuario;

	// Constructor que recibe y guarda la ventana, el rango y el ID del usuario al abrir el menú
	public ControladorMenuMaestro(VentanaMaestro v, String rango, int id) {
		vMaestro = v;
		m = new Modelo();
		rangoUsuario = rango;
		idUsuario = id;
	}
	// Método que "escucha" y se ejecuta cada vez que el Maestro hace clic en un botón del menú
	public void actionPerformed(ActionEvent e) {

	    // Comprueba si el botón pulsado fue el de "Citas"
	    if (e.getSource().equals(vMaestro.getBtnCitas())) {
	    	// Prepara la nueva ventana de gestión de citas y le asigna su controlador
	        VentanaGestionCita vGestionCitas = new VentanaGestionCita(rangoUsuario, idUsuario);
	        ControladorMenuCita cCitas = new ControladorMenuCita(vGestionCitas, rangoUsuario, idUsuario);
	        vGestionCitas.setControlador(cCitas);

	        ArrayList<Cita> datosCitas;
	        // Decide qué citas mostrar: si por algún motivo entra un Aprendiz, solo ve las suyas. 
	     	// Si es otro cargo (como Maestro u Oficial), ve todas.
	        if (rangoUsuario.equals("Aprendiz")) {
	            datosCitas = m.recuperarCitasPropias(idUsuario);
	        } else {
	            datosCitas = m.recuperarCitas();
	        }
	     // Carga los datos en la tabla, muestra la ventana de Citas y cierra el menú actual
	        vGestionCitas.cargarDatosCitas(datosCitas);
	        vGestionCitas.setVisible(true);
	        vMaestro.dispose();

	    // Comprueba si el botón pulsado fue el de "Talleres"
	    } else if (e.getSource().equals(vMaestro.getBtnTalleres())) {
	    	// Abre la pantalla de talleres, conecta su controlador, carga los datos de la BBDD y cierra el menú actual
	        VentanaGestionTalleres vGestionTalleres = new VentanaGestionTalleres(rangoUsuario, idUsuario);
	        ControladorMenuTaller cMenuTaller = new ControladorMenuTaller(vGestionTalleres, rangoUsuario, idUsuario);
	        vGestionTalleres.setControlador(cMenuTaller);
	        vGestionTalleres.cargarDatosTalleres(m.recuperarTalleres());
	        vGestionTalleres.setVisible(true);
	        vMaestro.dispose();

	    // Comprueba si el botón pulsado fue el de "Clientes"
	    } else if (e.getSource().equals(vMaestro.getBtnClientes())) {
	        VentanaGestionCliente vGestionClientes = new VentanaGestionCliente(rangoUsuario, idUsuario);
	        controlador.cliente.ControladorMenuCliente cClientes = new controlador.cliente.ControladorMenuCliente(vGestionClientes, rangoUsuario, idUsuario);
	        vGestionClientes.setControlador(cClientes);
	        
	        // Solicita la lista de clientes actualizada, la muestra y cierra el menú actual
	        ArrayList<Cliente> datosActualizados = m.recuperarClientes();
	        vGestionClientes.cargarDatosClientes(datosActualizados);
	        vGestionClientes.setVisible(true);
	        vMaestro.dispose();

	    // Comprueba si el botón pulsado fue el de "Trajes" 
	    } else if (e.getSource().equals(vMaestro.getBtnTrajes())) {
	        VentanaGestionTrajes vGestionTrajes = new VentanaGestionTrajes(rangoUsuario, idUsuario);
	        ControladorMenuTraje cMenuTrajes = new ControladorMenuTraje(vGestionTrajes, rangoUsuario, idUsuario);
	        vGestionTrajes.setControlador(cMenuTrajes); 
	        vGestionTrajes.cargarDatosTrajes(m.recuperarTrajes());
	        vGestionTrajes.setVisible(true);
	        vMaestro.dispose();

	    // Comprueba si el botón pulsado fue el de "Cerrar Sesion"
	    } else if (e.getSource().equals(vMaestro.getBtnCerrarSesion())) {
	        VentanaLogin vLogin = new VentanaLogin("Inicio de Sesión");
	        vLogin.setControlador(new ControladorLogin(vLogin));
	        vLogin.setVisible(true);
	        vMaestro.dispose();
	    }
	    
	    else if (e.getSource().equals(vMaestro.getBtnEmpleados())) {
	        VentanaGestionEmpleado vGestion = new VentanaGestionEmpleado(rangoUsuario, idUsuario);
	        controlador.empleado.ControladorMenuEmpleado cMenu = new controlador.empleado.ControladorMenuEmpleado(vGestion, rangoUsuario, idUsuario);
	        vGestion.setControlador(cMenu);

	        vGestion.cargarDatosEmpleados(m.recuperarEmpleados());
	        vGestion.setVisible(true);
	        vMaestro.dispose();
	    }
	    
	    
	    
	}
}
