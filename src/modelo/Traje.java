/**
 * 
 */
package modelo;

/**
 * 
 */
public class Traje {
	private int id_traje;
    private String nombre;
    private String estado;
    private String nombreCliente;
    private int id_cliente;
	
    //Getters y Setters
    public int getId_traje() {
		return id_traje;
	}
	public void setId_traje(int id_traje) {
		this.id_traje = id_traje;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNombreCliente() { 
		return nombreCliente; 
	}
	public void setNombreCliente(String nombreCliente) { 
		this.nombreCliente = nombreCliente; 
	}
	public int getId_cliente() { 
		return id_cliente; 
	}
	public void setId_cliente(int id_cliente) { 
		this.id_cliente = id_cliente; 
	}
}
