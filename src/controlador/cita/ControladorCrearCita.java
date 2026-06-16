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

	// Método que detecta qué botón o selección se ha pulsado en la ventana
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// 1. DETECTAR EL CAMBIO DE CLIENTE y FILTRAR LOS TRAJES
			if (e.getSource().equals(vista.getNombreCliente())) {
				if (vista.getNombreCliente().getSelectedItem() != null) {
					String clienteSeleccionado = vista.getNombreCliente().getSelectedItem().toString();
					
					// Si elige crear uno nuevo, vamos a su ventana
					if (clienteSeleccionado.equals("- Crear nuevo cliente -")) {
						vista.dispose();
						vista.VentanaCrearCliente vCli = new vista.VentanaCrearCliente(rangoUsuario, idUsuario, "Crear Cita");
						controlador.cliente.ControladorCrearCliente cCli = new controlador.cliente.ControladorCrearCliente(vCli, idUsuario);
						vCli.setControlador(cCli);
						vCli.setVisible(true);
						return; 
					} else {
						// TRUCO CLAVE: Quitamos temporalmente el controlador del combo de trajes
						// para que al borrar y añadir elementos NO se dispare la ventana de crear traje.
						vista.getNombreTraje().removeActionListener(this);
						
						// Vaciamos el combo de trajes viejos
						vista.getNombreTraje().removeAllItems();
						
						// Volvemos a meter la opción por defecto si es Maestro
						if (rangoUsuario.equals("Maestro")) {
							vista.getNombreTraje().addItem("- Crear nuevo traje -");
						}
						
						// Buscamos en la base de datos solo los trajes de este cliente
						ArrayList<String> trajesFiltrados = modelo.recuperarNombresTrajesPorCliente(clienteSeleccionado);
						
						// Los metemos en el combo
						for (String traje : trajesFiltrados) {
							vista.getNombreTraje().addItem(traje);
						}
						
						// Una vez que el combo está perfectamente lleno y en paz, 
						// le volvemos a activar el controlador para que vuelva a funcionar normal
						vista.getNombreTraje().addActionListener(this);
					}
				}
			}

			// 2. DETECTAR SI SE ELIGE CREAR UN NUEVO TRAJE (Solo si hace clic de verdad)
			if (e.getSource().equals(vista.getNombreTraje())) {
				if (vista.getNombreTraje().getSelectedItem() != null && 
					vista.getNombreTraje().getSelectedItem().toString().equals("- Crear nuevo traje -")) {
					
					vista.dispose();
					vista.VentanaCrearTraje vTra = new vista.VentanaCrearTraje(rangoUsuario, "Crear Cita", idUsuario);
					controlador.traje.ControladorCrearTraje cTra = new controlador.traje.ControladorCrearTraje(vTra, rangoUsuario, idUsuario);
					vTra.setControladorGuardar(cTra);
					vTra.setVisible(true);
					return;
				}
			}
			
			// 3. BOTÓN ASIGNAR APRENDICES
			if (e.getSource().equals(vista.getBtnAsignarAprendices())) {
				VentanaAsignarAprendices vAsignar = new VentanaAsignarAprendices(vista);
				vAsignar.rellenarAprendices(modelo.recuperarNombresAprendices());
				
				vAsignar.setControlador(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e2) {
						if (e2.getSource().equals(vAsignar.getBtnAceptar())) {
							if (vAsignar.validarSeleccion()) {
								String n1 = vAsignar.getAprendiz1();
								String n2 = vAsignar.getAprendiz2();
								
								if (n1.equals("-- Ninguno --")) {
									idAp1Seleccionado = 0;
								} else {
									idAp1Seleccionado = modelo.obtenerIdEmpleadoPorNombre(n1);
								}
								
								if (n2.equals("-- Ninguno --")) {
									idAp2Seleccionado = 0;
								} else {
									idAp2Seleccionado = modelo.obtenerIdEmpleadoPorNombre(n2);
								}
								
								JOptionPane.showMessageDialog(vAsignar, "Aprendices asignados.");
								vAsignar.dispose();
							}
						} else if (e2.getSource().equals(vAsignar.getBtnCancelar())) {
							vAsignar.dispose();
						}
					}
				});
				vAsignar.setVisible(true);
			
			// 4. BOTÓN GUARDAR CAMBIOS
			} else if (e.getSource().equals(vista.getBtnGuardarCambios())) {
				try {
					Cita nuevaCita = new Cita();
					nuevaCita.setId_cita(0);
					nuevaCita.setDia(vista.getFechaCita());
					nuevaCita.setHora(vista.getHoraCita());
					nuevaCita.setDuracion(vista.getDuracion());
					
					int idCli = modelo.obtenerIdClientePorNombre(vista.getNombreCliente().getSelectedItem().toString());
					int idTal = modelo.obtenerIdTallerPorNombre(vista.getNombreTaller().getSelectedItem().toString());
					int idRes = modelo.obtenerIdEmpleadoPorNombre(vista.getNombreResponsable().getSelectedItem().toString());
					int idTra = modelo.obtenerIdTrajePorNombre(vista.getNombreTraje().getSelectedItem().toString());
					
					nuevaCita.setId_cliente(idCli);
					nuevaCita.setId_taller(idTal);
					nuevaCita.setId_empleado(idRes);
					nuevaCita.setId_traje(idTra);
					
					nuevaCita.setId_aprendiz1(idAp1Seleccionado);
					nuevaCita.setId_aprendiz2(idAp2Seleccionado);
					
					// VALIDACIÓN: Evitar duplicados en el taller
					if (modelo.existeSolapamientoTaller(idTal, vista.getFechaCita(), vista.getHoraCita(), vista.getDuracion())) {
					    JOptionPane.showMessageDialog(vista, "El taller seleccionado ya está ocupado en ese rango de horario.", "Taller no disponible", JOptionPane.ERROR_MESSAGE);
					    return; // Detiene el flujo para que no registre la cita
					}

					if (modelo.crearCita(nuevaCita)) {
						JOptionPane.showMessageDialog(vista, "Cita guardada correctamente.");
						VentanaGestionCita vGestionCita = new VentanaGestionCita(rangoUsuario, idUsuario);
						vGestionCita.setControlador(new ControladorMenuCita(vGestionCita, rangoUsuario, idUsuario));
						
						ArrayList<Cita> listaCitas;
						if (rangoUsuario.equals("Aprendiz")) {
							listaCitas = modelo.recuperarCitasPropias(idUsuario);
						} else {
							listaCitas = modelo.recuperarCitas();
						}
						vGestionCita.cargarDatosCitas(listaCitas); 
						vGestionCita.setVisible(true);
						vista.dispose();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(vista, "Error al guardar: " + ex.getMessage());
				}
				
			// 5. BOTÓN ATRÁS
			} else if (e.getSource().equals(vista.getBtnAtras())) {
				ArrayList<Cita> listaCitas;
				if (rangoUsuario.equals("Aprendiz")) {
					listaCitas = modelo.recuperarCitasPropias(idUsuario);
				} else {
					listaCitas = modelo.recuperarCitas();
				}
				VentanaGestionCita vG = new VentanaGestionCita(rangoUsuario, idUsuario);
				vG.setControlador(new ControladorMenuCita(vG, rangoUsuario, idUsuario));
				vG.cargarDatosCitas(listaCitas);
				vG.setVisible(true);
				vista.dispose();
				
			// 6. BOTÓN CERRAR SESIÓN
			} else if (e.getSource().equals(vista.getBtnCerrarSesion())) {
				VentanaLogin vL = new VentanaLogin(rangoUsuario);
				vL.setControlador(new ControladorLogin(vL));
				vL.setVisible(true);
				vista.dispose();
			}
		}
}