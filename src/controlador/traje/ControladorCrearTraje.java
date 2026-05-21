package controlador.traje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controlador.ControladorLogin;
import controlador.cita.ControladorCrearCita;
import modelo.Modelo;
import modelo.Traje;
import vista.VentanaCrearCita;
import vista.VentanaCrearTraje;
import vista.VentanaGestionTrajes;
import vista.VentanaLogin;

public class ControladorCrearTraje implements ActionListener {

	private VentanaCrearTraje vista;
	private String rangoUsuario;
	private int idUsuario;

	public ControladorCrearTraje(VentanaCrearTraje vista, String rango, int id) {
		this.vista = vista;
		this.rangoUsuario = rango;
		this.idUsuario = id;
		Modelo m = new Modelo();
		ArrayList<String> clientesActualizados = m.recuperarNombresClientes();
		this.vista.rellenarClientes(m.recuperarNombresClientes());
	}

	public void actionPerformed(ActionEvent e) {
		Modelo m = new Modelo();

		if (e.getSource().equals(vista.getBtnAtras())) {
			String origen = vista.getVentanaOrigen();
			
			if (origen != null && origen.equals("Crear Cita")) {
				VentanaCrearCita vCrearCita = new VentanaCrearCita(rangoUsuario, idUsuario);
				ControladorCrearCita cCrearCita = new ControladorCrearCita(vCrearCita, rangoUsuario, idUsuario);
				vCrearCita.setControlador(cCrearCita);
				vCrearCita.setVisible(true);
			} else { 
				VentanaGestionTrajes vGestionTrajes = new VentanaGestionTrajes(vista.getRangoUsuario(), idUsuario);
				ControladorMenuTraje cGestionTrajes = new ControladorMenuTraje(vGestionTrajes, vista.getRangoUsuario(), idUsuario);
				vGestionTrajes.setControlador(cGestionTrajes);
				vGestionTrajes.cargarDatosTrajes(m.recuperarTrajes());
				vGestionTrajes.setVisible(true);
				vista.dispose();			
			}			
		} else if (e.getSource().equals(vista.getBtnCerrarSesion())) {
			VentanaLogin vLogin = new VentanaLogin("Inicio de Sesión");
			ControladorLogin cLogin = new ControladorLogin(vLogin);
			vLogin.setControlador(cLogin);
			vLogin.setVisible(true);
			vista.dispose();
			return;
		} else if (e.getSource().equals(vista.getBtnGuardarCambios())) {
		    String nombre = vista.getNombreTraje();
		    String estado = vista.getEstado();
		    String clienteSeleccionado = vista.getNombreClienteSeleccionado();

		    if (nombre.isEmpty() || clienteSeleccionado.isEmpty()) {
		        JOptionPane.showMessageDialog(vista, "Por favor, rellena todos los campos.");
		        return;
		    }

		    try {
		        // Buscamos el ID correspondiente al cliente seleccionado en el comboBox
		        int idCliente = m.obtenerIdClientePorNombre(clienteSeleccionado); 

		        Traje traje = new Traje();
		        traje.setId_traje(0);
		        traje.setNombre(nombre);
		        traje.setEstado(estado);
		        traje.setId_cliente(idCliente);

		        System.out.println("Guardando traje para el cliente: " + clienteSeleccionado);
		        
				if (m.crearTraje(traje)) {
					JOptionPane.showMessageDialog(vista, "Traje creado exitosamente");
					
					String origen = vista.getVentanaOrigen();
					if (origen != null && origen.equals("Crear Cita")) {
						VentanaCrearCita vCrearCita = new VentanaCrearCita(rangoUsuario, idUsuario);
						ControladorCrearCita cCrearCita = new ControladorCrearCita(vCrearCita, rangoUsuario, idUsuario);
						vCrearCita.setControlador(cCrearCita);
						vCrearCita.setVisible(true);
						vista.dispose();
					} else {
						VentanaGestionTrajes vGestionTrajes = new VentanaGestionTrajes(vista.getRangoUsuario(), idUsuario);
						ControladorMenuTraje cMenuTraje = new ControladorMenuTraje(vGestionTrajes, vista.getRangoUsuario(), idUsuario);
						vGestionTrajes.setControlador(cMenuTraje);
						vGestionTrajes.cargarDatosTrajes(m.recuperarTrajes());
						vGestionTrajes.setVisible(true);
						vista.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(vista, "Error: No se pudo crear el traje");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(vista, "El ID del traje debe ser un número entero.");
			}
		}
	}
}
