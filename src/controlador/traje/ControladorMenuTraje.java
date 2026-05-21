package controlador.traje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import controlador.ControladorLogin;
import controlador.ControladorMenuMaestro;
import modelo.Modelo;
import vista.VentanaCrearTraje;
import vista.VentanaGestionTrajes;
import vista.VentanaLogin;
import vista.VentanaMaestro;
import vista.VentanaModificarTraje;


public class ControladorMenuTraje implements ActionListener {

	private VentanaGestionTrajes vGestionTrajes;
	private Modelo m;
	private String rangoUsuario;
	private int idUsuario;

	public ControladorMenuTraje(VentanaGestionTrajes v, String rango, int id) {
		this.vGestionTrajes = v;
		this.m = new Modelo();
		this.rangoUsuario = rango;
		this.idUsuario = id;
	}

	@Override
	public void actionPerformed(ActionEvent e) { 

		if (e.getSource().equals(vGestionTrajes.getBtnEliminar())) { 
			int idTraje = vGestionTrajes.getIdTrajeSeleccionado(); 
			if (idTraje == -1) {
				JOptionPane.showMessageDialog(vGestionTrajes, "Selecciona el traje que desea eliminar");
			} else {
				int confirmacion = JOptionPane.showConfirmDialog(vGestionTrajes,
						"¿Seguro que deseas eliminar el traje con ID " + idTraje + "?", "Confirmar",
						JOptionPane.YES_NO_OPTION);
				if (confirmacion == JOptionPane.YES_OPTION) {
					if (m.eliminarTraje(idTraje)) {
						JOptionPane.showMessageDialog(vGestionTrajes, "Traje eliminado");
						vGestionTrajes.cargarDatosTrajes(m.recuperarTrajes());
						return;
					} else {
						JOptionPane.showMessageDialog(vGestionTrajes, "Error al eliminar el traje");
					}
				}
			}
		} else if (e.getSource().equals(vGestionTrajes.getBtnCrear())) {
			VentanaCrearTraje vCrearTraje = new VentanaCrearTraje(vGestionTrajes.getRangoUsuario(), rangoUsuario, idUsuario);
			ControladorCrearTraje cCrearTraje = new ControladorCrearTraje(vCrearTraje, rangoUsuario, idUsuario); 
			vCrearTraje.setControladorGuardar(cCrearTraje);
			vCrearTraje.setVisible(true);
			vGestionTrajes.dispose();
		} else if(e.getSource().equals(vGestionTrajes.getBtnModificar())) {
		    int idTraje = vGestionTrajes.getIdTrajeSeleccionado();
		    
		    if (idTraje == -1) {
		        JOptionPane.showMessageDialog(vGestionTrajes, "Selecciona un traje para modificar");
		    } else {
		        // 1. Sacamos los datos básicos de los métodos que ya tenías
		        String nombreTraje = vGestionTrajes.getNombreTrajeSeleccionado();
		        String estado = vGestionTrajes.getEstadoTrajeSeleccionado();
		        
		        int fila = vGestionTrajes.getTable().getSelectedRow();
		        String clienteActual = vGestionTrajes.getTable().getValueAt(fila, 3).toString();

		        VentanaModificarTraje vModificarTraje = new VentanaModificarTraje(rangoUsuario, idTraje, nombreTraje, estado, clienteActual);
		        ControladorModificarTraje cModificarTraje = new ControladorModificarTraje(vModificarTraje, rangoUsuario, idUsuario, clienteActual);
		        
		        vModificarTraje.setControladorModificar(cModificarTraje);
		        vModificarTraje.setVisible(true);
		        vGestionTrajes.dispose();
		    }
		} else if (e.getSource().equals(vGestionTrajes.getBtnAtras())) {
			VentanaMaestro vMaestro = new VentanaMaestro("Menú Maestro", rangoUsuario, idUsuario);
			ControladorMenuMaestro cMaestro = new ControladorMenuMaestro(vMaestro, rangoUsuario, idUsuario);
			vMaestro.setControlador(cMaestro);
			vMaestro.setVisible(true);
			vGestionTrajes.dispose();
		} else if (e.getSource().equals(vGestionTrajes.getBtnCerrarSesion())) {
			VentanaLogin vLogin = new VentanaLogin("Inicio de Sesión"); 
			ControladorLogin c = new ControladorLogin(vLogin);
			vLogin.setControlador(c);
			vLogin.setVisible(true);
			vGestionTrajes.dispose();
		}
	}
}