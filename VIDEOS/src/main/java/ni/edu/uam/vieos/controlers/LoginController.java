package ni.edu.uam.vieos.controlers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ni.edu.uam.vieos.EstudianteApplication;

public class LoginController {
    private static final String USUARIO = "admin";
    private static final String CLAVE = "1234";

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtClave;
    @FXML private Label lblError;

    @FXML
    private void iniciarSesion() {
        lblError.setText("");

        String usuario = txtUsuario.getText().trim();
        String clave = txtClave.getText();

        if (usuario.isEmpty() || clave.isEmpty()) {
            lblError.setText("Completa el usuario y la contraseña.");
            return;
        }

        if (USUARIO.equalsIgnoreCase(usuario) && CLAVE.equals(clave)) {
            abrirAplicacion();
        } else {
            lblError.setText("Usuario o contraseña incorrectos.");
            txtClave.clear();
            txtClave.requestFocus();
        }
    }

    @FXML
    private void limpiarLogin() {
        txtUsuario.clear();
        txtClave.clear();
        lblError.setText("");
        txtUsuario.requestFocus();
    }

    private void abrirAplicacion() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    EstudianteApplication.class.getResource("/ni/edu/uam/vieos/estudiante-view.fxml")
            );
            Scene scene = new Scene(loader.load(), 900, 600);

            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setTitle("Registro de estudiantes");
            stage.setResizable(true);
            stage.setMinWidth(820);
            stage.setMinHeight(560);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("VIDEOS");
            alert.setHeaderText("No se pudo abrir el sistema");
            alert.setContentText("Ocurrió un error al cargar la pantalla principal.\n\n" + e.getMessage());
            alert.showAndWait();
        }
    }
}
