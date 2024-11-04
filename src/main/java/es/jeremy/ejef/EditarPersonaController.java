package es.jeremy.ejef;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * La clase {@code EditarPersonaController} controla la ventana de edición de una persona.
 * Permite modificar los datos de una persona existente y actualizar la tabla de personas en el controlador principal.
 */
public class EditarPersonaController {

    /** Campo de texto para el nombre de la persona. */
    @FXML
    private TextField nombreField;

    /** Campo de texto para los apellidos de la persona. */
    @FXML
    private TextField apellidosField;

    /** Campo de texto para la edad de la persona. */
    @FXML
    private TextField edadField;

    /** Botón para guardar los cambios realizados en los datos de la persona. */
    @FXML
    private Button guardarButton;

    /** Botón para cancelar la edición y cerrar la ventana. */
    @FXML
    private Button cancelarButton;

    /** Controlador padre que maneja la tabla de personas en la vista principal. */
    private HelloController parentController;

    /** Persona que se está editando en esta ventana. */
    private Persona personaEdicion;

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
     * Carga los datos de una persona seleccionada en los campos de texto para su edición.
     *
     * @param persona La persona cuyos datos serán cargados para su edición.
     */
    public void cargarDatos(Persona persona) {
        this.personaEdicion = persona; // Guardar la referencia de la persona
        nombreField.setText(persona.getNombre());
        apellidosField.setText(persona.getApellidos());
        edadField.setText(String.valueOf(persona.getEdad()));
    }

    /**
     * Guarda los cambios realizados en los campos de texto. Valida que los campos no estén vacíos
     * y que la edad sea un número válido. Actualiza los datos de la persona en edición.
     */
    private void guardarPersona() {
        String nombre = nombreField.getText();
        String apellidos = apellidosField.getText();
        String edadStr = edadField.getText();

        // Validación de datos
        if (nombre.isEmpty() || apellidos.isEmpty() || edadStr.isEmpty()) {
            mostrarAlerta("Todos los campos son obligatorios.");
            return;
        }

        try {
            int edad = Integer.parseInt(edadStr);
            // Actualizar los datos de la persona en edición
            personaEdicion.setNombre(nombre);
            personaEdicion.setApellidos(apellidos);
            personaEdicion.setEdad(edad);

            // Notificar al controlador padre que los datos han cambiado
            parentController.actualizarTabla();

            // Cerrar la ventana
            cerrarVentana();
        } catch (NumberFormatException e) {
            // Manejar el error de conversión si la edad no es un número válido
            mostrarAlerta("La edad debe ser un número válido.");
        }
    }

    /**
     * Cancela la edición y cierra la ventana sin guardar los cambios.
     */
    private void cancelar() {
        cerrarVentana();
    }

    /**
     * Cierra la ventana actual.
     */
    private void cerrarVentana() {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Muestra una alerta informativa al usuario.
     *
     * @param mensaje El mensaje que se mostrará en la alerta.
     */
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Establece el controlador padre, que permite la interacción con la vista principal.
     *
     * @param parentController El controlador de la vista principal.
     */
    public void setParentController(HelloController parentController) {
        this.parentController = parentController;
    }
}
