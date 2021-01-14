package dad.javafx.email;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class Controller implements Initializable {

	public Controller() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EnviarEmail.fxml"));
		loader.setController(this);
		loader.load();

		enviarBoton.setOnAction(e -> {
			try {
				onEnviarAction(e);
			} catch (EmailException e1) {
				e1.printStackTrace();
			}
		});

		vaciarBoton.setOnAction(e -> onVaciarAction(e));

		cerrarBoton.setOnAction(e -> onCerrarAction(e));

	}

	Model model = new Model();

	@FXML
	private GridPane view;

	@FXML
	private Label servidorLabel;

	@FXML
	private Label conexionLabel;

	@FXML
	private Label remitenteLabel;

	@FXML
	private Label destinatarioLabel;

	@FXML
	private Label asuntoLabel;

	@FXML
	private TextField servidorText;

	@FXML
	private TextField puertoText;

	@FXML
	private TextField emailText;

	@FXML
	private TextArea mensajeArea;

	@FXML
	private PasswordField passField;

	@FXML
	private TextField destinatarioText;

	@FXML
	private TextField asuntoText;

	@FXML
	private VBox botonera;

	@FXML
	private Button enviarBoton;

	@FXML
	private Button vaciarBoton;

	@FXML
	private Button cerrarBoton;

	@FXML
	private CheckBox checkBox;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Intento de bindings del puerto
			//Se puede poner el puertoProperty() como String en el modelo para hacer un bindeo normal, ya que no nos hace fa�ta la integer property para hacer ninguna operaci�n
		 //model.puertoProperty().bind(puertoText.textProperty());
		Bindings.bindBidirectional(puertoText.textProperty(), model.puertoProperty(), new NumberStringConverter());

		// ------------------------------------------------------
		model.servidorProperty().bind(servidorText.textProperty());

		model.sslProperty().bind(checkBox.selectedProperty());
		model.remitenteProperty().bind(emailText.textProperty());
		model.remitentePassProperty().bind(passField.textProperty());
		model.destinatarioProperty().bind(destinatarioText.textProperty());
		model.asuntoProperty().bind(asuntoText.textProperty());
		model.mensajeProperty().bind(mensajeArea.textProperty());

	}

	@FXML
	void onEnviarAction(ActionEvent event) throws EmailException {
		int puerto = model.getPuerto();

		Alert bien = new Alert(AlertType.CONFIRMATION);
		Alert error = new Alert(AlertType.ERROR);
		System.out.println(puerto);
		try {
			Email email = new SimpleEmail();
			email.setHostName(model.getServidor());
			email.setSmtpPort(puerto);
			email.setAuthenticator(new DefaultAuthenticator(model.getRemitente(), model.getRemitentePass()));
			email.setSSLOnConnect(model.isSsl());
			email.setFrom(model.getRemitente());
			email.setSubject(model.getAsunto());
			email.setMsg(model.getMensaje());
			email.addTo(model.getDestinatario());
			email.send();
			
			bien.setTitle("Mensaje enviado");
			bien.setContentText("Mensaje enviado con éxito a " + model.getDestinatario() + ".");
			bien.showAndWait();
			
			destinatarioText.clear();
			asuntoText.clear();
			mensajeArea.clear();

			

		} catch (EmailException e) {
			error.setTitle("Error");
			error.setContentText(e.getMessage());
			error.setHeaderText("No se pudo enviar el email");
			error.showAndWait();
		}
	}

	@FXML
	void onCerrarAction(ActionEvent event) {
//		Stage stage = (Stage) view.getScene().getWindow();
//		stage.close();
		Platform.exit();
	}

	@FXML
	void onVaciarAction(ActionEvent event) {
		servidorText.clear();
		checkBox.setSelected(false);
		puertoText.clear();
		emailText.clear();
		passField.clear();
		destinatarioText.clear();
		asuntoText.clear();
		mensajeArea.clear();

	}

	public GridPane getView() {
		return view;
	}

	

}
