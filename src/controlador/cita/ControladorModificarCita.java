package controlador.cita;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import modelo.Cita;
import modelo.Modelo;
import vista.VentanaModificarCitas;
import vista.VentanaAsignarAprendices;
import vista.VentanaGestionCita;
import vista.VentanaLogin;
import controlador.ControladorLogin;
//Controlador que gestiona la pantalla para editar o modificar una cita existente
public class ControladorModificarCita implements ActionListener {

	// Variables para la ventana, el rango/ID del usuario, la BBDD y los aprendices
	private VentanaModificarCitas vista;
	private String rangoUsuario;
	private int idUsuario;
	private Modelo modelo;
	// Guardarán los IDs de los aprendices (0 significa que no se seleccionó ninguno)
	private int idAprendiz1Seleccionado = 0;
	private int idAprendiz2Seleccionado = 0;

	// Constructor: se ejecuta en cuanto se abre la ventana de modificar cita
	public ControladorModificarCita(VentanaModificarCitas vista, String rango, int id) {
		this.vista = vista;
		this.rangoUsuario = rango;
		this.idUsuario = id;
		this.modelo = new Modelo();

		// 1. Al cargar, rellenamos los combos de la vista (importante para que se pueda
		// seleccionar)
		cargarCombos();
	}

		// Método auxiliar para llenar las listas desplegables (ComboBox) con los datos de la base de datos
		private void cargarCombos() {
			ArrayList<String> clientes = modelo.recuperarNombresClientes();
			ArrayList<String> talleres = modelo.recuperarNombresTalleres();
			ArrayList<String> empleados = modelo.recuperarNombresEmpleados();
			ArrayList<String> trajes = modelo.recuperarNombresTrajes();
			vista.rellenarComboBox(clientes, talleres, empleados, trajes);
		}
	
