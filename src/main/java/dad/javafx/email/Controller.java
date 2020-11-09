package dad.javafx.email;

import java.io.IOException;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller {

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
	private TextField usarConeText;

	@FXML
	private TextField emailText;

	@FXML
	private TextArea mensajeArea;

	@FXML
	private TextField contrase�aText;

	@FXML
	private TextField emailDesText;

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

	
	@FXML
	void onEnviarAction(ActionEvent event) throws EmailException {
		
		Alert bien = new Alert(AlertType.CONFIRMATION);
		Alert error = new Alert(AlertType.ERROR);
		
		try {
			Email email = new SimpleEmail();
			email.setHostName(model.getServidor());
			email.setSmtpPort(model.getPuerto());
			email.setAuthenticator(new DefaultAuthenticator(model.getRemitente(), model.getRemitentePass()));
			email.setSSLOnConnect(model.isSsl());
			email.setFrom(model.getRemitente());
			email.setSubject(model.getAsunto());
			email.setMsg(model.getMensaje());
			email.addTo(model.getDestinatario());
			email.send();
			
			bien.setTitle("Mensaje enviado");
			bien.setContentText("Mensaje enviado con �xito a" + "\'" +  model.getDestinatario() + "\'.");
			bien.showAndWait();
			
			model.setDestinatario("");
			model.setAsunto("");
			model.setMensaje("");
		} catch (EmailException e) {
			//e.printStackTrace();
			error.setTitle("Error");
			error.setContentText("No se pudo enviar el email");
			error.setHeaderText(e.getMessage());
			error.showAndWait();
		}
	}
	
	@FXML
	void onCerrarAction(ActionEvent event) {
		Stage stage = (Stage) view.getScene().getWindow();
		stage.close();
	}

	

	@FXML
	void onVaciarAction(ActionEvent event) {
		servidorText.textProperty().set("");
		checkBox.setSelected(false);
		usarConeText.textProperty().set("");
		emailText.textProperty().set("");
		contrase�aText.textProperty().set("");
		emailDesText.textProperty().set("");
		asuntoText.textProperty().set("");
		mensajeArea.textProperty().set("");

		
	}

	public GridPane getView() {
		return view;
	}
	
	
}
