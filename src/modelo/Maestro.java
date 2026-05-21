package modelo;

public class Maestro extends Empleado {
	public Maestro (int id_empleado, String nombre, String apodo, String categoría, String contraseña) {
		super(id_empleado, nombre, apodo, categoría, contraseña);
	}
	
	//MÉTODOS
	public String modificarCita() {
		return "Cita modificada por el Maestro " + nombre + ".";
	}
	
	public String reservarCita() {
		return "Cita reservada por el Maestro " + nombre + ".";
	}
	
	public String añadirCliente() {
		return "Cliente añadido por el Maestro " + nombre + ".";
	}
	
	public String modificarCliente() {
		return "Cliente modificado por el Maestro " + nombre + ".";
	}
	
	public String borrarCliente() {
		return "Cliente eliminado por el Maestro " + nombre + ".";
	}
	
	public String borrarCita() {
		return "Cita eliminada por el Maestro " + nombre + ".";
	}
	
	public String añadirTaller() {
		return "Taller añadidio por el Maestro " + nombre + ".";
	}
	
	public String modificarTaller() {
		return "Taller modificado por el Maestro " + nombre + ".";
	}
	
	public String borrarTaller() {
		return "Taller borrado por el Maestro " + nombre + ".";
	}
	
	public String crearCita() {
		return "Cita creada por el Maestro " + nombre + ".";
	}
}