	// Método que detecta cuándo el usuario hace clic en los botones
	@Override
	public void actionPerformed(ActionEvent e) {
		Modelo m = new Modelo();

		// LOGICA DE BOTONES
		if (e.getSource().equals(vista.getBtnAsignarAprendices())) {
			// Abrimos el JDialog que creamos antes
			VentanaAsignarAprendices vAsignar = new VentanaAsignarAprendices(vista);

			// Rellenamos con los nombres de la BD
			vAsignar.rellenarAprendices(m.recuperarNombresAprendices());

			// Configuramos los botones del JDialog
			vAsignar.setControlador(new ActionListener() {
				public void actionPerformed(ActionEvent e2) {
					// Si se pulsa "Aceptar" en la ventanita
					if (e2.getSource().equals(vAsignar.getBtnAceptar())) {
						if (vAsignar.validarSeleccion()) {
							// Rescatamos los nombres elegidos
							String n1 = vAsignar.getAprendiz1();
							String n2 = vAsignar.getAprendiz2();

							// Convertimos nombres a IDs y guardamos en las variables de clase
							if (n1.equals("-- Ninguno --")) {
								idAprendiz1Seleccionado = 0;
							} else {
								idAprendiz1Seleccionado = m.obtenerIdEmpleadoPorNombre(n1);
							}	
							
							if (n2.equals("-- Ninguno --")) {
								idAprendiz2Seleccionado = 0;
							} else {
								idAprendiz2Seleccionado = m.obtenerIdEmpleadoPorNombre(n2);
							}
							// Avisamos al usuario y cerramos la ventanita
							JOptionPane.showMessageDialog(vAsignar, "Aprendices seleccionados.");
							vAsignar.dispose();
						}
						// Si se pulsa "Cancelar" en la ventanita, simplemente se cierra
					} else if (e2.getSource().equals(vAsignar.getBtnCancelar())) {
						vAsignar.dispose();
					}
				}
			});
			// Mostramos la ventanita
			vAsignar.setVisible(true);
			
		// --- 2. BOTÓN GUARDAR CAMBIOS ---
		} else if (e.getSource().equals(vista.getBtnGuardarCambios())) {
			try {
				// Creamos un objeto Cita nuevo para agrupar todos los datos modificados
				Cita cita = new Cita();

				// Datos básicos desde los Spinners y el TXT oculto
				cita.setId_cita(Integer.parseInt(vista.getTxtIdCita().getText()));
				cita.setDia(new java.sql.Date(((java.util.Date) vista.getSpinnerFecha().getValue()).getTime()));

				// Extraemos la hora y la convertimos al formato que entiende la BBDD
				java.util.Date dHora = (java.util.Date) vista.getSpinnerHora().getValue();
				cita.setHora(new java.sql.Time(dHora.getTime()));

				cita.setDuracion((int) vista.getSpinnerDuracion().getValue());

				// Obtención de IDs desde los ComboBox 
				int idClienteCmb = m
						.obtenerIdClientePorNombre(vista.getCmbNombreCliente().getSelectedItem().toString());
				int idTallerCmb = m.obtenerIdTallerPorNombre(vista.getCmbNombreTaller().getSelectedItem().toString());
				int idResponsableCmb = m
						.obtenerIdEmpleadoPorNombre(vista.getCmbNombreResponsable().getSelectedItem().toString());
				int idTrajeCmb = m.obtenerIdTrajePorNombre(vista.getCmbNombreTraje().getSelectedItem().toString());

				// Asignamos esos IDs a nuestra cita
				cita.setId_cliente(idClienteCmb);
				cita.setId_taller(idTallerCmb);
				cita.setId_empleado(idResponsableCmb);
				cita.setId_traje(idTrajeCmb);

				// Guardamos los nombres de los aprendices seleccionados
				cita.setId_aprendiz1(idAprendiz1Seleccionado);
				cita.setId_aprendiz2(idAprendiz2Seleccionado);

				// Ejecutamos la actualización en la base de datos
				if (m.modificarCita(cita)) {
					// Si todo va bien, avisamos
					JOptionPane.showMessageDialog(vista, "Cita actualizada correctamente");
					
					// Preparamos el regreso a la pantalla principal de citas
					VentanaGestionCita vG = new VentanaGestionCita(rangoUsuario, idUsuario);
					vG.setControlador(new ControladorMenuCita(vG, rangoUsuario, idUsuario));

					// Recargamos los datos de las tablas
					if (rangoUsuario.equalsIgnoreCase("Maestro") || rangoUsuario.equalsIgnoreCase("Oficial")) {
						vG.cargarDatosCitas(m.recuperarCitas());
					} else {
						vG.cargarDatosCitas(m.recuperarCitasPropias(idUsuario));
					}

					// Mostramos la lista de citas y cerramos la ventana de modificar
					vG.setVisible(true);
					vista.dispose();
				}

			} catch (Exception ex) {
				// Si falla algo (ej. algún dato vacío o incorrecto), lanzamos un aviso
				JOptionPane.showMessageDialog(vista, "Error al procesar los datos: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		// --- 3. BOTÓN ATRÁS ---
		else if (e.getSource().equals(vista.getBtnAtras())) {
			// Cancela la modificación, vuelve a crear la ventana de gestión de citas y recarga la tabla
			VentanaGestionCita vG = new VentanaGestionCita(rangoUsuario, idUsuario);
			vG.setControlador(new ControladorMenuCita(vG, rangoUsuario, idUsuario));

			// Filtra qué citas debe ver el usuario al volver
			if (rangoUsuario.equalsIgnoreCase("Maestro") || rangoUsuario.equalsIgnoreCase("Oficial")) {
				vG.cargarDatosCitas(m.recuperarCitas());
			} else {
				vG.cargarDatosCitas(m.recuperarCitasPropias(idUsuario));
			}

			// Muestra la ventana principal y cierra esta
			vG.setVisible(true);
			vista.dispose();
		}
		// --- 4. CERRAR SESIÓN ---
		else if (e.getSource().equals(vista.getBtnCerrarSesion())) {
			// Devuelve al usuario a la pantalla de Login para cambiar de cuenta
			VentanaLogin vL = new VentanaLogin(rangoUsuario);
			vL.setControlador(new ControladorLogin(vL));
			vL.setVisible(true);
			vista.dispose();
		}
	}
}