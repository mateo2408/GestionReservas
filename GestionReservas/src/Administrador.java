public class Administrador extends Usuario {

    public Administrador(String cedula, String nombre, String correo, String direccion, String clave, String telefono) {
        super(cedula, nombre, correo, direccion, clave, telefono);
    }

    public void realizarMantenimiento(Equipo equipo, String tipoMantenimiento, String fechaMantenimiento) {
        if ("Correctivo".equalsIgnoreCase(tipoMantenimiento)) {
            equipo.setEstado("Operativo");
            equipo.setFechaMantenimientoCorrectivo(fechaMantenimiento);
        } else if ("Preventivo".equalsIgnoreCase(tipoMantenimiento)) {
            equipo.setEstado("Operativo");
            equipo.setFechaMantenimientoPreventivo(fechaMantenimiento);
        } else {
            throw new IllegalArgumentException("Tipo de mantenimiento no válido");
        }
    }


}