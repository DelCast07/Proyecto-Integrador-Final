package modelo;

import java.sql.Time;

/**
 * 
 */
public class Cita {
	private int id_cita;
    private int id_cliente;
    private int id_traje;
    private int id_empleado;
    private int id_taller;
    private java.util.Date dia;
    private Time hora;
    private int duracion;
    private String nombreCliente;
    private String nombreTaller;
    private String nombreEmpleadoResponsable;
    private String nombreTraje;
    private int id_aprendiz1;
    private int id_aprendiz2;
    private String nombreAprendiz1; 
    private String nombreAprendiz2;

    //Getters y Setters
    public int getId_cita() {
		return id_cita;
	}
	public void setId_cita(int id_cita) {
		this.id_cita = id_cita;
	}
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public int getId_traje() {
		return id_traje;
	}
	public void setId_traje(int id_traje) {
		this.id_traje = id_traje;
	}
	public int getId_empleado() {
		return id_empleado;
	}
	public void setId_empleado(int id_empleado) {
		this.id_empleado = id_empleado;
	}
	public int getId_taller() {
		return id_taller;
	}
	public void setId_taller(int id_taller) {
		this.id_taller = id_taller;
	}
	public java.util.Date getDia() {
		return dia;
	}
	public void setDia(java.util.Date date) {
		this.dia = date;
	}
	public Time getHora() {
		return hora;
	}
	public void setHora(Time time) {
		this.hora = time;
	}
	public int getDuracion() {
		return (int) duracion;
	}
	public void setDuracion(int i) {
		this.duracion = i;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}
	public String getNombreTaller() {
		return nombreTaller;
	}

	public String getNombreEmpleadoResponsable() {
		return nombreEmpleadoResponsable;
	}

	public String getNombreTraje() {
		return nombreTraje;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public void setNombreTaller(String nombreTaller) {
		this.nombreTaller = nombreTaller;
	}

	public void setNombreEmpleadoResponsable(String nombreEmpleadoResponsable) {
		this.nombreEmpleadoResponsable = nombreEmpleadoResponsable;
	}

	public void setNombreTraje(String nombreTraje) {
		this.nombreTraje = nombreTraje;
	}
	/**
	 * @return the id_aprendiz1
	 */
	public int getId_aprendiz1() {
		return id_aprendiz1;
	}
	/**
	 * @param id_aprendiz1 the id_aprendiz1 to set
	 */
	public void setId_aprendiz1(int id_aprendiz1) {
		this.id_aprendiz1 = id_aprendiz1;
	}
	/**
	 * @return the id_aprendiz2
	 */
	public int getId_aprendiz2() {
		return id_aprendiz2;
	}
	/**
	 * @param id_aprendiz2 the id_aprendiz2 to set
	 */
	public void setId_aprendiz2(int id_aprendiz2) {
		this.id_aprendiz2 = id_aprendiz2;
	}
	/**
	 * @return the nombreAprendiz1
	 */
	public String getNombreAprendiz1() {
		return nombreAprendiz1;
	}
	/**
	 * @param nombreAprendiz1 the nombreAprendiz1 to set
	 */
	public void setNombreAprendiz1(String nombreAprendiz1) {
		this.nombreAprendiz1 = nombreAprendiz1;
	}
	/**
	 * @return the nombreAprendiz2
	 */
	public String getNombreAprendiz2() {
		return nombreAprendiz2;
	}
	/**
	 * @param nombreAprendiz2 the nombreAprendiz2 to set
	 */
	public void setNombreAprendiz2(String nombreAprendiz2) {
		this.nombreAprendiz2 = nombreAprendiz2;
	}
}
