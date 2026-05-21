package controlador.cita;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Cita;
import modelo.Modelo;
import vista.VentanaCrearCita;
import vista.VentanaAsignarAprendices;
import vista.VentanaLogin;
import vista.VentanaGestionCita;
import controlador.ControladorLogin;

//Controlador encargado de gestionar la pantalla donde se crean nuevas citas
public class ControladorCrearCita implements ActionListener {
	// Variables para la vista, datos del usuario activo, el modelo (BBDD) y los aprendices que se van a asignar
	private VentanaCrearCita vista;
	private String rangoUsuario;
	private int idUsuario;
	private Modelo modelo;
	// Estas variables guardarán los IDs de los aprendices seleccionados (0 significa ninguno)
	private int idAp1Seleccionado = 0;
	private int idAp2Seleccionado = 0;

	// Constructor: se ejecuta al abrir la ventana de crear cita
	public ControladorCrearCita(VentanaCrearCita vista, String rango, int id) {
		this.vista = vista;
		this.rangoUsuario = rango;
		this.idUsuario = id;
		this.modelo = new Modelo();
		
		// Carga automáticamente las listas desplegables (ComboBox) con los datos de la base de datos
		vista.rellenarComboBox(modelo.recuperarNombresClientes(), modelo.recuperarNombresTalleres(),
				modelo.recuperarNombresEmpleados(), modelo.recuperarNombresTrajes());
	}

