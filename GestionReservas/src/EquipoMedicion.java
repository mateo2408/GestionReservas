import java.time.LocalDateTime;
import java.util.Date;

public class EquipoMedicion extends Equipo {
    private Date ultimaCalibración;
    private int periodoCalibración;

    public EquipoMedicion(String codigo, String nombre, String estado, boolean prestado, Date fechaAdquisicion, Date ultimaCalibración, int periodoCalibración) {
        super(nombre, estado, fechaAdquisicion);
        this.ultimaCalibración = ultimaCalibración;
        this.periodoCalibración = periodoCalibración;
    }

    public Date getUltimaCalibración() {
        return ultimaCalibración;
    }

    public int getPeriodoCalibración() {
        return periodoCalibración;
    }

    public boolean requiereMantenimientoCorrectivo() {
        return false;
    }

    public boolean requiereMantenimientoPreventivo() {
        return true;
    }
}
