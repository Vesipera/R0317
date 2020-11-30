/*
 *   Versiotietoikkuna tietokantasovellukseen
 *   
 *   Viimeksi muokattu 30.11.2020
 */

package tietokanta;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;

/**
 * @author Janne Lahdenperä <janne.lahdenpera@student.laurea.fi>
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Versiotiedot extends JFrame {
	
	private JLabel versiotiedot;
	
	public Versiotiedot() {
		// Asetetaan ikkunan otsikko ja koko
		setTitle("Versiotietoja");			
		setSize(300, 200);
		// Asetetaan ikkuna keskelle ruutua
		setLocationRelativeTo(null);		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		// Luodaan ruudulle tuleva teksti. Jotta asemointi toimii JLabelin sisällä,
		// laitetaan teksti <html> tagien sisään
		versiotiedot = new JLabel("<html>Tietokantaohjelma v. 1.0<br><br>Tekijä: Janne Lahdenperä</html>");
		versiotiedot.setFont(new Font("Tahoma", Font.BOLD, 18));
		versiotiedot.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(versiotiedot, BorderLayout.CENTER);	
	}
}