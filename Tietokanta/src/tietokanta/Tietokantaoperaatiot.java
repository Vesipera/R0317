/*
 *  Luokka tietokantaoperaatioita varten
 *  Luokan metodit määritelty static- määreellä, niin niitä voi
 *  käyttää ilman, että luokasta luo ensin oliota
 *  
 *  Viimeksi muokattu 30.11.2020
 */
package tietokanta;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * @author Janne Lahdenperä <janne.lahdenpera@student.laurea.fi>
 * @version 1.0
 */
public class Tietokantaoperaatiot {

	// Tietokantayhteyteen tarvittavat tiedot, palvelimelle pääsee vain erikseen määritellyistä
	// IP-osoitteista ja salasana on satunnaisesti luotu ja uniikki, joten uskallan jättää sen tähän näkyviin
	static final String URL = "jdbc:sqlserver://mysqlserver29112020.database.windows.net:1433;database=mySampleDatabase;user=azureuser@mysqlserver29112020;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
	static final String USERNAME = "azureuser";
	static final String PASSWORD = "jUM18!l$f9x5";

	/**
	 * Metodi hakee tietokannasta siellä olevat tiedot ja palauttaa ne listana
	 * 
	 * @return Tietokannasta haetut tiedot ArrayList-muodossa
	 */
	public static ArrayList<Pelitieto> haeTiedot() {
		try {
			// Luodaan yhteys tietokantaan
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// Luodaan uusi kysely
			Statement stmt = con.createStatement();
			// Suoritetaan kysely ja otetaan tulokset talteen
			ResultSet rs = stmt.executeQuery("SELECT * FROM Pelikirjasto;");
			// Luodaan palautettava ArrayList-olio
			ArrayList<Pelitieto> palautuslista = new ArrayList<Pelitieto>();
			
			// Käydään tulosjoukko läpi ja lisätään tiedot palautettavaan listaan
			while (rs.next()) {
				palautuslista.add(new Pelitieto(rs.getString(1), rs.getInt(2), rs.getString(3)
						, rs.getString(4), rs.getString(5)));
			}
			
			// Suljetaan yhteys
			con.close();
			// Palautetaan tiedot
			return palautuslista;
		} catch (Exception e) {
			// Virhetilanteessa ilmoitetaan siitä käyttäjälle ja palautetaan null
			JOptionPane.showMessageDialog(null, e.getMessage(), "TIETOKANTAONGELMA!",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	/**
	 * Metodi tallentaa tietokantaan sille annetun olion
	 * 
	 * @param tallennettava Tietokantaan tallennettava Pelitieto-olio
	 * @return Tieto siitä onnistuiko tallennus
	 */
	public static boolean tallennaTieto(Pelitieto tallennettava) {
		try {
			// Avataan tallennettava olio ja asetetaan sen tiedot erillisiin muuttujiin
			String nimi = tallennettava.getNimi();
			int julkaisuvuosi = tallennettava.getJulkaisuvuosi();
			String kehittäjä = tallennettava.getKehittäjä();
			String julkaisija = tallennettava.getJulkaisija();
			String genre = tallennettava.getGenre();
			
			// Luodaan yhteys tietokantaan
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// Esitellään tarvittava SQL-lause
			String sql = "INSERT INTO Pelikirjasto VALUES (?,?,?,?,?)";
			// Luodaan edellisen lauseen pohjalta prepareStatement-olio
			PreparedStatement preparedStmt = con.prepareStatement(sql);
			
			// Täydennetään lauseesta puuttuvat tiedot
			preparedStmt.setString(1, nimi);
			preparedStmt.setInt(2, julkaisuvuosi);
			preparedStmt.setString(3, kehittäjä);
			preparedStmt.setString(4, julkaisija);
			preparedStmt.setString(5, genre);
			// Suoritetaan SQL-lause
			preparedStmt.execute();
			
			// Suljetaan yhteys
			con.close();
			// Palautetaan tieto siitä, että kaikki onnistui
			return true;
		} catch (Exception e) {
			// Virheen sattuessa kerrotaan siitä käyttäjälle
			JOptionPane.showMessageDialog(null, e.getMessage(), "TIETOKANTAONGELMA!",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	/**
	 * Metodi poistaa tietokannasta sille parametrina annetun tiedon
	 * 
	 * @param poistettava Poistettavan pelin nimi
	 * @return Tieto siitä onnistuiko poisto
	 */
	public static boolean poistaTieto(String poistettava) {
		try {
			// Luodaan yhteys tietokantaan
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// Luodaan uusi kysely
			Statement stmt = con.createStatement();
			// Annetaan lauseen sisältö
			String sql = "DELETE FROM Pelikirjasto WHERE nimi='" + poistettava + "'";
			// Suoritetaan SQL-lause
			stmt.executeUpdate(sql);
			
			// Suljetaan yhteys
			con.close();
			// Kerrotaan onnistumisesta käyttäjälle
			return true;
		} catch (Exception e) {
			// Virhetilanne!! Kerrotaan siitä käyttäjälle
			JOptionPane.showMessageDialog(null, e.getMessage(), "TIETOKANTAONGELMA!",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
}
