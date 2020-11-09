package dad.javafx.email;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	Controller controlador;
	@Override
	public void start(Stage primaryStage) throws Exception {
		controlador = new Controller();
		
		Scene scene = new Scene(controlador.getView(), 600, 400);
		primaryStage.setTitle("Enviar email");
		primaryStage.getIcons().add(new Image("email-send-icon-32x32.png"));
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

}
