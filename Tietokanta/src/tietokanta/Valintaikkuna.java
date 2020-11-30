/*
 *  Luokka tietojen järjestelemisen yhteydessä näkyvän ikkunan luomiseen
 *  
 *  Viimeksi muokattu 30.11.2020
 */
package tietokanta;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * @author Janne Lahdenperä <janne.lahdenpera@student.laurea.fi>
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Valintaikkuna extends JPanel {

	// Ikkunan tarvitsemat muuttujat
	private JRadioButton nimi;
	private JRadioButton julkaisuvuosi;
	private JRadioButton kehittäjä;
	private JRadioButton julkaisija;
	private JRadioButton genre;

	public Valintaikkuna() {
		// Alustetaan tarvittavat napit
		nimi = new JRadioButton("Nimi");
		julkaisuvuosi = new JRadioButton("Julkaisuvuosi");
		kehittäjä = new JRadioButton("Kehittäjä");
		julkaisija = new JRadioButton("Julkaisija");
		genre = new JRadioButton("Genre");

		// Asetetaan napit ryhmään, jotta vain yhden voi valita kerrallaan
		ButtonGroup valinnat = new ButtonGroup();
		valinnat.add(nimi);
		valinnat.add(julkaisuvuosi);
		valinnat.add(kehittäjä);
		valinnat.add(julkaisija);
		valinnat.add(genre);

		// Annetaan ikkunalle sopiva asettelu
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		// Esittetään kysymys käyttäjälle
		add(new JLabel("Minkä mukaan järjestetään?"));
		// Ladotaan valitsimet näkyviin
		add(nimi);
		add(julkaisuvuosi);
		add(kehittäjä);
		add(julkaisija);
		add(genre);
	}

	/**
	 * Metodi hakee aktiivisena olevan valintanapin
	 * 
	 * @return Käyttäjän klikkaama valinta
	 */
	public String annaValinta() {
		// Käydään napit läpi ja palautetaan aktiivisena olevan teksti
		if (nimi.isSelected()) {
			return nimi.getText();
		} else if (julkaisuvuosi.isSelected()) {
			return julkaisuvuosi.getText();
		} else if (kehittäjä.isSelected()) {
			return kehittäjä.getText();
		} else if (julkaisija.isSelected()) {
			return julkaisija.getText();
		} else if (genre.isSelected()) {
			return genre.getText();
		} else {
			// Mitään ei valittu =(
			return null;
		}
	}
}
