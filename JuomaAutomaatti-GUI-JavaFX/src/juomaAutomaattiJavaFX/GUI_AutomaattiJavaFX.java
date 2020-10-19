/*
 *  R0317 Olio-ohjelmointi Javalla, Syksy 2020
 *  Tehtävässä on portattu aiemmin Swingillä tehty käyttöliittymä JavaFX:lle
 *  
 *  Ulkonäkö aika askeettinen, koodissa keskitytty enemmän toiminnallisuuteen
 *  
 *  Jonkin verran tyylimäärittelyjä (esim. nappien kuvat) tehty erillisessä css-tiedostossa
 *  
 *  Viimeksi muokattu 19.10.2020
 */
package juomaAutomaattiJavaFX;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * @author Janne Lahdenperä <janne.lahdenpera@student.laurea.fi>
 * @version 1.0
 */
public class GUI_AutomaattiJavaFX extends Application {

	private Label coffeeAmount;		// Esitellään tarvittavat luokkamuuttujat
	private Label teaAmount;
	private Label cocoaAmount;

	/**
	 * JavaFX-päämetodi
	 * <p>
	 * Luodaan käyttöliittymän ikkuna ja kaikki tarvittavat komponentit.
	 * 
	 * @param window sovelluksen ikkuna
	 */
	@Override
	public void start(Stage window) {
		try {
			JuomaAutomaatti ja = new JuomaAutomaatti();		// Luodaan uusi juoma-automaatti
			
			MenuBar menuBar = new MenuBar();				// Luodaan yläreunan valikkorypäs
			Menu upkeepMenu = new Menu("Ylläpito");
			MenuItem addCoffee = new MenuItem("Aseta kahvin määrä");
			MenuItem addTea = new MenuItem("Aseta teen määrä");
			MenuItem addCocoa = new MenuItem("Aseta kaakaon määrä");
			MenuItem saveAmounts = new MenuItem("Tallenna automaatin tila");
			MenuItem loadAmounts = new MenuItem("Lataa automaatti");
			MenuItem menuExit = new MenuItem("Lopeta");
			Menu infoMenu = new Menu("Tietoja ohjelmasta");
			MenuItem versionInfo = new MenuItem("Versiotiedot");
			MenuItem help = new MenuItem("Ohje");
			menuBar.getMenus().add(upkeepMenu);
			upkeepMenu.getItems().add(addCoffee);
			upkeepMenu.getItems().add(addTea);
			upkeepMenu.getItems().add(addCocoa);
			upkeepMenu.getItems().add(saveAmounts);
			upkeepMenu.getItems().add(loadAmounts);
			upkeepMenu.getItems().add(menuExit);
			menuBar.getMenus().add(infoMenu);
			infoMenu.getItems().add(versionInfo);
			infoMenu.getItems().add(help);

			Button coffeeButton = new Button();		// Luodaan napit ja annetaan niille
			coffeeButton.setId("button1");			// id-arvot css-määrittelyjä varten
			Button teaButton = new Button();
			teaButton.setId("button2");
			Button cocoaButton = new Button();
			cocoaButton.setId("button3");
			
			coffeeAmount = new Label("Kahvia: " + ja.getKahvi());	// Luodaan tuloskentät ja 
			coffeeAmount.setId("result1");							// annetaan niillekin id-arvot
			teaAmount = new Label("Teetä: " + ja.getTee());
			teaAmount.setId("result2");
			cocoaAmount = new Label("Kaakaota: " + ja.getKaakao());
			cocoaAmount.setId("result3");
			
			Label coffeeLabel = new Label("Kahvi");	// Määritellään nappitekstit ja niiden id-arvot
			coffeeLabel.setId("label1");
			Label teaLabel = new Label("Tee");
			teaLabel.setId("label2");
			Label cocoaLabel = new Label("Kaakao");
			cocoaLabel.setId("label3");

			/*
			 *  Käyttöliittymän asettelu koostuu kolmesta vaakasuuntaisesta laatikosta,
			 *  joissa on jokaisessa yksi nappi ja tuloskenttä. Nämä on kaikki asetettu
			 *  pystysuuntaiseen laatikkoon siten että ylimpänä on valikkorivi, ja
			 *  seuraavaksi aina vaakalaatikko, jonka jälkeen on nappiteksti
			 */
			HBox eka = new HBox();						// Luodaan ensimmäinen vaakalaatikko 
			eka.setPadding(new Insets(20, 0, 0, 20));	// Asetetaan laatikon yläpuolelle ja vasemmalle tilaa 
			eka.setSpacing(50);							// Asetetaan elementtien väliin tilaa
			eka.getChildren().add(coffeeButton);		// Lisätään nappi laatikkoon
			eka.getChildren().add(coffeeAmount);		// Lisätään tuloskenttä laatikkoon

			HBox toka = new HBox();						// Luodaan toinen vaakalaatikko
			toka.setPadding(new Insets(20, 0, 0, 20));
			toka.setSpacing(50);
			toka.getChildren().add(teaButton);
			toka.getChildren().add(teaAmount);

			HBox kolmas = new HBox();					// Luodaan kolmas vaakalaatikko
			kolmas.setPadding(new Insets(20, 0, 0, 20));
			kolmas.setSpacing(50);
			kolmas.getChildren().add(cocoaButton);
			kolmas.getChildren().add(cocoaAmount);

			VBox layout = new VBox();					// Luodaan lopullisena asettelupohjana toimiva pystylaatikko 
			layout.setSpacing(5);						// Asetetaan elementtien väliin tilaa
			layout.getChildren().add(menuBar);			// Lisätään valikko ruudun yläosaan
			layout.getChildren().add(eka);				// Lisätään ensimmäinen laatikko
			layout.getChildren().add(coffeeLabel);		// Lisätään nappiteksti
			layout.getChildren().add(toka);				// Lisätään toinen laatikko
			layout.getChildren().add(teaLabel);			// Lisätään nappiteksti
			layout.getChildren().add(kolmas);			// Lisätään kolmas laatikko
			layout.getChildren().add(cocoaLabel);       // Lisätään nappiteksti

			/*
			 *  Seuraavassa asetetaan kaikkien valikkojen ja nappien toiminnallisuus.
			 *  En tiedä olisiko nämä parempi määritellä heti elementtien määrittelyn
			 *  yhteydessä, mutta nyt olen toteuttanut ne kaikki tänne.  
			 */
			addCoffee.setOnAction((event) -> {		// Kahvin lisäys valikosta
				int newCoffee = inputAmount(); 		// Pyydetään uusi määrä
				if (newCoffee >= 0) { 				// Asetetaan uusi määrä vain jos se on kelvollinen
					ja.setKahvi(newCoffee);
					updateAmountsAndColours(ja);	// Päivitetään tiedot näytölle
				}
			});
			
			addTea.setOnAction((event) -> {			// Teen lisäys valikosta
				int newTea = inputAmount(); 		// Pyydetään uusi määrä
				if (newTea >= 0) { 					// Asetetaan uusi määrä vain jos se on kelvollinen
					ja.setTee(newTea);
					updateAmountsAndColours(ja);	// Päivitetään tiedot näytölle
				}
			});
			
			addCocoa.setOnAction((event) -> {		// Kaakaon lisäys valikosta
				int newCocoa = inputAmount(); 		// Pyydetään uusi määrä
				if (newCocoa >= 0) { 				// Asetetaan uusi määrä vain jos se on kelvollinen
					ja.setKaakao(newCocoa);
					updateAmountsAndColours(ja);	// Päivitetään tiedot näytölle
				}
			});
			
			saveAmounts.setOnAction((event) -> {	// Tallennusvalinnan toiminnallisuus
				try {
					SarjallistamistaJavaFX.kirjoitaTiedostoon(ja);	// Kirjoitetaan automaatin tila tiedostoon
					Alert alert = new Alert(AlertType.INFORMATION);	// Ilmoitetaan käyttäjälle onnistumisesta
					alert.setTitle("");
					alert.setHeaderText(null);
					alert.setContentText("Tiedot tallennettu.");
					alert.showAndWait();
				} catch (IOException e) {
					Alert alert = new Alert(AlertType.ERROR);	// Ilmoitetaan käyttäjälle epäonnistumisesta
					alert.setTitle("Virhe!");
					alert.setHeaderText(null);
					alert.setContentText("Virhe tietojen kirjoittamisessa!");
					alert.showAndWait();
				}
			});

			loadAmounts.setOnAction((event) -> {	// Latausvalinnan toiminnallisuus
				try {
					JuomaAutomaatti uusi = SarjallistamistaJavaFX.lueTiedostosta(); // Ladataan automaatti tiedostosta
					ja.setKahvi(uusi.getKahvi());
					ja.setTee(uusi.getTee());
					ja.setKaakao(uusi.getKaakao());
					updateAmountsAndColours(ja);	// Päivitetään tiedot ja värit
					
					Alert alert = new Alert(AlertType.INFORMATION);	// Ilmoitetaan käyttäjälle onnistumisesta
					alert.setTitle("");
					alert.setHeaderText(null);
					alert.setContentText("Tiedot ladattu onnistuneesti.");
					alert.showAndWait();
				} catch (FileNotFoundException e) {
					Alert alert = new Alert(AlertType.ERROR);	// Ilmoitetaan käyttäjälle epäonnistumisesta
					alert.setTitle("Virhe!");
					alert.setHeaderText(null);
					alert.setContentText("Virhe tietojen lukemisessa!");
					alert.showAndWait();
				}
			});
			
			menuExit.setOnAction((event) -> {	// Lopetusvalinnan toiminnallisuus
				Platform.exit();
				System.exit(0);
			});
			
			versionInfo.setOnAction((event) -> {	// Versiovalinnan toiminnallisuus
				VersiotiedotJavaFX newWindow = new VersiotiedotJavaFX();	// Luodaan uusi ikkuna luokan pohjalta
				Stage newStage = new Stage();
				newWindow.start(newStage);		// Käynnistetään uusi ikkuna
				newStage.show();				// ja laitetaan se näkyviin
			});
			
			help.setOnAction((event) -> {			// Ohjevalinnan toiminnallisuus
				OhjeJavaFX newWindow = new OhjeJavaFX();	// Luodaan uusi ikkuna luokan pohjalta
				Stage newStage = new Stage();
				newWindow.start(newStage);		// Käynnistetään uusi ikkuna
				newStage.show();				// ja laitetaan se näkyviin
			});

			coffeeButton.setOnAction((event) -> {	// Kahvinapin toiminnallisuus
				ja.valmistaKahvi();
				updateAmountsAndColours(ja);
			});

			teaButton.setOnAction((event) -> {		// Teenapin toiminnallisuus
				ja.valmistaTee();
				updateAmountsAndColours(ja);
			});

			cocoaButton.setOnAction((event) -> {	// Kaakaonapin toiminnallisuus
				ja.valmistaKaakao();
				updateAmountsAndColours(ja);
			});

			Scene scene = new Scene(layout, 350, 650);	// Käyttöliittymän koko
			scene.getStylesheets().add(getClass().getResource("automaatti.css").toExternalForm());	// Käytetty css-tiedosto
			window.setScene(scene);
			window.setTitle("JavaFX-automaatti v1.0");	// Ikkunan otsikko
			window.show();								// Näytetään käyttöliittymä käyttäjälle
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodi raaka-ainemäärien asettamiseen
	 * 
	 * @return Metodissa annettu positiivinen kokonaisluku, tai virhetilanteessa -1
	 */
	private int inputAmount() {
		try {
			TextInputDialog input = new TextInputDialog();	// Luodaan ikkuna uuden arvon kysymiseen
			input.setTitle("Input");
			input.setHeaderText(null);
			input.setContentText("Anna uusi arvo:");
			Optional<String> result = input.showAndWait();	// Asetetaan annettu arvo muuttujaan

			if (result.isPresent()) {							// Jos käyttäjä antaa jonkin syötteen,
				int newAmount = Integer.parseInt(result.get());	// parsitaan se kokonaisluvuksi
				if (newAmount < 0 || newAmount > 100000) {	// Arvon ollessa väärän kokoinen, heitetään poikkeus
					throw new Exception();
				}
				return newAmount;
			}
			
			return -1;	// Painettiin cancel, ei annettu arvoa
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);		// Virhetilanteessa ilmoitetaan käyttäjälle
			alert.setTitle("EPÄKELPO SYÖTE!");
			alert.setHeaderText(null);
			alert.setContentText("Et antanut sopivan kokoista kokonaislukua! "
					+ "(Hyväksyttävät syötteet välillä 0-100000)");
			alert.showAndWait();
			return -1;
		}
	}

	/**
	 * Metodi päivittää ruudulla näkyvät raaka-ainetiedot juoma-automaatin tietoja vastaaviksi
	 * <p>
	 * Jos jotain raaka-ainetta on jäljellä alle 20 yksikköä, esitetään se punaisella värillä
	 * 
	 * @param ja Käsiteltävä JuomaAutomaatti-olio
	 */
	private void updateAmountsAndColours(JuomaAutomaatti ja) {
		coffeeAmount.setText("Kahvia: " + ja.getKahvi());		// Päivitetään ruudun tiedot
		teaAmount.setText("Teetä: " + ja.getTee());
		cocoaAmount.setText("Kaakaota: " + ja.getKaakao());

		if (ja.getKahvi() < 20) { 					// Muutetaan ainemäärien väri ainetietojen mukaan
			coffeeAmount.setTextFill(Color.RED);
		} else {
			coffeeAmount.setTextFill(Color.BLACK);
		}

		if (ja.getTee() < 20) {
			teaAmount.setTextFill(Color.RED);
		} else {
			teaAmount.setTextFill(Color.BLACK);
		}

		if (ja.getKaakao() < 20) {
			cocoaAmount.setTextFill(Color.RED);
		} else {
			cocoaAmount.setTextFill(Color.BLACK);
		}
	}

	/**
	 * Sovelluksen main-metodi, käynnistää käyttöliittymän
	 * 
	 * @param args komentoriviparametrit, ei käytetä
	 */
	public static void main(String[] args) {
		launch(GUI_AutomaattiJavaFX.class);
	}
}
