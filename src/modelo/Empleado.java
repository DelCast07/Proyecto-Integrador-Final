package modelo;

public class Empleado {
	private int id_empleado;
	protected String nombre;
	private String apodo;
	protected String categoría;
	private String contraseña;
	
	public Empleado(int id_empleado, String nombre, String apodo, String categoría, String contraseña) {
        this.id_empleado = id_empleado;
        this.nombre = nombre;
        this.apodo = apodo;
        this.categoría = categoría;
        this.contraseña = contraseña;
    }
	
	//METODOS
	public String verCita() {
		return "Citas disponibles para el " + categoría + nombre;
	}
	
	//GETTERS Y SETTERS
	
	//id_empleado
	public int getId_empleado() {
		return id_empleado;
	}
	public void setId_empleado(int id_empleado) {
		this.id_empleado = id_empleado;
	}
	
	//nombre
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	//apodo
	public String getApodo() {
		return apodo;
	}
	public void setApodo(String apodo) {
		this.apodo = apodo;
	}
	
	//categoría
	public String getCategoría() {
		return categoría;
	}
	public void setCategoría(String categoría) {
		this.categoría = categoría;
	}
	
	//contraseña
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
}