	// Método que detecta qué botón se ha pulsado en la ventana
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(vista.getNombreCliente())) {
	        if (vista.getNombreCliente().getSelectedItem() != null && 
	            vista.getNombreCliente().getSelectedItem().toString().equals("- Crear nuevo cliente -")) {
	            
	            vista.dispose();
	            // Abrimos la ventana de cliente indicando que venimos de "Crear Cita"
	            vista.VentanaCrearCliente vCli = new vista.VentanaCrearCliente(rangoUsuario, idUsuario, "Crear Cita");
	            controlador.cliente.ControladorCrearCliente cCli = new controlador.cliente.ControladorCrearCliente(vCli, idUsuario);
	            vCli.setControlador(cCli);
	            vCli.setVisible(true);
	            return; 
	        }
	    }

	    if (e.getSource().equals(vista.getNombreTraje())) {
	        if (vista.getNombreTraje().getSelectedItem() != null && 
	            vista.getNombreTraje().getSelectedItem().toString().equals("- Crear nuevo traje -")) {
	            
	            vista.dispose();
	            // Abrimos la ventana de traje indicando el origen
	            vista.VentanaCrearTraje vTra = new vista.VentanaCrearTraje(rangoUsuario, "Crear Cita", idUsuario);
	            controlador.traje.ControladorCrearTraje cTra = new controlador.traje.ControladorCrearTraje(vTra, rangoUsuario, idUsuario);
	            vTra.setControladorGuardar(cTra);
	            vTra.setVisible(true);
	            return;
	        }
	    }
		// 1. Si se pulsa el botón "Asignar Aprendices"
		if (e.getSource().equals(vista.getBtnAsignarAprendices())) {
			// Abre una pequeña ventana secundaria para elegir a los aprendices
			VentanaAsignarAprendices vAsignar = new VentanaAsignarAprendices(vista);
			vAsignar.rellenarAprendices(modelo.recuperarNombresAprendices());
			
			// Le creamos un controlador "interno" a esta pequeña ventana para sus botones
			vAsignar.setControlador(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e2) {
					// Si en la ventanita pulsan "Aceptar"
					if (e2.getSource().equals(vAsignar.getBtnAceptar())) {
						if (vAsignar.validarSeleccion()) {
							// Obtenemos los nombres seleccionados en los desplegables
							String n1 = vAsignar.getAprendiz1();
							String n2 = vAsignar.getAprendiz2();
							
							// Convertimos el nombre del primer aprendiz a su ID numérico
							if (n1.equals("-- Ninguno --")) {
								idAp1Seleccionado = 0;
							} else {
								idAp1Seleccionado = modelo.obtenerIdEmpleadoPorNombre(n1);
							}
							
							// Convertimos el nombre del segundo aprendiz a su ID numérico
							if (n2.equals("-- Ninguno --")) {
								idAp2Seleccionado = 0;
							} else {
								idAp2Seleccionado = modelo.obtenerIdEmpleadoPorNombre(n2);
							}
							
							// Avisamos de que se guardaron y cerramos la ventanita
							JOptionPane.showMessageDialog(vAsignar, "Aprendices asignados.");
							vAsignar.dispose();
						}
					// Si en la ventanita pulsan "Cancelar", simplemente la cerramos
					} else if (e2.getSource().equals(vAsignar.getBtnCancelar())) {
						vAsignar.dispose();
					}
				}
			});
			// Hacemos visible la ventana de asignación
			vAsignar.setVisible(true);
		
		// 2. Si se pulsa el botón "Guardar Cambios" (crear la cita definitivamente)
		} else if (e.getSource().equals(vista.getBtnGuardarCambios())) {
			try {
				// Creamos un nuevo objeto Cita y lo rellenamos con lo escrito en la pantalla
				Cita nuevaCita = new Cita();
				nuevaCita.setId_cita(0);
				nuevaCita.setDia(vista.getFechaCita());
				nuevaCita.setHora(vista.getHoraCita());
				nuevaCita.setDuracion(vista.getDuracion());
				
				// Traducimos los textos seleccionados en los desplegables a sus IDs correspondientes en la BBDD
				int idCli = modelo.obtenerIdClientePorNombre(vista.getNombreCliente().getSelectedItem().toString());
				int idTal = modelo.obtenerIdTallerPorNombre(vista.getNombreTaller().getSelectedItem().toString());
				int idRes = modelo.obtenerIdEmpleadoPorNombre(vista.getNombreResponsable().getSelectedItem().toString());
				int idTra = modelo.obtenerIdTrajePorNombre(vista.getNombreTraje().getSelectedItem().toString());
				
				// Asignamos esos IDs a nuestra nueva cita
				nuevaCita.setId_cliente(idCli);
				nuevaCita.setId_taller(idTal);
				nuevaCita.setId_empleado(idRes);
				nuevaCita.setId_traje(idTra);
				
				// Asignamos los IDs de los aprendices que guardamos previamente en el otro botón
				nuevaCita.setId_aprendiz1(idAp1Seleccionado);
				nuevaCita.setId_aprendiz2(idAp2Seleccionado);

				// Intentamos guardar la cita en la base de datos
				if (modelo.crearCita(nuevaCita)) {
				    JOptionPane.showMessageDialog(vista, "Cita guardada correctamente.");
				    
				    // Si se guardó bien, volvemos a la pantalla del listado de citas
				    VentanaGestionCita vGestionCita = new VentanaGestionCita(rangoUsuario, idUsuario);
				    vGestionCita.setControlador(new ControladorMenuCita(vGestionCita, rangoUsuario, idUsuario));
				    
				    // Recargamos la tabla de citas según quién esté usando el programa
				    ArrayList<Cita> listaCitas;
				    if (rangoUsuario.equals("Aprendiz")) {
				        listaCitas = modelo.recuperarCitasPropias(idUsuario);
				    } else {
				        listaCitas = modelo.recuperarCitas();
				    }
				    vGestionCita.cargarDatosCitas(listaCitas); 
				    vGestionCita.setVisible(true); // Mostramos el listado
				    vista.dispose(); // Cerramos la pantalla de "Crear Cita"
				}
			} catch (Exception ex) {
				// Si algo falla (ej. formato de fecha incorrecto), mostramos el error
				JOptionPane.showMessageDialog(vista, "Error al guardar: " + ex.getMessage());
			}
		// 3. Si se pulsa el botón "Atrás" (cancelar creación)
		} else if (e.getSource().equals(vista.getBtnAtras())) {
			ArrayList<Cita> listaCitas;
			// Recuperamos las citas según el cargo del usuario
			if (rangoUsuario.equals("Aprendiz")) {
			    listaCitas = modelo.recuperarCitasPropias(idUsuario);
			} else {
			    listaCitas = modelo.recuperarCitas();
			}
			// Volvemos a la pantalla principal de gestión de citas sin guardar nada
			VentanaGestionCita vG = new VentanaGestionCita(rangoUsuario, idUsuario);
			vG.setControlador(new ControladorMenuCita(vG, rangoUsuario, idUsuario));
			vG.cargarDatosCitas(listaCitas); // Aquí pasamos la variable que ya hemos rellenado arriba
			vG.setVisible(true);
			vista.dispose();
		// 4. Si se pulsa el botón "Cerrar Sesión"
		} else if (e.getSource().equals(vista.getBtnCerrarSesion())) {
			// Volvemos a la pantalla principal de login y cerramos esta
			VentanaLogin vL = new VentanaLogin(rangoUsuario);
			vL.setControlador(new ControladorLogin(vL));
			vL.setVisible(true);
			vista.dispose();
		}
	}
}