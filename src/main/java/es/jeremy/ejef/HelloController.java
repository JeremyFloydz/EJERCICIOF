package es.jeremy.ejef;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

/**
 * La clase {@code HelloController} actúa como el controlador principal de la interfaz de usuario.
 * Gestiona las acciones relacionadas con la tabla de personas, que incluye agregar, modificar, eliminar,
 * exportar e importar datos. Además, ofrece funcionalidad de filtrado y alertas.
 */
public class HelloController {

    /** Tabla que muestra las personas. */
    @FXML
    private TableView<Persona> tableView;

    /** Columna de la tabla que muestra los nombres de las personas. */
    @FXML
    private TableColumn<Persona, String> nombreColumn;

    /** Columna de la tabla que muestra los apellidos de las personas. */
    @FXML
    private TableColumn<Persona, String> apellidosColumn;

    /** Columna de la tabla que muestra las edades de las personas. */
    @FXML
    private TableColumn<Persona, Integer> edadColumn;

    /** Botón para agregar nuevas personas. */
    @FXML
    private Button agregarButton;

    /** Botón para modificar personas seleccionadas. */
    @FXML
    private Button modificarButton;

    /** Botón para eliminar personas seleccionadas. */
    @FXML
    private Button eliminarButton;

    /** Botón para exportar la lista de personas a un archivo CSV. */
    @FXML
    private Button exportarButton;

    /** Botón para importar una lista de personas desde un archivo CSV. */
    @FXML
    private Button importarButton;

    /** Campo de texto para filtrar personas por nombre. */
    @FXML
    private TextField filtroNombreField;

    /** Lista observable que almacena las personas a mostrar en la tabla. */
    private ObservableList<Persona> personas;

    /**
     * Método de inicialización de JavaFX. Configura las columnas de la tabla,
     * asigna acciones a los botones y habilita el filtrado de la tabla.
     */
    @FXML
    public void initialize() {
        personas = FXCollections.observableArrayList();
        tableView.setItems(personas);

        // Configurar las columnas de la tabla
        nombreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        apellidosColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellidos()));
        edadColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEdad()).asObject());

        // Asignar acciones a los botones
        agregarButton.setOnAction(e -> agregarPersona());
        modificarButton.setOnAction(e -> modificarPersona());
        eliminarButton.setOnAction(e -> eliminarPersona());
        exportarButton.setOnAction(e -> exportarCSV());
        importarButton.setOnAction(e -> importarCSV());

        // Filtrar la tabla según el texto ingresado en el campo de filtro
        filtroNombreField.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarTabla(newValue);
        });
    }

    /**
     * Abre una ventana modal para agregar una nueva persona.
     * Si se añade correctamente, la persona será añadida a la lista.
     */
    private void agregarPersona() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ventana.fxml"));
            Parent root = loader.load();

            NuevaPersonaController controller = loader.getController();
            controller.setParentController(this);  // Establecer el controlador padre

            Stage stage = new Stage();
            stage.setTitle("Nueva Persona");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);  // Modalidad de la ventana
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de agregar persona.");
        }
    }

    /**
     * Modifica los datos de la persona seleccionada en la tabla.
     * Si no hay persona seleccionada, se muestra una advertencia.
     */
    private void modificarPersona() {
        Persona personaSeleccionada = tableView.getSelectionModel().getSelectedItem();
        if (personaSeleccionada != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("editarventana.fxml"));
                Parent root = loader.load();

                EditarPersonaController controller = loader.getController();
                controller.setParentController(this);  // Establecer el controlador padre
                controller.cargarDatos(personaSeleccionada);  // Cargar los datos de la persona

                Stage stage = new Stage();
                stage.setTitle("Modificar Persona");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo abrir la ventana de editar persona.");
            }
        } else {
            mostrarAlerta("Advertencia", "Por favor, selecciona una persona para modificar.");
        }
    }

    /**
     * Elimina la persona seleccionada en la tabla.
     * Si no hay persona seleccionada, se muestra una advertencia.
     */
    private void eliminarPersona() {
        Persona personaSeleccionada = tableView.getSelectionModel().getSelectedItem();
        if (personaSeleccionada != null) {
            personas.remove(personaSeleccionada);
            mostrarAlerta("Éxito", "Persona eliminada con éxito.");
        } else {
            mostrarAlerta("Advertencia", "Por favor, selecciona una persona para eliminar.");
        }
    }

    /**
     * Agrega una nueva persona a la lista, si no existe ya en la misma.
     *
     * @param nuevaPersona La nueva persona a agregar.
     */
    public void agregarPersona(Persona nuevaPersona) {
        if (!personas.contains(nuevaPersona)) {
            personas.add(nuevaPersona);
            mostrarAlerta("Éxito", "Persona agregada con éxito.");
        } else {
            mostrarAlerta("Error", "Esta persona ya existe en la lista.");
        }
    }

    /**
     * Muestra una alerta informativa.
     *
     * @param titulo  El título de la alerta.
     * @param mensaje El contenido del mensaje de la alerta.
     */
    public void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Refresca el contenido de la tabla, útil después de realizar modificaciones.
     */
    public void actualizarTabla() {
        tableView.refresh();
    }

    /**
     * Filtra la tabla de personas según el nombre ingresado.
     * Si el campo de búsqueda está vacío, muestra todas las personas.
     *
     * @param nombre El texto con el que se filtra la tabla.
     */
    private void filtrarTabla(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            tableView.setItems(personas);
        } else {
            ObservableList<Persona> filtradas = FXCollections.observableArrayList();
            for (Persona persona : personas) {
                if (persona.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                    filtradas.add(persona);
                }
            }
            tableView.setItems(filtradas);
        }
    }

    /**
     * Exporta la lista de personas a un archivo CSV seleccionado por el usuario.
     * Cada persona se guarda en una línea en el formato: Nombre, Apellidos, Edad.
     */
    private void exportarCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Nombre,Apellidos,Edad\n");
                for (Persona persona : personas) {
                    writer.write(persona.getNombre() + "," + persona.getApellidos() + "," + persona.getEdad() + "\n");
                }
                mostrarAlerta("Éxito", "Datos exportados correctamente.");
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo exportar los datos.");
            }
        }
    }

    /**
     * Importa una lista de personas desde un archivo CSV seleccionado por el usuario.
     * Cada línea debe contener datos en el formato: Nombre, Apellidos, Edad.
     */
    private void importarCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                reader.readLine();  // Ignorar la primera línea (cabecera)

                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length == 3) {
                        String nombre = data[0];
                        String apellidos = data[1];
                        int edad = Integer.parseInt(data[2]);

                        Persona nuevaPersona = new Persona(nombre, apellidos, edad);
                        if (!personas.contains(nuevaPersona)) {
                            personas.add(nuevaPersona);
                        } else {
                            mostrarAlerta("Advertencia", "La persona " + nombre + " ya existe en la lista.");
                        }
                    } else {
                        mostrarAlerta("Error", "Línea inválida en el archivo: " + line);
                    }
                }
                mostrarAlerta("Éxito", "Datos importados correctamente.");
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo importar los datos.");
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "El archivo contiene datos inválidos.");
            }
        }
    }
}
