/*
 *  R0317 Olio-ohjelmointi Javalla, Syksy 2020
 *  Tehtävässä on toteutettu graafinen käyttöliittymä lämpötilamuuntimelle (Celsius -> Fahrenheit)
 *  Muunninta voi käyttää sekä 'Convert'-napista että tekstikentässä enteriä painamalla
 *  
 *  Viimeksi muokattu 5.10.2020
 */

package lampotilamuunnin;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * @author Janne Lahdenperä <janne.lahdenpera@student.laurea.fi>
 * @version 1.0
 */
public class Lampotilamuunnin {
	
	/**
	 * Ohjelman päämetodi
	 * <p>
	 * Luodaan käyttöliittymän ikkuna ja kaikki tarvittavat komponentit.
	 * 
	 * @param args komentoriviparametrit, ei käytetä
	 */
	public static void main(String[] args) {

		JFrame window = new JFrame("Celsius Converter");		// Luodaan ikkuna
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(400, 100);
		window.setLocationRelativeTo(null);						// Keskitetään ikkuna näytölle

		// Luodaan käyttöliittymän komponentit
		JLabel degreesLabel = new JLabel("Celsius:", JLabel.RIGHT);				// Keskitetään Label oikeaan reunaan
		degreesLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));	// ja laitetaan sille 10 pikseliä täytettä
		JTextField degreesField = new JTextField(20);
		JButton button = new JButton("Convert");	
		JLabel result = new JLabel("");				// Luodaan kenttä tulokselle

		GridLayout layout = new GridLayout(2, 2);  	// Luodaan 2x2 ruudukko asettelua varten
		layout.setHgap(10);							// ja laitetaan siihen hieman väljyyttä
		
		window.setLayout(layout);			// Määritellään ikkunalle asettelu 
		window.add(degreesLabel);			// ja asetellaan kaikki komponentit paikoilleen
		window.add(degreesField);
		window.add(button);
		window.add(result);
		
		button.addActionListener(new ActionListener() {		// Lisätään nappiin kuuntelija
			@Override
			public void actionPerformed(ActionEvent e) {
				convert(degreesField, result);
			}
		});
		
		degreesField.addActionListener(new ActionListener() {  // Lisätään samanlainen kuuntelija tekstikentälle
			@Override
			public void actionPerformed(ActionEvent e) {
				convert(degreesField, result);
			}
		});

		window.setVisible(true);
	}
	
	/**
	 * Metodi muuntaa Celsius-lämpötilan Fahrenheit-asteikolle
	 * <p>
	 * Jos lukukenttään kirjoitettu arvo ei ole kelvollinen, tulostetaan virheilmoitus
	 * 
	 * @param source kenttä, josta muunnettava arvo luetaan
	 * @param output kenttä, johon muunnettava arvo tulostetaan
	 */
	private static void convert(JTextField source, JLabel output) {
		
		if (!source.getText().isBlank()) {	// Jos lukukenttä on tyhjä, ei tehdä mitään
			try {
				double celsius = Double.valueOf(source.getText()); 	// Luetaan alkuperäinen arvo double-muuttujaan
				double fahrenheit = celsius * 1.8 + 32;				// Muunnetaan lämpötila Fahrenheit-asteikolle
				output.setText("Fahrenheit: " + fahrenheit);		// Tulostetaan arvo kohdekenttään
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,		// Jos luettava arvo on virheellinen, tulostetaan virheilmoitus
						"Input only numerical data and remember to use period as decimal point!", "INVALID INPUT!",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
