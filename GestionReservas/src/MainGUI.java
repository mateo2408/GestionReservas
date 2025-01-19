import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import com.toedter.calendar.JDateChooser;
import javax.swing.SpinnerDateModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

public class MainGUI {
    private static JTextField correoField;
    private static JPasswordField claveField;
    private static JButton loginButton;
    private static List<Equipo> equipos = new ArrayList<>();
    private static List<DetalleReserva> reservas = new ArrayList<>();
    private static List<Estudiante> estudiantes = new ArrayList<>();
    private static List<Administrador> administradores = new ArrayList<>();
    private static List<RegistroMantenimiento> historialMantenimientos = new ArrayList<>();
    private static List<Laboratorio> laboratorios = new ArrayList<>();


    public static void main(String[] args) {
        // Configuración inicial
        equipos.add(new Equipo("Osciloscopio", "Disponible", new Date()));
        equipos.add(new Equipo("Fuente de voltaje", "Requiere mantenimiento", new Date()));
        equipos.add(new Equipo("Generador de onda", "Requiere mantenimiento", new Date()));


        // Crear usuarios de prueba
        Estudiante estudiante1 = new Estudiante("1234567890", "Paul Rosero", "pmrosero@gmail.com", "Quito", "1234", "0987654321", "Electrónica", 3);
        Administrador administrador1 = new Administrador("0987654321", "David Iza", "david.iza@udla.ec", "Ambato", "5678", "1234567890");

        estudiantes.add(estudiante1);
        administradores.add(administrador1);

        // Configuración GUI
        JFrame win = new JFrame();
        win.setTitle("Gestión de Equipos");
        win.setSize(800, 600);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true); // Asegurar que la ventana se muestre

