package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Modelo {
	// Configuración de la conexión a la base de datos
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/Proyecto-Integrador";
	private static final String usuario = "root";
	private static final String contrasena = "root";

	/**
	 * Método para conectar la base de datos * @return
	 */
	public Connection getConexion() {
		Connection conexion = null;
		// Controlamos excepciones a la hora de conectar la base de datos
		try {
			// cargamos el driver
			Class.forName(driver);

			// obtenemos conexión
			conexion = DriverManager.getConnection(url, usuario, contrasena);
			System.out.println("Conexión establecida");
		} catch (Exception e) {
			System.out.println("Error al conectar con la BBDD:");
			e.printStackTrace();
		}

		return conexion;
	}

	/**
	 * Metodo para cerrar la conexion de la base de datos con el proyecto * @param c
	 */
	public void cerrarConexion(Connection c) {
		// Control de excepciones para cerrar la conexion
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método para obtener la categoria del usuario que haya iniciado sesion
	 * 
	 * @param id
	 * @param contrasena
	 * @return
	 */
	public String obtenerCategoria(int id, String contrasena) {
		String categoria = null;
		String query = "SELECT categoria FROM EMPLEADO WHERE id_empleado = ? AND contrasena = ?";
		Connection conexion = getConexion();

		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			ps.setString(2, contrasena);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				// Extrae el valor de la columna 'categoria'
				categoria = rs.getString("categoria");
			}
		} catch (SQLException sqlex) {
			System.err.println("Error en ObtenerCategoriaEmpleado: " + sqlex.getMessage());
		} finally {
			cerrarConexion(conexion);
		}
		return categoria;
	}

	/**
	 * Metodo para Crear la tabla de citas
	 * 
	 * @return
	 */
	public ArrayList<Cita> recuperarCitas() {
		ArrayList<Cita> citas = new ArrayList<Cita>();
		Connection conexion = getConexion();
		// Consulta larga para traer nombres en lugar de solo IDs
		String query = "SELECT c.*, cl.nombre AS nombre_cliente, ta.nombre_sala AS nombre_taller, "
				+ "e.nombre AS nombre_empleado, tr.nombre AS nombre_traje, "
				+ "a1.nombre AS nombre_ap1, a2.nombre AS nombre_ap2 " + "FROM CITA c "
				+ "JOIN CLIENTE cl ON c.id_cliente = cl.id_cliente " + "JOIN TALLER ta ON c.id_taller = ta.id_taller "
				+ "JOIN TRAJE tr ON c.id_traje = tr.id_traje "
				+ "JOIN EMPLEADO e ON c.id_empleado_responsable = e.id_empleado "
				+ "LEFT JOIN EMPLEADO a1 ON c.id_aprendiz1 = a1.id_empleado "
				+ "LEFT JOIN EMPLEADO a2 ON c.id_aprendiz2 = a2.id_empleado " + "ORDER BY c.dia";
		try {
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery(query);

			// Añadimos los datos
			while (rs.next()) {
				Cita c = new Cita();
				c.setId_cita(rs.getInt("id_cita"));
				c.setDia(rs.getDate("dia"));
				c.setHora(rs.getTime("hora"));
				c.setDuracion(rs.getInt("duracion"));
				c.setId_cliente(rs.getInt("id_cliente"));
				c.setId_taller(rs.getInt("id_taller"));
				c.setId_empleado(rs.getInt("id_empleado_responsable"));
				c.setId_traje(rs.getInt("id_traje"));

				// Los id no se mostraran pero son necesarios
				c.setId_aprendiz1(rs.getInt("id_aprendiz1"));
				c.setId_aprendiz2(rs.getInt("id_aprendiz2"));
				c.setNombreAprendiz1(rs.getString("nombre_ap1"));
				c.setNombreAprendiz2(rs.getString("nombre_ap2"));

				// En lugar de los id se mostraran los nombres
				c.setNombreCliente(rs.getString("nombre_cliente"));
				c.setNombreTaller(rs.getString("nombre_taller"));
				c.setNombreEmpleadoResponsable(rs.getString("nombre_empleado"));
				c.setNombreTraje(rs.getString("nombre_traje"));

				citas.add(c);
			}
		} catch (SQLException e) {
			// JOptionPane.showMessageDialog(null, "Error de SQL: " + e.getMessage());ç
			System.err.println("Error de SQL: " + e.getMessage());
			// Si o si cerramos la conexion, haya errores o no.
		} finally {
			cerrarConexion(conexion);
		}

		return citas;
	}

	/**
	 * Metodo para crear la tabla de citas propias del aprendiz
	 * 
	 * @param idEmpleado
	 * @return
	 */
	public ArrayList<Cita> recuperarCitasPropias(int idEmpleado) {
		ArrayList<Cita> citasPropias = new ArrayList<>();
		Connection conexion = getConexion();

		// Query mejorada: Traemos nombres de todos los implicados y filtramos por los 3 campos de empleado
		String query = "SELECT c.*, cl.nombre AS nombre_cliente, ta.nombre_sala AS nombre_taller, "
				+ "e.nombre AS nombre_empleado, tr.nombre AS nombre_traje, "
				+ "a1.nombre AS nombre_ap1, a2.nombre AS nombre_ap2 " 
				+ "FROM CITA c "
				+ "JOIN CLIENTE cl ON c.id_cliente = cl.id_cliente " 
				+ "JOIN TALLER ta ON c.id_taller = ta.id_taller "
				+ "JOIN TRAJE tr ON c.id_traje = tr.id_traje "
				+ "JOIN EMPLEADO e ON c.id_empleado_responsable = e.id_empleado "
				+ "LEFT JOIN EMPLEADO a1 ON c.id_aprendiz1 = a1.id_empleado "
				+ "LEFT JOIN EMPLEADO a2 ON c.id_aprendiz2 = a2.id_empleado "
				+ "WHERE c.id_empleado_responsable = ? OR c.id_aprendiz1 = ? OR c.id_aprendiz2 = ? "
				+ "ORDER BY c.dia";

		try {
			PreparedStatement pst = conexion.prepareStatement(query);
			pst.setInt(1, idEmpleado);
			pst.setInt(2, idEmpleado);
			pst.setInt(3, idEmpleado);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Cita c = new Cita();

				c.setId_cita(rs.getInt("id_cita"));
				c.setDia(rs.getDate("dia"));
				c.setHora(rs.getTime("hora"));
				c.setDuracion(rs.getInt("duracion"));

				c.setNombreCliente(rs.getString("nombre_cliente"));
				c.setNombreTaller(rs.getString("nombre_taller"));
				c.setNombreEmpleadoResponsable(rs.getString("nombre_empleado"));
				c.setNombreTraje(rs.getString("nombre_traje"));
				c.setNombreAprendiz1(rs.getString("nombre_ap1"));
				c.setNombreAprendiz2(rs.getString("nombre_ap2"));

				// Los id no se ven pero son necesarios para la gestion
				c.setId_empleado(rs.getInt("id_empleado_responsable"));
				c.setId_cliente(rs.getInt("id_cliente"));
				c.setId_aprendiz1(rs.getInt("id_aprendiz1"));
				c.setId_aprendiz2(rs.getInt("id_aprendiz2"));

				citasPropias.add(c);
			}
		} catch (SQLException sqlex) {
			System.err.println("Error en citas propias: " + sqlex.getMessage());
		} finally {
			cerrarConexion(conexion);
		}
		return citasPropias;
	}

	/**
	 * Metodo para crear la tabla de talleres
	 * 
	 * @return
	 */
	public ArrayList<Taller> recuperarTalleres() {
		ArrayList<Taller> talleres = new ArrayList<Taller>();

		Connection conexion = getConexion();

		String query = "SELECT id_taller, nombre_sala, tipo_sala FROM TALLER";

		try {
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery(query);

			// Añadimos los datos
			while (rs.next()) {
				Taller t = new Taller();
				t.setId_taller(rs.getInt("id_taller"));
				t.setNombre(rs.getString("nombre_sala"));
				t.setTipo_sala(rs.getString("tipo_sala"));
				talleres.add(t);

			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error de SQL: " + e.getMessage());
			// Si o si cerramos la conexion, haya errores o no.
		} finally {
			cerrarConexion(conexion);
		}

		return talleres;
	}

	/**
	 * Método para crear la tabla de clientes
	 * 
	 * @return
	 */
	public ArrayList<Cliente> recuperarClientes() {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		Connection conexion = getConexion();
		String query = "SELECT id_cliente, nombre, superpoder, colores FROM CLIENTE";

		try {
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Cliente c = new Cliente();
				// Agregado el ID para que los controladores puedan identificar al cliente
				c.setId(rs.getInt("id_cliente"));
				c.setNombre(rs.getString("nombre"));
				c.setSuperpoder(rs.getString("superpoder"));
				c.setColores(rs.getString("colores"));
				clientes.add(c);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error de SQL al recuperar clientes: " + e.getMessage());
		} finally {
			cerrarConexion(conexion);
		}
		return clientes;
	}

	/**
	 * Método para crear la tabla trajes
	 * 
	 * @return
	 */
	public ArrayList<Traje> recuperarTrajes() {
    ArrayList<Traje> trajes = new ArrayList<Traje>();
    Connection conexion = getConexion();
    
    String query = "SELECT t.id_traje, t.nombre, t.estado, c.nombre AS nom_cliente " +
                   "FROM TRAJE t " +
                   "LEFT JOIN CLIENTE c ON t.id_cliente = c.id_cliente";
    try {
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            Traje t = new Traje();
            t.setId_traje(rs.getInt("id_traje"));
            t.setNombre(rs.getString("nombre"));
            t.setEstado(rs.getString("estado"));
            t.setNombreCliente(rs.getString("nom_cliente")); 
            trajes.add(t);
        }
    } catch (SQLException e) {
        System.err.println("Error al recuperar trajes: " + e.getMessage());
    } finally {
        cerrarConexion(conexion);
    }
    return trajes;
}

	/**
	 * Método para rellenar el combobox de trajes en la pagina crearCitas
	 * 
	 * @return
	 */
	public ArrayList<String> recuperarNombresTrajes() {
		ArrayList<String> lista = new ArrayList<>();
		String query = "SELECT nombre FROM TRAJE";
		try {
			Connection con = getConexion();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				lista.add(rs.getString("nombre"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Método para rellenar el combobox de empleados en la pagina crearCitas
	 * 
	 * @return
	 */
	public ArrayList<String> recuperarNombresEmpleados() {
		ArrayList<String> lista = new ArrayList<>();
		String query = "SELECT nombre FROM EMPLEADO";
		try {
			Connection con = getConexion();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				lista.add(rs.getString("nombre"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Método para rellenar el combobox de talleres en la pagina crearCitas
	 * 
	 * @return
	 */
	public ArrayList<String> recuperarNombresTalleres() {
		ArrayList<String> lista = new ArrayList<>();
		String sql = "SELECT nombre_sala FROM TALLER";
		try (Connection con = getConexion();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {

			while (rs.next()) {
				lista.add(rs.getString("nombre_sala"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Método para rellenar el combobox de clientes en la pagina crearCitas
	 * 
	 * @return
	 */
	public ArrayList<String> recuperarNombresClientes() {
		ArrayList<String> lista = new ArrayList<>();
		String sql = "SELECT nombre FROM CLIENTE";

		try (Connection con = getConexion();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {

			while (rs.next()) {
				lista.add(rs.getString("nombre"));
			}

		} catch (SQLException e) {
			System.err.println("Error al recuperar nombres de clientes: " + e.getMessage());
			e.printStackTrace();
		}

		return lista;
	}

	public boolean crearCliente(Cliente cliente) {
		// No incluimos el id_cliente porque es autoincremental en la BBDD
		String query = "INSERT INTO CLIENTE (nombre, superpoder, colores) VALUES (?, ?, ?)";
		Connection conexion = getConexion();

		try {
			PreparedStatement pst = conexion.prepareStatement(query);
			pst.setString(1, cliente.getNombre());
			pst.setString(2, cliente.getSuperpoder());
			pst.setString(3, cliente.getColores());

			int filasAfectadas = pst.executeUpdate();
			return filasAfectadas > 0;

		} catch (SQLException sqlex) {
			System.err.println("Error al crear el cliente: " + sqlex.getMessage());
			return false;
		} finally {
			cerrarConexion(conexion);
		}
	}

	/**
	 * Método para eliminar el cliente
	 * 
	 * @param idCliente
	 * @return
	 */
	public boolean eliminarCliente(int idCliente) {
    String query = "DELETE FROM CLIENTE WHERE id_cliente = ?";
    Connection conexion = getConexion();
    try {
        PreparedStatement pst = conexion.prepareStatement(query);
        pst.setInt(1, idCliente);
        int resultado = pst.executeUpdate();
        return resultado > 0;
    } catch (SQLException sqlex) {
        JOptionPane.showMessageDialog(null, "No se puede eliminar el cliente porque tiene registros asociados: " + sqlex.getErrorCode());
        System.err.println("Código de error SQL: " + sqlex.getErrorCode());
        return false;
    } finally {
        cerrarConexion(conexion);
    }
}

	/**
	 * Método para modificar el cliente
	 * 
	 * @param cliente
	 * @return
	 */
	public boolean modificarCliente(Cliente cliente) {
		String query = "UPDATE CLIENTE SET nombre = ?, superpoder = ?, colores = ? WHERE id_cliente = ?";
		Connection conexion = getConexion();
		try {
			PreparedStatement pst = conexion.prepareStatement(query);
			pst.setString(1, cliente.getNombre());
			pst.setString(2, cliente.getSuperpoder());
			pst.setString(3, cliente.getColores());
			pst.setInt(4, cliente.getId());
			int filasAfectadas = pst.executeUpdate();
			return filasAfectadas > 0;
		} catch (SQLException sqlex) {
			System.err.println("Error al modificar el cliente: " + sqlex.getMessage());
			return false;
		} finally {
			cerrarConexion(conexion);
		}
	}

	/**
	 * Método para crear un taller
	 * 
	 * @param taller
	 * @return
	 */
	public boolean crearTaller(Taller taller) {
		String query = "INSERT INTO TALLER (id_taller, nombre_sala, tipo_sala) VALUES (?, ?, ?)";
		Connection conexion = getConexion();

		try {
			PreparedStatement pst = conexion.prepareStatement(query);
			pst.setInt(1, taller.getId_taller());
			pst.setString(2, taller.getNombre());
			pst.setString(3, taller.getTipo_sala());

			int filasAfectadas = pst.executeUpdate();
			// Si hay cambios devolvera true
			return filasAfectadas > 0;

		} catch (SQLException sqlex) {
			System.err.println("Error al crear el taller: " + sqlex.getMessage());
			return false;
		} finally {
			cerrarConexion(conexion);
		}

	}

	/**
	 * Método para eliminar un taller
	 * 
	 * @param idTaller
	 * @return
	 */
	public boolean eliminarTaller(int idTaller) {
		String query = "DELETE FROM TALLER WHERE id_taller = ?";
		Connection conexion = getConexion();

		try {
			PreparedStatement pst = conexion.prepareStatement(query);
			pst.setInt(1, idTaller);

			int resultado = pst.executeUpdate();
			// Si hay algun cambio devuelve true
			return resultado > 0;
		} catch (SQLException sqlex) {
			System.err.println("Error al eliminar taller: " + sqlex.getMessage());
			return false;
		} finally {
			cerrarConexion(conexion);
		}
	}

	/**
	 * Método para modificar un taller
	 * 
	 * @param taller
	 * @return
	 */
	public boolean modificarTaller(Taller taller) {
		String query = "UPDATE TALLER SET nombre_sala = ?, tipo_sala = ? WHERE id_taller = ?";
		Connection conexion = getConexion();

		try {
			PreparedStatement pst = conexion.prepareStatement(query);

			pst.setString(1, taller.getNombre());
			pst.setString(2, taller.getTipo_sala());
			pst.setInt(3, taller.getId_taller());

			int filasAfectadas = pst.executeUpdate();
			return filasAfectadas > 0;

		} catch (SQLException sqlex) {
			System.err.println("Error al modificar el taller: " + sqlex.getMessage());
		} finally {
			cerrarConexion(conexion);
		}

		return false;
	}

	/**
	 * Método para crear una cita
	 * 
	 * @param cita
	 * @return
	 */
	public boolean crearCita(Cita cita) {
		String query = "INSERT INTO CITA (dia, hora, duracion, id_cliente, id_taller, id_empleado_responsable, id_traje, id_aprendiz1, id_aprendiz2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conexion = getConexion();
		try {
			PreparedStatement pst = conexion.prepareStatement(query);
			pst.setDate(1, new java.sql.Date(cita.getDia().getTime()));
			pst.setTime(2, cita.getHora());
			pst.setInt(3, cita.getDuracion());
			pst.setInt(4, cita.getId_cliente());
			pst.setInt(5, cita.getId_taller());
			pst.setInt(6, cita.getId_empleado());
			pst.setInt(7, cita.getId_traje());

			// Manejo de informacion en id_aprendiz1
			if (cita.getId_aprendiz1() > 0) {
				pst.setInt(8, cita.getId_aprendiz1());
			} else {
				pst.setNull(8, java.sql.Types.INTEGER);
			}

			// Manejo de informacion en id_aprendiz2
			if (cita.getId_aprendiz2() > 0) {
				pst.setInt(9, cita.getId_aprendiz2());
			} else {
				pst.setNull(9, java.sql.Types.INTEGER);
			}

			return pst.executeUpdate() > 0;
		} catch (SQLException sqlex) {
			System.err.println("Error al crear la cita: " + sqlex.getMessage());
			return false;
		} finally {
			cerrarConexion(conexion);
		}
	}

	/**
	 * Método para eliminar una cita
	 * 
	 * @param idCita
	 * @return
	 */
	public boolean eliminarCita(int idCita) {
		String query = "DELETE FROM CITA WHERE id_cita = ?";
		Connection conexion = getConexion();

		try {
			PreparedStatement pst = conexion.prepareStatement(query);
			pst.setInt(1, idCita);

			int resultado = pst.executeUpdate();
			// Si hay algun cambio devuelve true
			return resultado > 0;
		} catch (SQLException sqlex) {
			System.err.println("Error al eliminar cita: " + sqlex.getMessage());
			return false;
		} finally {
			cerrarConexion(conexion);
		}
	}

	/**
	 * Método para modificar una cita
	 * 
	 * @param cita
	 * @return
	 */
	public boolean modificarCita(Cita cita) {
		String query = "UPDATE CITA SET dia = ?, hora = ?, duracion = ?, id_cliente = ?, id_taller = ?, id_empleado_responsable = ?, id_traje = ?, id_aprendiz1 = ?, id_aprendiz2 = ? WHERE id_cita = ?";
		Connection conexion = getConexion();

		try {
			PreparedStatement pst = conexion.prepareStatement(query);

			pst.setDate(1, new java.sql.Date(cita.getDia().getTime()));
			pst.setTime(2, cita.getHora());
			pst.setInt(3, cita.getDuracion());
			pst.setInt(4, cita.getId_cliente());
			pst.setInt(5, cita.getId_taller());
			pst.setInt(6, cita.getId_empleado());
			pst.setInt(7, cita.getId_traje());

			// Manejo de informacion del id_aprendiz1 para el UPDATE
			if (cita.getId_aprendiz1() > 0) {
				pst.setInt(8, cita.getId_aprendiz1());
			} else {
				pst.setNull(8, java.sql.Types.INTEGER);
			}

			// Manejo de informacion del id_aprendiz2 para el UPDATE
			if (cita.getId_aprendiz2() > 0) {
				pst.setInt(9, cita.getId_aprendiz2());
			} else {
				pst.setNull(9, java.sql.Types.INTEGER);
			}

			pst.setInt(10, cita.getId_cita());

			int filasAfectadas = pst.executeUpdate();
			return filasAfectadas > 0;

		} catch (SQLException sqlex) {
			System.err.println("Error al modificar la cita: " + sqlex.getMessage());
			return false;
		} finally {
			cerrarConexion(conexion);
		}
	}

	/**
	 * Método para crear un traje
	 * 
	 * @param traje
	 * @return
	 */
	public boolean crearTraje(Traje traje) {
		String query = "INSERT INTO TRAJE (nombre, estado, id_cliente) VALUES (?, ?, ?)";		
		Connection conexion = getConexion();
		
		try {
	        PreparedStatement pst = conexion.prepareStatement(query);
	        pst.setString(1, traje.getNombre());
	        pst.setString(2, traje.getEstado());
	        pst.setInt(3, traje.getId_cliente());
	        return pst.executeUpdate() > 0;
	    } catch (SQLException sqlex) {
	        System.err.println("Error al crear el traje: " + sqlex.getMessage());
	        return false;
	    } finally {
	        cerrarConexion(conexion);
	    }
	}

	/**
	 * Método para eliminar un traje
	 * 
	 * @param idTraje
	 * @return
	 */
	public boolean eliminarTraje(int idTraje) {
		String query = "DELETE FROM TRAJE WHERE id_traje = ?";
		Connection conexion = getConexion();

		try {
			PreparedStatement pst = conexion.prepareStatement(query);
			pst.setInt(1, idTraje);
			return pst.executeUpdate() > 0;
		} catch (SQLException sqlex) {
			System.err.println("Error al eliminar traje: " + sqlex.getMessage());
			return false;
		} finally {
			cerrarConexion(conexion);
		}
	}

	/**
	 * Método para modificar un traje
	 * 
	 * @param traje
	 * @return
	 */
	public boolean modificarTraje(Traje traje) {
		String query = "UPDATE TRAJE SET nombre = ?, estado = ? WHERE id_traje = ?";
		Connection conexion = getConexion();

		try {
			PreparedStatement pst = conexion.prepareStatement(query);
			pst.setString(1, traje.getNombre());
			pst.setString(2, traje.getEstado());
			pst.setInt(3, traje.getId_traje());
			int filasAfectadas = pst.executeUpdate();
			return filasAfectadas > 0;
		} catch (SQLException sqlex) {
			System.err.println("Error al modificar el traje: " + sqlex.getMessage());
		} finally {
			cerrarConexion(conexion);
		}

		return false;
	}

	/**
	 * Método para obtener el id del cliente a partir del nombre seleccionado en la
	 * tabla
	 * 
	 * @param nombre
	 * @return
	 */
	public int obtenerIdClientePorNombre(String nombre) {
		int id = -1;
		String query = "SELECT id_cliente FROM CLIENTE WHERE nombre = ?";
		try (Connection conexion = getConexion(); PreparedStatement ps = conexion.prepareStatement(query)) {
			ps.setString(1, nombre); // 1. Primero el valor
			ResultSet rs = ps.executeQuery(); // 2. Luego la ejecución
			if (rs.next())
				id = rs.getInt("id_cliente");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * Método para obtener el id del taller a partir del nombre seleccionado en la
	 * tabla
	 * 
	 * @param nombre
	 * @return
	 */
	public int obtenerIdTallerPorNombre(String nombre) {
		int id = -1;
		String query = "SELECT id_taller FROM TALLER WHERE nombre_sala = ?";
		try (Connection conexion = getConexion(); PreparedStatement ps = conexion.prepareStatement(query)) {
			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				id = rs.getInt("id_taller");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * Método para obtener el id del empleado a partir del nombre seleccionado en la
	 * tabla
	 * 
	 * @param nombre
	 * @return
	 */
	public int obtenerIdEmpleadoPorNombre(String nombre) {
		int id = -1;
		String query = "SELECT id_empleado FROM EMPLEADO WHERE nombre = ?";
		try (Connection conexion = getConexion(); PreparedStatement ps = conexion.prepareStatement(query)) {
			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				id = rs.getInt("id_empleado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * Método para obtener el id del traje a partir del nombre seleccionado en la
	 * tabla
	 * 
	 * @param nombre
	 * @return
	 */
	public int obtenerIdTrajePorNombre(String nombre) {
		int id = -1;
		String query = "SELECT id_traje FROM TRAJE WHERE nombre = ?";
		try (Connection conexion = getConexion(); PreparedStatement ps = conexion.prepareStatement(query)) {
			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				id = rs.getInt("id_traje");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * Método utilizado para gestionar los permisos del oficial
	 * 
	 * @param id
	 * @return
	 */
	public String obtenerNombreEmpleado(int id) {
		String nombre = "";
		String query = "SELECT nombre FROM EMPLEADO WHERE id_empleado = ?";
		Connection con = getConexion();

		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				nombre = rs.getString("nombre");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nombre;
	}
	
	/**
	 * Metodo para obtener los nombres de los empleados con rango de aprendiz
	 * @return
	 */
	public ArrayList<String> recuperarNombresAprendices() {
	    ArrayList<String> lista = new ArrayList<>();
	    // Filtramos por la categoría específica
	    String sql = "SELECT nombre FROM EMPLEADO WHERE categoria = 'Aprendiz'";

	    try (Connection con = getConexion();
	         Statement st = con.createStatement();
	         ResultSet rs = st.executeQuery(sql)) {

	        while (rs.next()) {
	            lista.add(rs.getString("nombre"));
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al recuperar nombres de aprendices: " + e.getMessage());
	    }
	    return lista;
	}
	
	// ========================================================
	// MÉTODOS PARA EMPLEADOS
	// ========================================================

	public ArrayList<modelo.Empleado> recuperarEmpleados() {
		ArrayList<modelo.Empleado> empleados = new ArrayList<>();
		Connection conexion = getConexion();
		String query = "SELECT id_empleado, nombre, apodo, categoria, contrasena FROM EMPLEADO";

		try {
			java.sql.Statement st = conexion.createStatement();
			java.sql.ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				// NOTA: Asegúrate de que tu clase Empleado se pueda instanciar y tenga estos
				// setters
				modelo.Empleado e = new modelo.Empleado(rs.getInt("id_empleado"), rs.getString("nombre"),
						rs.getString("apodo"), rs.getString("categoria"), rs.getString("contrasena"));
				empleados.add(e);
			}
		} catch (java.sql.SQLException e) {
			System.err.println("Error al recuperar empleados: " + e.getMessage());
		} finally {
			cerrarConexion(conexion);
		}
		return empleados;
	}

	public boolean crearEmpleado(modelo.Empleado emp) {
		String query = "INSERT INTO EMPLEADO (nombre, apodo, categoria, contrasena) VALUES (?, ?, ?, ?)";
		Connection conexion = getConexion();
		try {
			java.sql.PreparedStatement pst = conexion.prepareStatement(query);
			pst.setString(1, emp.getNombre());
			pst.setString(2, emp.getApodo());
			pst.setString(3, emp.getCategoría());
			pst.setString(4, emp.getContraseña());
			return pst.executeUpdate() > 0;
		} catch (java.sql.SQLException sqlex) {
			System.err.println("Error al crear empleado: " + sqlex.getMessage());
			return false;
		} finally {
			cerrarConexion(conexion);
		}
	}

	public boolean modificarEmpleado(modelo.Empleado emp) {
		String query = "UPDATE EMPLEADO SET nombre = ?, apodo = ?, categoria = ?, contrasena = ? WHERE id_empleado = ?";
		Connection conexion = getConexion();
		try {
			java.sql.PreparedStatement pst = conexion.prepareStatement(query);
			pst.setString(1, emp.getNombre());
			pst.setString(2, emp.getApodo());
			pst.setString(3, emp.getCategoría());
			pst.setString(4, emp.getContraseña());
			pst.setInt(5, emp.getId_empleado());
			return pst.executeUpdate() > 0;
		} catch (java.sql.SQLException sqlex) {
			System.err.println("Error al modificar empleado: " + sqlex.getMessage());
			return false;
		} finally {
			cerrarConexion(conexion);
		}
	}

	public boolean eliminarEmpleado(int idEmpleado) {
		String query = "DELETE FROM EMPLEADO WHERE id_empleado = ?";
		Connection conexion = getConexion();
		try {
			java.sql.PreparedStatement pst = conexion.prepareStatement(query);
			pst.setInt(1, idEmpleado);
			return pst.executeUpdate() > 0;
		} catch (java.sql.SQLException sqlex) {
			System.err.println("Error al eliminar empleado: " + sqlex.getMessage());
			return false;
		} finally {
			cerrarConexion(conexion);
		}
	}

	public boolean tieneCitasAsociadas(int idEmpleado) {
		String query = "SELECT COUNT(*) AS total FROM CITA WHERE id_empleado_responsable = ? OR id_aprendiz1 = ? OR id_aprendiz2 = ?";
		Connection conexion = getConexion();
		try {
			java.sql.PreparedStatement pst = conexion.prepareStatement(query);
			pst.setInt(1, idEmpleado);
			pst.setInt(2, idEmpleado);
			pst.setInt(3, idEmpleado);
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt("total") > 0; // true si tiene 1 o más citas
			}
		} catch (java.sql.SQLException sqlex) {
			System.err.println("Error al comprobar las citas del empleado: " + sqlex.getMessage());
		} finally {
			cerrarConexion(conexion);
		}
		return false;
	}

}
