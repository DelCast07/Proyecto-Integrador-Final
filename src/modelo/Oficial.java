package modelo;

public class Oficial extends Empleado {

    // Constructorr
    public Oficial(int id_empleado, String nombre, String apodo, String categoria, String contrasena) {
        super(id_empleado, nombre, apodo, categoria, contrasena);
    }

    /**
     * El oficial puede crear citas de tipo Diseño, Costura y Pruebas.
     */
    public void crearCita() {
        // Aquí se conectará con la BBDD para insertar citas de 3 tipos
        System.out.println("Creando cita de Diseño/Costura/Pruebas...");
    }

    /**
     * Lógica: Modificar solo las citas suya
     * El resto en la interfaz aparecerán bloqueadas.
     */
    public void modificarCitaPropia() {
        // Aquí se validará el if
        System.out.println("Modificando mi propia cita...");
    }

    /**
     *Cada cita puede tener de 0 a 2 aprendices.
     */
    public void asignarAprendiz() {
        // Aquí se validará que el contador de aprendices en la cita sea < 2
        System.out.println("Asignando aprendiz (Respetando el límite de 0-2)...");
    }
    }