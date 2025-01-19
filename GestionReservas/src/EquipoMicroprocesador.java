import java.time.LocalDateTime;
import java.util.Date;

public class EquipoMicroprocesador extends Equipo {
    private int ram;
    private String modeloMicro;
    private String modeloCPU;

    public EquipoMicroprocesador(String codigo, String nombre, String estado, boolean prestado, Date fechaAdquisicion, int ram, String modeloMicro, String modeloCPU) {
        super(nombre, estado, fechaAdquisicion);
        this.ram = ram;
        this.modeloMicro = modeloMicro;
        this.modeloCPU = modeloCPU;
    }

    public int getRam() {
        return ram;
    }

    public String getModeloMicro() {
        return modeloMicro;
    }

    public String getModeloCPU() {
        return modeloCPU;
    }

    public boolean requiereMantenimientoCorrectivo() {
        return true;
    }

    public boolean requiereMantenimientoPreventivo() {
        return true;
    }
}