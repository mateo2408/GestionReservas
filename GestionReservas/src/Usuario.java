import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Usuario {
    private String cedula;
    private String nombre;
    private String correo;
    private String direccion;
    private String clave;
    private String telefono;
    private static List<DetalleReserva> reservasTotales = new ArrayList<>();

    public Usuario(String cedula, String nombre, String correo, String direccion, String clave, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.direccion = direccion;
        this.clave = clave;
        this.telefono = telefono;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getClave() {
        return clave;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean validarcedula(String cedula) {
        // Validar que la cédula tenga exactamente 10 caracteres
        if (cedula == null || cedula.length() != 10) {
            return false;
        }

        // Validar que todos los caracteres sean numéricos
        return cedula.matches("\\d+");
    }

    public static boolean validarCorreo(String correo) {
        // Expresión regular para validar correos electrónicos
        String regex = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        // Compilar la expresión regular
        Pattern pattern = Pattern.compile(regex);
        // Verificar si el correo coincide con la expresión regular
        return correo != null && pattern.matcher(correo).matches();
    }

    public List<DetalleReservaEquipo> getAllReservasEquipo() {
        return reservasTotales.stream()
                .filter(reserva -> reserva instanceof DetalleReservaEquipo)
                .map(reserva -> (DetalleReservaEquipo) reserva)
                .collect(Collectors.toList());
    }

    public List<DetalleReservaLaboratorio> getAllReservasLaboratorio() {
        return reservasTotales.stream()
                .filter(reserva -> reserva instanceof DetalleReservaLaboratorio)
                .map(reserva -> (DetalleReservaLaboratorio) reserva)
                .collect(Collectors.toList());
    }

    public void añadirReservaGlobal(DetalleReserva reserva)
    {
        reservasTotales.add(reserva);
    }

    public void eliminarReservaGlobal(DetalleReserva reserva)
    {
        reservasTotales.remove(reserva);
    }

    public boolean devolverEquipo(Equipo equipo, DetalleReserva reserva) {
        // Lógica para devolución de equipo
        if (equipo != null && reserva != null) {
            equipo.setPrestado(false);
            return true;
        }
        return false;
    }
}
