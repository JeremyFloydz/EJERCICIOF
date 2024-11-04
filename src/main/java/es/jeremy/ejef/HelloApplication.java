package es.jeremy.ejef;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

/**
 * La clase {@code HelloApplication} es la clase principal para iniciar una aplicación
 * JavaFX que carga una interfaz de usuario definida en un archivo FXML.
 * Extiende la clase {@code Application} de JavaFX, que es el punto de entrada
 * para cualquier aplicación JavaFX.
 */
public class HelloApplication extends Application {

    /**
     * Este método se llama al iniciar la aplicación JavaFX.
     * Es responsable de configurar la ventana principal de la aplicación,
     * cargar la escena desde un archivo FXML, y establecer el icono de la ventana.
     *
     * @param stage El escenario principal proporcionado por la plataforma JavaFX.
     * @throws IOException Si hay un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        // Crear la escena con las dimensiones adecuadas
        Scene scene = new Scene(fxmlLoader.load(), 734, 474);

        // Establecer el título de la ventana
        stage.setTitle("Personas");

        // Cargar el logo como imagen
        Image icon = new Image(getClass().getResourceAsStream("/img/agenda.png"));
        stage.getIcons().add(icon); // Establecer el ícono de la ventana

        // Mostrar la escena en la ventana
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método principal que lanza la aplicación JavaFX.
     * Este método invoca {@link #launch(String...)} que es proporcionado por la clase {@code Application}
     * para inicializar la aplicación.
     *
     * @param args Los argumentos de línea de comandos, si los hay.
     */
    public static void main(String[] args) {
        // Iniciar la aplicación
        launch();
    }
}
