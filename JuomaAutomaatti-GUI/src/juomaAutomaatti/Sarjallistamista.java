/*
 *  Valmis apuluokka olion kirjoittamiseen xml-tiedostoon, ei tehty muutoksia
 */
package juomaAutomaatti;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Sarjallistamista {

	// Otetaan vastaan juoma-automaatti-olio ja kirjoitetaan se XML-muotoisena
	// tiedostoon
	public static void kirjoitaTiedostoon(JuomaAutomaatti ja) throws IOException {

		// XML:n kirjoittamista varten

		FileOutputStream tiedosto = new FileOutputStream("Automaatti.xml");
		XMLEncoder enc = new XMLEncoder(new BufferedOutputStream(tiedosto));

		// Kirjoitetaan olio XML-muotoiseen tiedostoon
		enc.writeObject(ja);

		// Lopputoimet tiedostoille
		enc.close();
		tiedosto.close();
	}

	public static JuomaAutomaatti lueTiedostosta() throws FileNotFoundException {

		XMLDecoder dec = null;
		FileInputStream tiedosto = new FileInputStream("Automaatti.xml");
		dec = new XMLDecoder(new BufferedInputStream(tiedosto));
		JuomaAutomaatti luettu = (JuomaAutomaatti) dec.readObject();

		// Lopputoimet
		dec.close();

		// Palautetaan tiedostosta luettu automaatti
		return luettu;

	}

}