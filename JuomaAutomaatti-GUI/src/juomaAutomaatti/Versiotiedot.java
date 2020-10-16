/*
 *  Versiotietoikkuna juoma-automaatille
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
public class Versiotiedot extends JFrame {
	
	private JLabel versiotiedot;
	
	public Versiotiedot() {
		setTitle("Versiotietoja");			// Asetetaan ikkunan otsikko ja koko
		setSize(300, 200);
		setLocationRelativeTo(null);		// Asetetaan ikkuna keskelle ruutua
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		// Luodaan ruudulle tuleva teksti, jotta asemointi toimii JLabelin sisällä,
		// laitetaan teksti <html> tagien sisään
		versiotiedot = new JLabel("<html>Juoma-automaatti v. 1.0<br><br>Tekijä: Janne Lahdenperä</html>");
		versiotiedot.setFont(new Font("Tahoma", Font.BOLD, 18));
		versiotiedot.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(versiotiedot, BorderLayout.CENTER);
		
	}
}