        // Mostrar panel de login
        mostrarPanelLogin(win, estudiante1, administrador1);
    }


    private static void mostrarFormularioRegistro(JFrame win) {
        win.getContentPane().removeAll();
        JPanel registroPanel = new JPanel(new GridLayout(10, 2));

        JTextField cedulaField = new JTextField();
        JTextField nombreField = new JTextField();
        JTextField correoField = new JTextField();
        JPasswordField claveField = new JPasswordField();
        JTextField telefonoField = new JTextField();
        JTextField ciudadField = new JTextField();
        JComboBox<String> tipoUsuarioBox = new JComboBox<>(new String[]{"Estudiante", "Administrador"});
        JButton registrarButton = new JButton("Registrar");
        JButton regresarButton = new JButton("Regresar");

        registroPanel.add(new JLabel("Cédula:"));
        registroPanel.add(cedulaField);
        registroPanel.add(new JLabel("Nombre:"));
        registroPanel.add(nombreField);
        registroPanel.add(new JLabel("Correo:"));
        registroPanel.add(correoField);
        registroPanel.add(new JLabel("Contraseña:"));
        registroPanel.add(claveField);
        registroPanel.add(new JLabel("Teléfono:"));
        registroPanel.add(telefonoField);
        registroPanel.add(new JLabel("Ciudad:"));
        registroPanel.add(ciudadField);
        registroPanel.add(new JLabel("Tipo de Usuario:"));
        registroPanel.add(tipoUsuarioBox);
        registroPanel.add(registrarButton);
        registroPanel.add(regresarButton);

        win.add(registroPanel);
        win.revalidate();
        win.repaint();

        registrarButton.addActionListener(e -> {
            String cedula = cedulaField.getText();
            String nombre = nombreField.getText();
            String correo = correoField.getText();
            String clave = new String(claveField.getPassword());
            String telefono = telefonoField.getText();
            String ciudad = ciudadField.getText();
            String tipoUsuario = (String) tipoUsuarioBox.getSelectedItem();

            if (cedula.isEmpty() || nombre.isEmpty() || correo.isEmpty() || clave.isEmpty() || telefono.isEmpty() || ciudad.isEmpty()) {
                JOptionPane.showMessageDialog(win, "Por favor, complete todos los campos.");
                return;
            }

            if (!new Usuario(null, null, null, null, null, null).validarcedula(cedula)) {
                JOptionPane.showMessageDialog(win, "Cédula inválida. Debe tener 10 caracteres numéricos.");
                return;
            }

            if (!Usuario.validarCorreo(correo)) {
                JOptionPane.showMessageDialog(win, "Correo electrónico inválido.");
                return;
            }

            // Validar que la cédula y el correo no existan ya
            boolean cedulaExiste = estudiantes.stream().anyMatch(eu -> eu.getCedula().equals(cedula)) ||
                    administradores.stream().anyMatch(ad -> ad.getCedula().equals(cedula));

            boolean correoExiste = estudiantes.stream().anyMatch(eu -> eu.getCorreo().equals(correo)) ||
                    administradores.stream().anyMatch(ad -> ad.getCorreo().equals(correo));

            if (cedulaExiste) {
                JOptionPane.showMessageDialog(win, "La cédula ya está registrada.");
                return;
            }

            if (correoExiste) {
                JOptionPane.showMessageDialog(win, "El correo ya está registrado.");
                return;
            }

            assert tipoUsuario != null;
            if (tipoUsuario.equals("Estudiante")) {
                Estudiante nuevoEstudiante = new Estudiante(cedula, nombre, correo, ciudad, clave, telefono, "Carrera", 1);
                estudiantes.add(nuevoEstudiante);
                JOptionPane.showMessageDialog(win, "Estudiante registrado con éxito.");
            } else {
                Administrador nuevoAdministrador = new Administrador(cedula, nombre, correo, ciudad, clave, telefono);
                administradores.add(nuevoAdministrador);
                JOptionPane.showMessageDialog(win, "Administrador registrado con éxito.");
            }
        });

        regresarButton.addActionListener(e -> mostrarPanelLogin(win, null, null)); // Regresar al login
    }



    private static void mostrarPanelLogin(JFrame win, Estudiante dummyEstudiante, Administrador dummyAdministrador) {
        win.getContentPane().removeAll();

        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        correoField = new JTextField();
        claveField = new JPasswordField();
        loginButton = new JButton("Iniciar Sesión");
        JButton botonRegistro = new JButton("Registrar Usuario");

        loginPanel.add(new JLabel("Correo:"));
        loginPanel.add(correoField);
        loginPanel.add(new JLabel("Contraseña:"));
        loginPanel.add(claveField);
        loginPanel.add(loginButton);
        loginPanel.add(botonRegistro);

        win.add(loginPanel);
        win.revalidate();
        win.repaint();

   loginButton.addActionListener(e -> {
    String correo = correoField.getText();
    String clave = new String(claveField.getPassword());

    // Validar credenciales en estudiantes
    for (Estudiante estudiante : estudiantes) {
        if (estudiante.getCorreo().equals(correo) && estudiante.getClave().equals(clave)) {
            mostrarPanelEstudiante(win, estudiante);
            return;
        }
    }

    // Validar credenciales en administradores
    for (Administrador administrador : administradores) {
        if (administrador.getCorreo().equals(correo) && administrador.getClave().equals(clave)) {
            mostrarPanelAdministrador(win, administrador); // Pasar el administrador correcto
            return;
        }
    }

    JOptionPane.showMessageDialog(win, "Credenciales inválidas.");
});

        botonRegistro.addActionListener(e -> mostrarFormularioRegistro(win));
    }
    private static void mostrarPanelEstudiante(JFrame win, Estudiante estudiante) {
    win.getContentPane().removeAll();
    JPanel panelEstudiante = new JPanel(new BorderLayout());

    // Crear el panel de pestañas
    JTabbedPane tabbedPane = new JTabbedPane();

    // === Panel de Reservas Actuales ===
    DefaultTableModel modeloReservas = new DefaultTableModel(
            new String[]{"Número", "Tipo", "Fecha", "Detalles"}, 0
    );
    JTable tablaReservas = new JTable(modeloReservas);
    JScrollPane scrollReservas = new JScrollPane(tablaReservas);
    panelEstudiante.add(scrollReservas, BorderLayout.NORTH);

    // === Panel de Reserva de Laboratorio ===
    JPanel panelReservaLab = new JPanel(new GridLayout(0, 4, 10, 10));

    // Componentes para laboratorio
    JComboBox<String> laboratorioComboBox = new JComboBox<>(
            laboratorios.stream()
                    .map(Laboratorio::getNombre)
                    .toArray(String[]::new)
    );
    JDateChooser fechaLabChooser = new JDateChooser();
    fechaLabChooser.setDateFormatString("dd/MM/yyyy");
    fechaLabChooser.setMinSelectableDate(new Date());

    SpinnerDateModel modeloHoraInicio = new SpinnerDateModel();
    SpinnerDateModel modeloHoraFin = new SpinnerDateModel();
    JSpinner spinnerHoraInicio = new JSpinner(modeloHoraInicio);
    JSpinner spinnerHoraFin = new JSpinner(modeloHoraFin);

    JSpinner.DateEditor editorInicio = new JSpinner.DateEditor(spinnerHoraInicio, "HH:mm");
    JSpinner.DateEditor editorFin = new JSpinner.DateEditor(spinnerHoraFin, "HH:mm");
    spinnerHoraInicio.setEditor(editorInicio);
    spinnerHoraFin.setEditor(editorFin);

    JTextField ocupantesField = new JTextField();

    // Configurar panel de laboratorio
    panelReservaLab.add(new JLabel("Laboratorio:"));
    panelReservaLab.add(laboratorioComboBox);
    panelReservaLab.add(new JLabel("Fecha:"));
    panelReservaLab.add(fechaLabChooser);
    panelReservaLab.add(new JLabel("Hora inicio:"));
    panelReservaLab.add(spinnerHoraInicio);
    panelReservaLab.add(new JLabel("Hora fin:"));
    panelReservaLab.add(spinnerHoraFin);
    panelReservaLab.add(new JLabel("Número de Ocupantes:"));
    panelReservaLab.add(ocupantesField);

    JButton reservarLabButton = new JButton("Reservar Laboratorio");
    panelReservaLab.add(new JLabel(""));
    panelReservaLab.add(reservarLabButton);

    // === Panel de Reserva de Equipo ===
    JPanel panelReservaEquipo = new JPanel(new GridLayout(0, 4, 10, 10));

    JComboBox<String> equipoComboBox = new JComboBox<>(equipos.stream()
            .filter(equipo -> !equipo.isPrestado())
            .map(Equipo::getNombre)
            .toArray(String[]::new));

    JDateChooser fechaEquipoChooser = new JDateChooser();
    fechaEquipoChooser.setDateFormatString("dd/MM/yyyy");
    fechaEquipoChooser.setMinSelectableDate(new Date());

    JSpinner duracionSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 3, 1));

    // Configurar panel de equipo
    panelReservaEquipo.add(new JLabel("Equipo:"));
    panelReservaEquipo.add(equipoComboBox);
    panelReservaEquipo.add(new JLabel("Fecha:"));
    panelReservaEquipo.add(fechaEquipoChooser);
    panelReservaEquipo.add(new JLabel("Duración (días):"));
    panelReservaEquipo.add(duracionSpinner);

    JButton reservarEquipoButton = new JButton("Reservar Equipo");
    panelReservaEquipo.add(new JLabel(""));
    panelReservaEquipo.add(reservarEquipoButton);

    // Agregar paneles a las pestañas
    tabbedPane.addTab("Reserva de Laboratorio", panelReservaLab);
    tabbedPane.addTab("Reserva de Equipo", panelReservaEquipo);

    panelEstudiante.add(tabbedPane, BorderLayout.CENTER);

    // === Acción del botón de reserva de equipo ===
    reservarEquipoButton.addActionListener(e -> {
        try {
            if (fechaEquipoChooser.getDate() == null) {
                JOptionPane.showMessageDialog(win, "Seleccione una fecha válida");
                return;
            }

            String equipoSeleccionado = (String) equipoComboBox.getSelectedItem();
            int duracion = (int) duracionSpinner.getValue();
            LocalDate fechaInicio = fechaEquipoChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaFin = fechaInicio.plusDays(duracion - 1);

            Equipo equipo = equipos.stream()
                    .filter(eq -> eq.getNombre().equals(equipoSeleccionado))
                    .findFirst()
                    .orElse(null);

            if (equipo != null && !equipo.isPrestado()) {
                equipo.agregarDiasDeUso(duracion); // Acumular días de uso

                if (equipo.getDiasDeUso() > 3) {
                    JOptionPane.showMessageDialog(win, "El equipo " + equipo.getNombre() + " ahora requiere mantenimiento.");
                }

                estudiante.añadirReservaEquipo(fechaInicio, equipo, duracion);
                actualizarTablaReservas(modeloReservas, estudiante);
                equipo.setPrestado(true);
                JOptionPane.showMessageDialog(win, "Equipo reservado con éxito");

                // Limpiar campos
                fechaEquipoChooser.setDate(null);
                duracionSpinner.setValue(1);

                // Actualizar lista de equipos disponibles
                equipoComboBox.setModel(new DefaultComboBoxModel<>(equipos.stream()
                        .filter(eq -> !eq.isPrestado())
                        .map(Equipo::getNombre)
                        .toArray(String[]::new)));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(win, "Error al reservar equipo: " + ex.getMessage());
        }
    });

    // Paneles inferiores (Eliminar reserva y Salir)
    JButton eliminarReservaButton = new JButton("Eliminar Reserva");
    eliminarReservaButton.addActionListener(e -> {
        int filaSeleccionada = tablaReservas.getSelectedRow();
        if (filaSeleccionada != -1) {
            try {
                int numeroReserva = (int) modeloReservas.getValueAt(filaSeleccionada, 0);
                DetalleReserva reservaAEliminar = estudiante.getReservas().stream()
                        .filter(reserva -> reserva.getNumeroReserva() == numeroReserva)
                        .findFirst()
                        .orElse(null);

                if (reservaAEliminar != null) {
                    if (reservaAEliminar instanceof DetalleReservaEquipo) {
                        DetalleReservaEquipo reservaEquipo = (DetalleReservaEquipo) reservaAEliminar;
                        reservaEquipo.getEquipo().setPrestado(false);
                    }
                    estudiante.eliminarReserva(reservaAEliminar);
                    actualizarTablaReservas(modeloReservas, estudiante);
                    JOptionPane.showMessageDialog(win, "Reserva eliminada con éxito");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(win, "Error al eliminar reserva: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(win, "Seleccione una reserva para eliminar");
        }
    });

    JButton botonSalir = new JButton("Salir");
    botonSalir.addActionListener(e -> mostrarPanelLogin(win, null, null));

    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    panelBotones.add(eliminarReservaButton);
    panelBotones.add(botonSalir);
    panelEstudiante.add(panelBotones, BorderLayout.SOUTH);

    win.add(panelEstudiante);
    win.revalidate();
    win.repaint();
}


    // Método auxiliar actualizado para usar getReservas() del estudiante
    private static void actualizarTablaReservas(DefaultTableModel modelo, Estudiante estudiante) {
        modelo.setRowCount(0);
        for (DetalleReserva reserva : estudiante.getReservas()) {
            String detalles;
            String tipo;

            if (reserva instanceof DetalleReservaLaboratorio) {
                DetalleReservaLaboratorio reservaLab = (DetalleReservaLaboratorio) reserva;
                tipo = "Laboratorio";
                detalles = String.format("%s, %s-%s, %d ocupantes",
                        reservaLab.getLaboratorioReservado(),
                        reservaLab.getHoraInicio().toString(),
                        reservaLab.getHoraFin().toString(),
                        reservaLab.getNumeroOcupantes());
            } else {
                DetalleReservaEquipo reservaEquipo = (DetalleReservaEquipo) reserva;
                tipo = "Equipo";
                detalles = String.format("%s, %d días",
                        reservaEquipo.getEquipo().getNombre(),
                        reservaEquipo.getDuracion());
            }

            modelo.addRow(new Object[]{
                    reserva.getNumeroReserva(),
                    tipo,
                    reserva.getFecha(),
                    detalles
            });
        }
    }

    private static void mostrarVentanaPrincipal(JFrame win, JTextField correoField, JPasswordField claveField, JButton loginButton) {
        win.getContentPane().removeAll();
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));

        loginPanel.add(new JLabel("Correo:"));
        loginPanel.add(correoField);
        loginPanel.add(new JLabel("Contraseña:"));
        loginPanel.add(claveField);
        loginPanel.add(new JLabel(""));
        loginPanel.add(loginButton);

        win.add(loginPanel);
        win.revalidate();
        win.repaint();
    }


   private static void mostrarPanelAdministrador(JFrame win, Administrador administrador) {
    win.getContentPane().removeAll();
    JPanel panelAdministrador = new JPanel(new BorderLayout());

    // Panel de Equipos y Laboratorios que Requieren Mantenimiento
    JTable tablaEquiposLaboratorios = new JTable(new DefaultTableModel(
            new String[]{"Nombre", "Tipo", "Requiere Mantenimiento Correctivo", "Requiere Mantenimiento Preventivo"}, 0
    ));
    DefaultTableModel model = (DefaultTableModel) tablaEquiposLaboratorios.getModel();
    equipos.forEach(equipo -> model.addRow(new Object[]{equipo.getNombre(), "Equipo", equipo.requiereMantenimientoCorrectivo(), equipo.requiereMantenimientoPreventivo()}));
    laboratorios.forEach(laboratorio -> model.addRow(new Object[]{laboratorio.getNombre(), "Laboratorio", "N/A", "N/A"}));
    JScrollPane scrollEquiposLaboratorios = new JScrollPane(tablaEquiposLaboratorios);
    panelAdministrador.add(scrollEquiposLaboratorios, BorderLayout.NORTH);

    // Panel de Mantenimiento
    JPanel mantenimientoPanel = new JPanel(new GridLayout(8, 1));
    JComboBox<String> equipoMantenimientoComboBox = new JComboBox<>(
            equipos.stream()
                    .map(Equipo::getNombre)
                    .toArray(String[]::new)
    );

    JButton btnHistorial = new JButton("Ver Historial");
    btnHistorial.addActionListener(e -> mostrarHistorial(win));
    mantenimientoPanel.add(btnHistorial);

    JButton btnPreventivo = new JButton("Mantenimiento Preventivo");
    JButton btnCorrectivo = new JButton("Mantenimiento Correctivo");

    mantenimientoPanel.add(new JLabel("Equipo para Mantenimiento:"));
    mantenimientoPanel.add(equipoMantenimientoComboBox);
    mantenimientoPanel.add(btnPreventivo);
    mantenimientoPanel.add(btnCorrectivo);

    // Botones para añadir equipos y laboratorios
    JButton btnCrearEquipo = new JButton("Crear Equipo");
    JButton btnCrearLaboratorio = new JButton("Crear Laboratorio");

    btnCrearEquipo.addActionListener(e -> mostrarFormularioCrearEquipo(win));
    btnCrearLaboratorio.addActionListener(e -> mostrarFormularioCrearLaboratorio(win));

    mantenimientoPanel.add(btnCrearEquipo);
    mantenimientoPanel.add(btnCrearLaboratorio);

    panelAdministrador.add(mantenimientoPanel, BorderLayout.CENTER);

    // Acción para Mantenimiento Preventivo
    btnPreventivo.addActionListener(e -> realizarMantenimiento(win, administrador, equipoMantenimientoComboBox, "Preventivo", tablaEquiposLaboratorios));

    // Acción para Mantenimiento Correctivo
    btnCorrectivo.addActionListener(e -> realizarMantenimiento(win, administrador, equipoMantenimientoComboBox, "Correctivo", tablaEquiposLaboratorios));

    // Botón para regresar al inicio de sesión
    JButton botonSalir = new JButton("Salir");
    botonSalir.addActionListener(e -> {
        mostrarPanelLogin(win, null, null); // Regresar al login
    });

    // Panel inferior con el botón de salida
    JPanel panelBotonSalir = new JPanel();
    panelBotonSalir.add(botonSalir);

    panelAdministrador.add(panelBotonSalir, BorderLayout.SOUTH);

    win.add(panelAdministrador);
    win.revalidate();
    win.repaint();
}
private static void mostrarFormularioCrearEquipo(JFrame win) {
    win.getContentPane().removeAll();
    JPanel crearEquipoPanel = new JPanel(new GridLayout(4, 2));

    JTextField nombreField = new JTextField();
    JTextField estadoField = new JTextField();
    JButton crearButton = new JButton("Crear");
    JButton regresarButton = new JButton("Regresar");

    crearEquipoPanel.add(new JLabel("Nombre del Equipo:"));
    crearEquipoPanel.add(nombreField);
    crearEquipoPanel.add(new JLabel("Estado del Equipo:"));
    crearEquipoPanel.add(estadoField);
    crearEquipoPanel.add(crearButton);
    crearEquipoPanel.add(regresarButton);

    win.add(crearEquipoPanel);
    win.revalidate();
    win.repaint();

    crearButton.addActionListener(e -> {
        String nombre = nombreField.getText();
        String estado = estadoField.getText();

        if (nombre.isEmpty() || estado.isEmpty()) {
            JOptionPane.showMessageDialog(win, "Por favor, complete todos los campos.");
            return;
        }

        equipos.add(new Equipo(nombre, estado, new Date()));
        JOptionPane.showMessageDialog(win, "Equipo creado con éxito.");
        mostrarPanelAdministrador(win, null);
    });

    regresarButton.addActionListener(e -> mostrarPanelAdministrador(win, null));
}

private static void mostrarFormularioCrearLaboratorio(JFrame win) {
    win.getContentPane().removeAll();
    JPanel crearLaboratorioPanel = new JPanel(new GridLayout(3, 2));

    JTextField nombreField = new JTextField();
    JTextField ubicacionField = new JTextField();
    JButton crearButton = new JButton("Crear");
    JButton regresarButton = new JButton("Regresar");

    crearLaboratorioPanel.add(new JLabel("Nombre del Laboratorio:"));
    crearLaboratorioPanel.add(nombreField);
    crearLaboratorioPanel.add(new JLabel("Ubicación del Laboratorio:"));
    crearLaboratorioPanel.add(ubicacionField);
    crearLaboratorioPanel.add(crearButton);
    crearLaboratorioPanel.add(regresarButton);

    win.add(crearLaboratorioPanel);
    win.revalidate();
    win.repaint();

    crearButton.addActionListener(e -> {
        String nombre = nombreField.getText();
        String ubicacion = ubicacionField.getText();

        if (nombre.isEmpty() || ubicacion.isEmpty()) {
            JOptionPane.showMessageDialog(win, "Por favor, complete todos los campos.");
            return;
        }

        laboratorios.add(new Laboratorio(nombre, ubicacion));
        JOptionPane.showMessageDialog(win, "Laboratorio creado con éxito.");
        mostrarPanelAdministrador(win, null);
    });

    regresarButton.addActionListener(e -> mostrarPanelAdministrador(win, null));
}



    // Método auxiliar para realizar el mantenimiento
    private static void realizarMantenimiento(JFrame win, Administrador administrador, JComboBox<String> equipoComboBox, String tipoMantenimiento, JTable tablaEquipos) {
    String equipoSeleccionado = (String) equipoComboBox.getSelectedItem();
    if (equipoSeleccionado == null) {
        JOptionPane.showMessageDialog(win, "Seleccione un equipo para mantenimiento.");
        return;
    }

    String responsable = JOptionPane.showInputDialog(win, "Ingrese el nombre del responsable del mantenimiento:");
    if (responsable == null || responsable.trim().isEmpty()) {
        JOptionPane.showMessageDialog(win, "El nombre del responsable es obligatorio.");
        return;
    }

    JDateChooser dateChooser = new JDateChooser();
    dateChooser.setDateFormatString("dd/MM/yyyy");

    int resultado = JOptionPane.showConfirmDialog(
            win, dateChooser, "Seleccione la fecha de mantenimiento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (resultado != JOptionPane.OK_OPTION || dateChooser.getDate() == null) {
        JOptionPane.showMessageDialog(win, "Fecha de mantenimiento no seleccionada.");
        return;
    }

    Date fechaSeleccionada = dateChooser.getDate();
    LocalDate fechaMantenimiento = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    Equipo equipo = equipos.stream()
            .filter(eq -> eq.getNombre().equals(equipoSeleccionado))
            .findFirst()
            .orElse(null);

    if (equipo != null) {
        administrador.realizarMantenimiento(equipo, tipoMantenimiento, fechaMantenimiento.toString());
        agregarRegistro(equipo.getNombre(), tipoMantenimiento, fechaMantenimiento.toString(), responsable);
        JOptionPane.showMessageDialog(win, "Mantenimiento realizado con éxito.");
        actualizarTablaEquipos(tablaEquipos);
    }
}


    // Método para mostrar el panel principal (login panel)
    private static void mostrarPanelPrincipal(JFrame win, JTextField correoField, JPasswordField claveField, JButton loginButton) {
        win.getContentPane().removeAll(); // Limpiar el contenido del JFrame

        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        loginPanel.add(new JLabel("Correo:"));
        loginPanel.add(correoField);
        loginPanel.add(new JLabel("Contraseña:"));
        loginPanel.add(claveField);
        loginPanel.add(loginButton);

        // Restablecer los campos de texto
        correoField.setText("");
        claveField.setText("");

        win.add(loginPanel); // Agregar el panel principal al JFrame
        win.revalidate();
        win.repaint();
    }
    private static void mostrarHistorial(JFrame win) {
        // Crear una ventana nueva
        JDialog historialFrame = new JDialog(win, "Historial de Mantenimientos", true);
        historialFrame.setSize(600, 400);

        // Crear tabla para mostrar el historial
        String[] columnas = {"Equipo", "Tipo", "Fecha", "Responsable"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (RegistroMantenimiento registro : historialMantenimientos) {
            model.addRow(new Object[]{registro.getEquipo(), registro.getTipoMantenimiento(), registro.getFecha(), registro.getResponsable()});
        }

        JTable tablaHistorial = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);

        historialFrame.add(scrollPane);
        historialFrame.setLocationRelativeTo(win);
        historialFrame.setVisible(true);
    }
    private static void agregarRegistro(String equipo, String tipoMantenimiento, String fecha, String responsable) {
        RegistroMantenimiento registro = new RegistroMantenimiento(equipo, tipoMantenimiento, fecha, responsable);
        historialMantenimientos.add(registro);
    }
    private static void actualizarTablaEquipos(JTable tablaEquipos) {
    DefaultTableModel model = (DefaultTableModel) tablaEquipos.getModel();
    model.setRowCount(0); // Limpiar la tabla

    equipos.forEach(equipo -> model.addRow(new Object[]{
            equipo.getNombre(),
            "Equipo",
            equipo.requiereMantenimientoCorrectivo(),
            equipo.requiereMantenimientoPreventivo()
    }));

    laboratorios.forEach(laboratorio -> model.addRow(new Object[]{
            laboratorio.getNombre(),
            "Laboratorio",
            "N/A",
            "N/A"
    }));
}
}