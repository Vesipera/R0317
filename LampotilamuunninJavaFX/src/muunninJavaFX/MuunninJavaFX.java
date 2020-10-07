/*
 *  R0317 Olio-ohjelmointi Javalla, Syksy 2020
 *  Celsius-Fahrenheit -muuntimen toteutus JavaFX:llä
 *  
 *  Viimeksi muokattu 7.10.2020
 */

package muunninJavaFX;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author Janne Lahdenperä <janne.lahdenpera@student.laurea.fi> 
 * @version 1.0
 */
public class MuunninJavaFX extends Application {

	/**
	 * JavaFX-päämetodi
	 * <p>
	 * Luodaan käyttöliittymän ikkuna ja kaikki tarvittavat komponentit.
	 * 
	 * @param window sovelluksen ikkuna
	 */
	@Override
	public void start(Stage window) {					// Luodaan käyttöliittymän komponentit
		Label degreesLabel = new Label("Celsius:");		// Teksti
		Button button = new Button("Convert");			// Nappi
		button.setMinWidth(100);						// Määritellään napin koko
		TextField degreesField = new TextField();		// Syöttökenttä
		Label result = new Label("");					// Tulosalue

		GridPane layout = new GridPane();					// Luodaan ruudukkoasettelu
		layout.setPadding(new Insets(0, 10, 10, 0));		// Määritellään sille hieman paddingia
		layout.setHgap(10);									// Asetetaan hieman tilaa elemanttien välille
		layout.setVgap(10);
		layout.add(degreesLabel, 1, 1);						// Asetellaan komponentit ruudukkoon
		GridPane.setHalignment(degreesLabel, HPos.RIGHT);	// Keskitetään teksti oikeaan laitaan
		layout.add(button, 1, 2);
		layout.add(degreesField, 2, 1);
		layout.add(result, 2, 2);

		button.setOnAction((event) -> {				// Luodaan napille toiminto
			convert(degreesField, result);
		});

		degreesField.setOnAction((event) -> {		// Luodaan enterin painamiselle syötekentässä toiminto
			convert(degreesField, result);
		});
		
		Scene scene = new Scene(layout);			// Annetaan asettelu Scene-oliolle
		window.setScene(scene);						// Asetetaan Scene ikkunaan
		window.setTitle("Celsius Converter");		// Annetaan ikkunalle otsikko
		degreesField.requestFocus();				// Asetetaan focus syötekenttään
		window.show();								// Näytetään ikkuna käyttäjälle
	}

	/**
	 * Metodi muuntaa Celsius-lämpötilan Fahrenheit-asteikolle
	 * <p>
	 * Jos lukukenttään kirjoitettu arvo ei ole kelvollinen, tulostetaan virheilmoitus
	 * 
	 * @param source kenttä, josta muunnettava arvo luetaan
	 * @param output kenttä, johon muunnettava arvo tulostetaan
	 */
	private static void convert(TextField source, Label output) {

		if (!source.getText().isBlank()) {						// Jos lukukenttä on tyhjä, ei tehdä mitään
			try {
				double celsius = Double.valueOf(source.getText()); 	// Luetaan alkuperäinen arvo double-muuttujaan
				double fahrenheit = celsius * 1.8 + 32; 			// Muunnetaan lämpötila Fahrenheit-asteikolle
				output.setText("Fahrenheit: " + fahrenheit);		// Näytetään tulos
				source.requestFocus();						// Siirretään fokus takaisin tekstikenttään
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR);		// Epäkelvon syötteen kohdalla tulostetaan virheilmoitus
				alert.setTitle("INVALID INPUT");
				alert.setHeaderText(null);
				alert.setContentText("Input only numerical data and remember to use period as decimal point!");
				alert.showAndWait();
			}
		}
	}

	/**
	 * Ohjelman päämetodi
	 * <p>
	 * Metodissa ainoastaan käynnistetään JavaFX:n start()-metodi.
	 * 
	 * @param args komentoriviparametrit, ei käytetä
	 */
	public static void main(String[] args) {
		launch(MuunninJavaFX.class);
	}
}
