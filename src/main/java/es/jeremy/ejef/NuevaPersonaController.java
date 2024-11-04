package es.jeremy.ejef;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * La clase {@code NuevaPersonaController} controla la ventana de creación de una nueva persona.
 * Permite ingresar los datos de una persona y luego enviarlos al controlador principal para
 * agregarlos a la lista de personas.
 */
public class NuevaPersonaController {

    /** Campo de texto para el nombre de la persona. */
    @FXML
    private TextField nombreField;

    /** Campo de texto para los apellidos de la persona. */
    @FXML
    private TextField apellidosField;

    /** Campo de texto para la edad de la persona. */
    @FXML
    private TextField edadField;

    /** Botón para guardar los datos ingresados. */
    @FXML
    private Button guardarButton;

    /** Botón para cancelar la operación y cerrar la ventana. */
    @FXML
    private Button cancelarButton;

    /** Controlador padre que maneja la lista de personas en la vista principal. */
    private HelloController parentController;

    /**
     * Inicializa el controlador de la vista, asignando las acciones a los botones.
     * Este método es llamado automáticamente después de cargar el archivo FXML.
     */
    @FXML
    public void initialize() {
        // Asignar la acción al botón de guardar
        guardarButton.setOnAction(e -> guardarPersona());
        // Asignar la acción al botón de cancelar
        cancelarButton.setOnAction(e -> cancelar());
    }

    /**
     * Establece el controlador padre. Esto es necesario para que el controlador
     * de esta ventana pueda interactuar con el controlador principal.
     *
     * @param parentController El controlador de la vista principal.
     */
    public void setParentController(HelloController parentController) {
        this.parentController = parentController;
    }

    /**
     * Guarda los datos ingresados en los campos de texto y los envía al controlador padre.
     * Valida que los campos no estén vacíos y que la edad sea un número válido.
     */
    private void guardarPersona() {
        // Obtener los datos de los campos de texto
        String nombre = nombreField.getText();
        String apellidos = apellidosField.getText();
        int edad;

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty() || apellidos.isEmpty() || edadField.getText().isEmpty()) {
            mostrarAlerta("Error", "Por favor, completa todos los campos.");
            return;
        }

        try {
            // Intentar convertir el campo de edad a un número entero
            edad = Integer.parseInt(edadField.getText());

            // Crear una nueva persona y agregarla al controlador padre
            Persona nuevaPersona = new Persona(nombre, apellidos, edad);
            parentController.agregarPersona(nuevaPersona); // Agregar a la lista del controlador padre

            // Cerrar la ventana
            Stage stage = (Stage) guardarButton.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            // Manejar el error de conversión si la edad no es un número válido
            mostrarAlerta("Error", "La edad debe ser un número válido.");
        }
    }

    /**
     * Cancela la operación actual y cierra la ventana sin realizar cambios.
     */
    private void cancelar() {
        // Cerrar la ventana sin realizar ninguna acción
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Muestra una alerta informativa en la ventana.
     *
     * @param titulo  El título de la alerta.
     * @param mensaje El mensaje que se mostrará en la alerta.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
