/*
 *  Ohjeikkunan pohjaluokka
 */
package juomaAutomaattiJavaFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class OhjeJavaFX extends Application {

	@Override
	public void start(Stage window) {
		Label version = new Label("Napista saa juomaa! Jos juoma loppuu, yläpalkista saa lisää!");

		BorderPane layout = new BorderPane();
		layout.setCenter(version);
		Scene scene = new Scene(layout, 400, 100);
		window.setScene(scene);
		window.setTitle("");

	}

}
