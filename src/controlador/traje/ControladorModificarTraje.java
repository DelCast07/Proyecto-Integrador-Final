package controlador.traje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import controlador.ControladorLogin;
import modelo.Modelo;
import modelo.Traje; // <- Importación correcta
import vista.VentanaModificarTraje;
import vista.VentanaGestionTrajes;
import vista.VentanaLogin;

public class ControladorModificarTraje implements ActionListener {

	private VentanaModificarTraje vista;
	private String rangoUsuario;
	private int idUsuario;

	public ControladorModificarTraje(VentanaModificarTraje ventana, String rango, int id, String clienteActual) {
	    this.vista = ventana;
	    this.rangoUsuario = rango;
	    this.idUsuario = id;
	    
	    Modelo m = new Modelo();
	    this.vista.rellenarClientes(m.recuperarNombresClientes());
	    this.vista.seleccionarCliente(clienteActual);            
	}

	public void actionPerformed(ActionEvent e) {
		Modelo modelo = new Modelo();

		// Si se pulsa el boton de atras
		if (e.getSource().equals(vista.getBtnAtras())) {
			VentanaGestionTrajes vGestionTrajes = new VentanaGestionTrajes(vista.getRangoUsuario(), idUsuario);
		    
		    controlador.traje.ControladorMenuTraje cMenuTraje = new controlador.traje.ControladorMenuTraje(vGestionTrajes, rangoUsuario, idUsuario);
		    vGestionTrajes.setControlador(cMenuTraje);
		    vGestionTrajes.cargarDatosTrajes(modelo.recuperarTrajes());
		    vGestionTrajes.setVisible(true);
		    
		    vista.dispose();
		    return;
		} else if (e.getSource().equals(vista.getBtnCerrarSesion())){
			VentanaLogin vLogin = new VentanaLogin("Inicio de Sesión");
			ControladorLogin c = new ControladorLogin(vLogin);
			vLogin.setControlador(c);
			vLogin.setVisible(true);
			vista.dispose();
			return;
		} else if (e.getSource().equals(vista.getBtnGuardarCambios())){
			
			String nombre = vista.getNombreTraje();
			String estado = vista.getEstado();
			String clienteSeleccionado = vista.getNombreClienteSeleccionado();
			
			if (nombre.isEmpty() || clienteSeleccionado.isEmpty()) {
			    JOptionPane.showMessageDialog(vista, "Rellena todos los campos.");
			    return;
			}
			int idTraje = Integer.parseInt(vista.getIdTraje());
			int idCliente = modelo.obtenerIdClientePorNombre(clienteSeleccionado);

			Traje trajeModificado = new Traje();
			trajeModificado.setId_traje(idTraje);
			trajeModificado.setNombre(nombre);
			trajeModificado.setEstado(estado);
			trajeModificado.setId_cliente(idCliente);

			boolean exito = modelo.modificarTraje(trajeModificado);

			if (exito) {
			    JOptionPane.showMessageDialog(vista, "Traje actualizado correctamente.");
			    VentanaGestionTrajes vGestionTrajes = new VentanaGestionTrajes(vista.getRangoUsuario(), idUsuario);
			    
			    controlador.traje.ControladorMenuTraje cMenuTraje = new controlador.traje.ControladorMenuTraje(vGestionTrajes, rangoUsuario, idUsuario);
			    vGestionTrajes.setControlador(cMenuTraje);
			    vGestionTrajes.cargarDatosTrajes(modelo.recuperarTrajes());
			    vGestionTrajes.setVisible(true);
			    
			    vista.dispose();
			} else {
				JOptionPane.showMessageDialog(vista, "Error: No se pudo actualizar el traje");
			}
		}
	}
}