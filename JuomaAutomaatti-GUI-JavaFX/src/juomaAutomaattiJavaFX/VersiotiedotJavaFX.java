/*
 *  Versiotietoikkunan pohjaluokka
 */
package juomaAutomaattiJavaFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class VersiotiedotJavaFX extends Application {

	@Override
	public void start(Stage window) {		
		Label version = new Label("Juoma-automaatti JavaFX:llä v1.0");
		
		BorderPane layout = new BorderPane();
		layout.setCenter(version);
		Scene scene = new Scene(layout, 200, 100);
		window.setScene(scene);
		window.setTitle("");
	}
}
