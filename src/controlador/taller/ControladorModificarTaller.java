package controlador.taller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import controlador.ControladorLogin;
import modelo.Modelo;
import modelo.Taller;
import vista.VentanaModificarTaller;
import vista.VentanaGestionTalleres;
import vista.VentanaLogin;
//Controlador que se encarga de gestionar la pantalla para editar la información de un taller que ya existe
public class ControladorModificarTaller implements ActionListener {
	// Variables para guardar la ventana actual, los datos del usuario y el ID específico del taller que vamos a editar
	private VentanaModificarTaller vista;
	private String rangoUsuario;
	private int idUsuario;
	private int idTaller;
	// Constructor: se ejecuta en cuanto se abre la ventana y guarda todos los datos necesarios para trabajar
	public ControladorModificarTaller(VentanaModificarTaller ventana, String rango, int idUser, int idTa) {
        this.vista = ventana;
        this.rangoUsuario = rango;
        this.idUsuario = idUser;
        this.idTaller = idTa; 
    }
	// Método que detecta los clics en los botones de la ventana
	public void actionPerformed(ActionEvent e) {
		// Preparamos la conexión a la base de datos
		Modelo modelo = new Modelo();

		// --- 1. BOTÓN ATRÁS ---
		// Si se pulsa el boton de atras
		if (e.getSource().equals(vista.getBtnAtras())) {
			// Preparamos la vuelta a la pantalla de gestión de talleres
			VentanaGestionTalleres vGestionTalleres = new VentanaGestionTalleres(vista.getRangoUsuario(), idUsuario);
		    ControladorMenuTaller cMenuTaller = new ControladorMenuTaller(vGestionTalleres, rangoUsuario, idUsuario);
		 // Recargamos la tabla de talleres por si hubo algún cambio general
		    vGestionTalleres.setControlador(cMenuTaller);
		 // Mostramos la lista y cerramos esta ventana
			vGestionTalleres.cargarDatosTalleres(modelo.recuperarTalleres());
			vGestionTalleres.setVisible(true);
			vista.dispose();
			return;
			
			// --- 2. BOTÓN CERRAR SESIÓN ---
		}else if (e.getSource().equals(vista.getBtnCerrarSesion())){
			// Nos lleva directamente a la pantalla de Login y cierra esta
			VentanaLogin vLogin = new VentanaLogin("Inicio de Sesión");
			ControladorLogin c = new ControladorLogin(vLogin);
			vLogin.setControlador(c);
			vLogin.setVisible(true);
			vista.dispose();
			
			// --- 3. BOTÓN GUARDAR CAMBIOS ---
		}if (e.getSource().equals(vista.getBtnGuardarCambios())) {
			// Rescatamos lo que el usuario haya escrito en los campos de texto
            String nombre = vista.getNombreTaller();
            String tipo = vista.getTipoSala();

         // Validamos que no haya borrado el nombre dejándolo en blanco
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "El nombre del taller no puede estar vacío.");
                return;
            }

         // Creamos un objeto Taller y le metemos los datos nuevos
            Taller tallerModificado = new Taller();
            tallerModificado.setId_taller(this.idTaller);
            tallerModificado.setNombre(nombre);
            tallerModificado.setTipo_sala(tipo);

         // Le pedimos al modelo que haga el UPDATE en la base de datos
            boolean exito = modelo.modificarTaller(tallerModificado);

         // Evaluamos cómo fue la actualización
            if (exito) {
            	// Si salió bien, avisamos
                JOptionPane.showMessageDialog(vista, "Taller actualizado correctamente.");
                VentanaGestionTalleres vGestionTalleres = new VentanaGestionTalleres(rangoUsuario, idUsuario);
			    ControladorMenuTaller cMenuTaller = new ControladorMenuTaller(vGestionTalleres, rangoUsuario, idUsuario);
			    vGestionTalleres.setControlador(cMenuTaller);
			    vGestionTalleres.setControlador(new ControladorMenuTaller(vGestionTalleres, rangoUsuario, idUsuario));
			 // Recargamos la tabla para que ya muestre el taller con su nuevo nombre o tipo
			    vGestionTalleres.cargarDatosTalleres(modelo.recuperarTalleres());
			    vGestionTalleres.setVisible(true);
                vista.dispose();
            } else {
            	// Si hubo un fallo al comunicarse con la BBDD, mostramos error
                JOptionPane.showMessageDialog(vista, "Error: No se pudo actualizar el taller.");
            }
        }
	}
}
