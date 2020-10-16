/*
 *  Ohjeikkuna juoma-automaatin käyttöön
 */
package juomaAutomaatti;

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
public class Ohje extends JFrame {

	private JLabel ohjetiedot;

	public Ohje() {
		setTitle("Ohje"); 				// Asetetaan ikkunan otsikko ja koko
		setSize(300, 200);
		setLocationRelativeTo(null); 	// Asetetaan ikkuna keskelle ruutua
		getContentPane().setLayout(new BorderLayout(0, 0));

		// Luodaan ruudulle tuleva teksti, jotta asemointi toimii JLabelin sisällä,
		// laitetaan teksti <html> tagien sisään
		ohjetiedot = new JLabel("<html>Napista saa juomaa!<br><br>Jos juoma loppuu,<br>yläpalkista voi lisätä!</html>");
		ohjetiedot.setFont(new Font("Tahoma", Font.BOLD, 18));
		ohjetiedot.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(ohjetiedot, BorderLayout.CENTER);

	}

}
