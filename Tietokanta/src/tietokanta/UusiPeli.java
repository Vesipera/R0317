/*
 *  Luokka pelin lisäämisen yhteydessä esiin tulevaa ikkunaa varten
 *  
 *  Viimeksi muokattu 30.11.2020
 */
package tietokanta;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Janne Lahdenperä <janne.lahdenpera@student.laurea.fi>
 * @version 1.0
 */
@SuppressWarnings("serial")
public class UusiPeli extends JPanel {
	
	// Ikkunan tarvitseman muuttujat
	private JTextField nimi;
	private JTextField julkaisuvuosi;
	private JTextField kehittäjä;
	private JTextField julkaisija;
	private JComboBox<String> genre;
	
	public UusiPeli() {
		// Alustetaan syöttökentät tarvittaville tiedoille
		nimi = new JTextField(20);
		julkaisuvuosi = new JTextField(10);
		kehittäjä = new JTextField(20);
		julkaisija = new JTextField(20);
		// Alasvetovalikon sisältö
		String[] genres = {"Tasohyppely", "Toiminta", "3d-ammuskelu", "Roolipeli",
				"Roguelite", "Strategia", "Urheilu", "Puzzle", "Muu"};
		// Valikko genreä varten
		genre = new JComboBox<String> (genres);
		
		// Annetaan ikkunalle sopiva asettelu
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Lisätään tarvittavat komponentit päällekkäin
		add(new JLabel("Pelin nimi:"));
		add(nimi);
		add(new JLabel("Pelin julkaisuvuosi:"));
		add(julkaisuvuosi);
		add(new JLabel("Pelin kehittäjä:"));
		add(kehittäjä);
		add(new JLabel("Pelin julkaisija:"));
		add(julkaisija);
		add(new JLabel("Pelin genre:"));
		add(genre);
	}
	
	// Getterit tarvittavien tietojen hakemiseen
	
	public String getNimi() {
		return nimi.getText();
	}
	
	// Virheenkäsittelyyn numeromuutoksessa tarvitaan throws-määre
	public int getJulkaisuvuosi() throws NumberFormatException {
		return Integer.parseInt(julkaisuvuosi.getText());
	}
	
	public String getKehittäjä() {
		return kehittäjä.getText();
	}
	
	public String getJulkaisija() {
		return julkaisija.getText();
	}
	
	public String getGenre() {
		return (String)genre.getSelectedItem();
	}
}
