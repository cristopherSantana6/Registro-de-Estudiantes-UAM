package ni.edu.uam.vieos.controlers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ni.edu.uam.vieos.dao.EstudianteDao;
import ni.edu.uam.vieos.modelos.Estudiante;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class EstudianteController {
    private final EstudianteDao listado = new EstudianteDao();
    private final ObservableList<Estudiante> estudiantes = FXCollections.observableArrayList();
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtCarrera;
    @FXML private DatePicker dpFechaNac;
    @FXML private CheckBox chkTieneBeca;
    @FXML private TableView<Estudiante> tblEstudiantes;
    @FXML private TableColumn<Estudiante, String> colNombres;
    @FXML private TableColumn<Estudiante, String> colApellidos;
    @FXML private TableColumn<Estudiante, String> colCarrera;
    @FXML private TableColumn<Estudiante, String> colFecha;
    @FXML private TableColumn<Estudiante, String> colEdad;
    @FXML private TableColumn<Estudiante, String> colBeca;
    @FXML private Label lblRegistros;
    @FXML private Label lblEstado;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;

    @FXML
    private void initialize() {
        colNombres.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNombres()));
        colApellidos.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getApellidos()));
        colCarrera.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCarrera()));
        colFecha.setCellValueFactory(data -> new ReadOnlyStringWrapper(
                data.getValue().getFechaNacimiento() == null ? "-" : data.getValue().getFechaNacimiento().format(formatoFecha)));
        colEdad.setCellValueFactory(data -> new ReadOnlyStringWrapper(calcularEdad(data.getValue().getFechaNacimiento())));
        colBeca.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().isTieneBeca() ? "Sí" : "No"));

        tblEstudiantes.setItems(estudiantes);
        tblEstudiantes.getSelectionModel().selectedItemProperty().addListener(
                (obs, anterior, actual) -> cargarSeleccion(actual)
        );
        actualizarContador();
        limpiarFormulario();
    }

    @FXML
    private void guardarOnClick() {
        if (!validarFormulario()) {
            return;
        }

        Estudiante seleccionado = tblEstudiantes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Estudiante nuevo = leerDatos();
            listado.agregar(nuevo);
            estudiantes.setAll(listado.obtenerRegistros());
            lblEstado.setText("Estudiante agregado correctamente.");
        } else {
            int indice = listado.obtenerRegistros().indexOf(seleccionado);
            listado.actualizar(indice, leerDatos());
            estudiantes.setAll(listado.obtenerRegistros());
            lblEstado.setText("Estudiante actualizado correctamente.");
        }

        actualizarContador();
        limpiarFormulario();
        tblEstudiantes.getSelectionModel().clearSelection();
    }

    @FXML
    private void eliminarOnClick() {
        Estudiante seleccionado = tblEstudiantes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Selecciona un estudiante", "Selecciona un registro de la tabla para eliminarlo.");
            return;
        }

        int indice = listado.obtenerRegistros().indexOf(seleccionado);
        listado.eliminar(indice);
        estudiantes.setAll(listado.obtenerRegistros());
        actualizarContador();
        limpiarFormulario();
        tblEstudiantes.getSelectionModel().clearSelection();
        lblEstado.setText("Estudiante eliminado correctamente.");
    }

    @FXML
    private void limpiarOnClick() {
        tblEstudiantes.getSelectionModel().clearSelection();
        limpiarFormulario();
        lblEstado.setText("Formulario limpio.");
    }

    private Estudiante leerDatos() {
        return new Estudiante(
                txtNombres.getText().trim(),
                txtApellidos.getText().trim(),
                txtCarrera.getText().trim(),
                dpFechaNac.getValue(),
                chkTieneBeca.isSelected()
        );
    }

    private boolean validarFormulario() {
        StringBuilder errores = new StringBuilder();

        if (txtNombres.getText() == null || txtNombres.getText().trim().isEmpty()) {
            errores.append("• Escribe los nombres.\n");
        }
        if (txtApellidos.getText() == null || txtApellidos.getText().trim().isEmpty()) {
            errores.append("• Escribe los apellidos.\n");
        }
        if (txtCarrera.getText() == null || txtCarrera.getText().trim().isEmpty()) {
            errores.append("• Escribe la carrera.\n");
        }
        LocalDate fecha = dpFechaNac.getValue();
        if (fecha == null) {
            errores.append("• Selecciona la fecha de nacimiento.\n");
        } else {
            if (fecha.isAfter(LocalDate.now())) {
                errores.append("• La fecha de nacimiento no puede ser futura.\n");
            } else if (calcularEdadNumero(fecha) > 100) {
                errores.append("• Verifica la fecha de nacimiento.\n");
            }
        }

        if (errores.length() > 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Datos incompletos", errores.toString());
            return false;
        }
        return true;
    }

    private void cargarSeleccion(Estudiante estudiante) {
        if (estudiante == null) {
            btnAgregar.setText("Agregar estudiante");
            btnEliminar.setDisable(true);
            return;
        }

        txtNombres.setText(estudiante.getNombres());
        txtApellidos.setText(estudiante.getApellidos());
        txtCarrera.setText(estudiante.getCarrera());
        dpFechaNac.setValue(estudiante.getFechaNacimiento());
        chkTieneBeca.setSelected(estudiante.isTieneBeca());
        btnAgregar.setText("Guardar cambios");
        btnEliminar.setDisable(false);
        lblEstado.setText("Editando: " + estudiante);
    }

    private void limpiarFormulario() {
        txtNombres.clear();
        txtApellidos.clear();
        txtCarrera.clear();
        dpFechaNac.setValue(null);
        chkTieneBeca.setSelected(false);
        btnAgregar.setText("Agregar estudiante");
        btnEliminar.setDisable(true);
    }

    private void actualizarContador() {
        int cantidad = estudiantes.size();
        lblRegistros.setText("Registros guardados: " + cantidad);
    }

    private String calcularEdad(LocalDate fecha) {
        return fecha == null ? "-" : calcularEdadNumero(fecha) + " años";
    }

    private int calcularEdadNumero(LocalDate fecha) {
        return Period.between(fecha, LocalDate.now()).getYears();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Registro de estudiantes");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
