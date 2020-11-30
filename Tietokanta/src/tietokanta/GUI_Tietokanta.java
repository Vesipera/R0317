/*
 * 	R0317 Olio-ohjelmointi Javalla, Syksy 2020
 * 	Tehtävässä on toteutettu JDBC:n avulla pieni MySQL-tietokanta pelitietojen tallentamiseen.
 * 	Tietokanta sijaitsee Microsoftin Azure-palvelimella, joten siihen saa yhteyden vain
 *  allekirjoittaneen määrittelemistä IP-osoitteista.
 *  
 *  Käyttöliittymän ulkoasuun mallia otettu Mika Stenbergin tekemästä esimerkkisovelluksesta.
 *  
 *  Viimeksi muokattu 30.11.2020
 */

package tietokanta;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author Janne Lahdenperä <janne.lahdenpera@student.laurea.fi>
 * @version 1.0
 */
public class GUI_Tietokanta {

	// Tietokannan lokaaliin käsittelyyn luodaan globaali Pelitietoja sisältävä ArrayList
	private static ArrayList<Pelitieto> tiedot = new ArrayList<Pelitieto>();

	/**
	 * Main-metodi, joka luo käyttöliittymän ulkoasun ja lisää siihen toiminnallisuuden
	 */
	public static void main(String[] args) {

		// Luodaan tietokannan näyttävän taulukon sisältöpohja
		DefaultTableModel model = new DefaultTableModel();
		// Annetaan taulukon sarakkeille otsikot
		model.addColumn("Pelin nimi");
		model.addColumn("Julkaisuvuosi");
		model.addColumn("Kehittäjä");
		model.addColumn("Julkaisija");
		model.addColumn("Genre");

		// Luodaan taulukko annetulla pohjalla
		JTable table = new JTable(model);
		// Luodaan vierityspalkki taulukkoa varten
		JScrollPane scrollPane = new JScrollPane();

		// Luodaan valikkopalkki ja annetaan sille sisältöä
		JMenuBar menuBar = new JMenuBar();
		JMenu upkeepMenu = new JMenu("Tiedosto");
		menuBar.add(upkeepMenu);
		JMenuItem quit = new JMenuItem("Lopeta");
		upkeepMenu.add(quit);
		JMenu infomenu = new JMenu("Tietoja ohjelmasta");
		menuBar.add(infomenu);
		JMenuItem info = new JMenuItem("Versiotiedot ja tekijä");
		infomenu.add(info);

		// Luodaan paneeli käyttöliittymän napeille
		JPanel nappipaneeli = new JPanel();
		// Luodaan tarvittavat napit
		JButton haeNappi = new JButton("Hae tiedot");
		JButton lisääNappi = new JButton("Lisää peli");
		JButton poistaNappi = new JButton("Poista valittu peli");
		JButton järjestäNappi = new JButton("Järjestä tiedot");
		// Asetetaan napit paneeliin
		nappipaneeli.add(haeNappi);
		nappipaneeli.add(lisääNappi);
		nappipaneeli.add(poistaNappi);
		nappipaneeli.add(järjestäNappi);

		// Luodaan sovelluksen pääikkuna ja annetaan sille layoutmanageri
		JFrame ikkuna = new JFrame();
		BorderLayout sijoittelija = new BorderLayout();
		ikkuna.setLayout(sijoittelija);

		// Ikkunan otsikko
		ikkuna.setTitle("Pelikirjasto");
		// Asetetaan valikko ikkunan yläosaan
		ikkuna.setJMenuBar(menuBar);
		// Vieritettävä alue ikkunan keskelle
		ikkuna.add(scrollPane, BorderLayout.CENTER);
		// Taulukko vieritysalueeseen
		scrollPane.setViewportView(table);
		// Nappipaneeli ikkunan alalaitaan
		ikkuna.add(nappipaneeli, BorderLayout.SOUTH);

		// Pakataan elementit nätisti paikoilleen
		ikkuna.pack();
		// Asetetaan ikkuna ruudun keskelle
		ikkuna.setLocationRelativeTo(null);
		// Näytetään käyttöliittymä käyttäjälle
		ikkuna.setVisible(true);
		
		// Seuraavassa käyttöliittymän toiminnallisuus

		// Valikon lopeta-toiminto
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Poistutaan ohjelmasta
				System.exit(0);	
			}
		});

		// Valikon versio- ja tekijätoiminto 
		info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Luodaan uusi ikkuna Versiotiedot-luokan pohjalta
				Versiotiedot ikkuna = new Versiotiedot();
				ikkuna.setVisible(true);
			}
		});

		// Tietojen hakunappi, otetaan yhteys tietokantaan ja haetaan
		// sieltä tiedot näytettäväksi käyttäjälle
		haeNappi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Tyhjennetään taulukko siinä mahdollisesti olevasta datasta
				model.setRowCount(0);
				// Haetaan tiedot listaan
				tiedot = Tietokantaoperaatiot.haeTiedot();
				// Löydettiin tietoa, jee!
				if (tiedot != null) {	
					// Käydään lista läpi ja esitetään sen sisältö taulukossa
					for (Pelitieto tieto : tiedot) {	
						model.addRow(new Object[] { tieto.getNimi(), tieto.getJulkaisuvuosi(), tieto.getKehittäjä(),
								tieto.getJulkaisija(), tieto.getGenre() });
					}
				}
			}
		});

		// Tietojen lisäysnappi, pyydetään käyttäjältä lisättävän pelin tiedot
		// ja lisätään ne tietokantaan sekä lokaaliin listaan
		lisääNappi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Luodaan uusi lisäysikkuna
				UusiPeli syöttöikkuna = new UusiPeli();
				// Käytetään luotua ikkunaa pohjana JOptionPanelle
				int result = JOptionPane.showConfirmDialog(null, syöttöikkuna, "Uuden pelin tiedot",
						JOptionPane.OK_CANCEL_OPTION);
				// Jos käyttäjä painoi OK, jatketaan tiedon lisäämistä
				if (result == JOptionPane.OK_OPTION) {
					// Try-catch rakenne, koska lisätessä muutetaan merkkijono kokonaisluvuksi
					try {
						// Luodaan uusi tieto-olio syötettyjen arvojen pohjalta
						Pelitieto peli = new Pelitieto(syöttöikkuna.getNimi(), syöttöikkuna.getJulkaisuvuosi(),
								syöttöikkuna.getKehittäjä(), syöttöikkuna.getJulkaisija(),
								syöttöikkuna.getGenre());
						// Lisätään tiedot tietokantaan ja jatketaan eteenpäin vain jos operaatio onnistui
						if (Tietokantaoperaatiot.tallennaTieto(peli) == true) {
							// Lisätään tieto listaan
							tiedot.add(peli);
							// Näytetään tieto taulussa ja kerrotaan onnistumisesta käyttäjälle
							model.addRow(new Object[] { peli.getNimi(), peli.getJulkaisuvuosi(), peli.getKehittäjä(),
									peli.getJulkaisija(), peli.getGenre() });
							JOptionPane.showMessageDialog(null, "Tiedon lisäys onnistui.");
						}
					// Käyttäjä ei antanut kelvollista vuosilukua =(
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Et antanut toimivaa vuosilukua!", "EPÄKELPO SYÖTE!",
								JOptionPane.ERROR_MESSAGE); // Virhetilanteessa annetaan virheilmoitus
					}
				}
			}
		});

		// Tietojen poistonappi, poistaa valitun rivin tiedot sekä
		// tietokannasta että lokaalista listasta
		poistaNappi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Tarkastetaan onko käyttäjä klikannut jotain riviä ennen napin painamista
				if (table.getSelectedRow() != -1) {
					// Varmistetaan vielä että käyttäjä haluaa tehdä poiston
					int result = JOptionPane.showConfirmDialog(null,
							"Oletko varma että haluat poistaa kyseisen pelin, valintaa ei voi peruuttaa!",
							"Pelitiedon poisto", JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
					// Jatketaan jos varmistus on saatu
					if (result == JOptionPane.OK_OPTION) {
						// Haetaan pelin nimi poistettavan rivin ensimmäisestä sarakkeesta
						String poistettava = (String) model.getValueAt(table.getSelectedRow(), 0);
						// Lähetetään tieto poistettavaksi tietokannasta, suoritetaan loppu vain jos poisto onnistui 
						if (Tietokantaoperaatiot.poistaTieto(poistettava) == true) {
							// Poistetaan tieto listasta
							tiedot.remove(table.getSelectedRow());
							// Poistetaan tieto taulusta ja ilmoitetaan siitä käyttäjälle
							model.removeRow(table.getSelectedRow());
							JOptionPane.showMessageDialog(null, "Peli poistettu.");
						}
					}
				}
			}
		});

		// Nappi tietojen järjestämiseen. Tietojen järjestämisessä käytetään lokaalisti luotua
		// listarakennetta, se ei siis kommunikoi mitenkään tietokannan kanssa. Yksinkertaisempi
		// tapa olisi varmaan lähettää tietokannalle SELECT ORDER BY -queryjä, mutta se loisi
		// vain turhaa kuormitusta tietokantaan
		järjestäNappi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Luodaan valintaikkuna luokan pohjalta
				Valintaikkuna ikkuna = new Valintaikkuna();
				// Kysytään käyttäjältä miten hän haluaa tiedot järjestää
				int result = JOptionPane.showConfirmDialog(null, ikkuna, "", JOptionPane.OK_CANCEL_OPTION);
				// Saatiin käyttäjältä vahvistus
				if (result == JOptionPane.OK_OPTION) {
					// Haetaan annettu valinta omaan muuttujaan
					String valinta = ikkuna.annaValinta();
					if (valinta != null) {
						// Järjestetään tiedot valinnan mukaan
						järjestäTiedot(valinta);
						// Tyhjennetään taulukko siinä mahdollisesti olevasta datasta
						model.setRowCount(0);
						// Käydään järjestetty lista läpi ja näytetään sen tiedot taulukossa
						for (Pelitieto tieto : tiedot) {
							model.addRow(new Object[] { tieto.getNimi(), tieto.getJulkaisuvuosi(), tieto.getKehittäjä(),
									tieto.getJulkaisija(), tieto.getGenre() });
						}
					}
				}
			}
		});
	}

	/**
	 * Metodi järjestää Pelitieto-olioita sisältävän listan annetun parametrin mukaan
	 * 
	 * @param parametri Järjestämisessä käytettävä attribuutti
	 */
	private static void järjestäTiedot(String parametri) {
		// Luodaan Comparator-olio vertailua varten
		Comparator<Pelitieto> vertailija;

		// Toiminnallisuus eri parametreilla, annetaan Comparator-oliolle
		// järjestämiseen käytettävä olion muuttuja
		switch (parametri) {
		case "Nimi":
			vertailija = Comparator.comparing(Pelitieto::getNimi);
			break;
		case "Julkaisuvuosi":
			vertailija = Comparator.comparing(Pelitieto::getJulkaisuvuosi);
			break;
		case "Kehittäjä":
			vertailija = Comparator.comparing(Pelitieto::getKehittäjä);
			break;
		case "Julkaisija":
			vertailija = Comparator.comparing(Pelitieto::getJulkaisija);
			break;
		case "Genre":
			vertailija = Comparator.comparing(Pelitieto::getGenre);
			break;
		default:
			vertailija = null;
			// Tänne ei pitäisi ohjelmassa päätyä, mutta annetaan varmuuden vuoksi käyttäjälle virheilmoitus
			JOptionPane.showMessageDialog(null, "Virhe tietojen järjestämisessä!", "VIRHE!", JOptionPane.ERROR_MESSAGE);
		}

		// Järjestetään lista annettujen tietojen pohjalta
		Collections.sort(tiedot, vertailija);
	}
}
