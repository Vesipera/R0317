/*
 *  R0317 Olio-ohjelmointi Javalla, Syksy 2020
 *  Tehtävässä on toteutettu graafinen käyttöliittymä aiemmin kurssilla tehdylle juoma-automaatille.
 *  Tehtävän yhteydessä harjoiteltu Eclipsen WindowBuilderin käyttöä.
 *  
 *  Pohjautuu Mika Stenbergin valmiiksi laatimaan käyttöliittymään
 *  
 *  Viimeksi muokattu 16.10.2020
 */
package juomaAutomaatti;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * @author Janne Lahdenperä <janne.lahdenpera@student.laurea.fi>
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GUI_Automaatti extends JFrame {

	// Luokkamuuttujat esitellään täällä jotta komponentteihin 
	// voidaan viitata mistä tahansa luokan sisältä

	JPanel contentPane;
	JLabel coffeeAmount;	// JLabelit esitelty täällä, jotta kaikki niihin liittyvä
	JLabel teaAmount;		// toiminnallisuus toimii
	JLabel cocoaAmount;

	/**
	 * Main-metodi, joka käynnistää sovelluksen
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Luodaan ensmin uusi JuomaAutomaatti-olio
					JuomaAutomaatti ja = new JuomaAutomaatti();

					// Käytt�liittymä saa parametrina olion, jonka tiedot se näyttää
					GUI_Automaatti frame = new GUI_Automaatti(ja);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Konstruktorissa rakennetaan käyttöliittymä. Huomaa, että otetaan parametrina
	 * vastaan alussa luotu juoma-automaatti. Tämä siksi, että voidaan näyttää sen
	 * tiedot GUI:ssa
	 */
	public GUI_Automaatti(JuomaAutomaatti ja) {

		// Ikkunan otsikko ja koko

		setTitle("Juoma-automaatti GUI v. 1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 465, 705);
		setLocationRelativeTo(null);	// Asetetaan ikkuna keskelle ruutua

		JMenuBar menuBar = new JMenuBar();	// Luodaan valikkorivi ja asetetaan se ikkunan yläosaan
		setJMenuBar(menuBar);

		JMenu upkeepMenu = new JMenu("Ylläpito");	// Luodaan ensimmäinen valikko
		menuBar.add(upkeepMenu);					// ja annetaan sille 6 alavalintaa

		JMenuItem addCoffee = new JMenuItem("Aseta kahvin määrä");	// Valinta kahvin määrän asettamiselle
		addCoffee.addActionListener(new ActionListener() {			// Asetetaan kuuntelija
			public void actionPerformed(ActionEvent arg0) {			

				int newCoffee = inputAmount();					// Pyydetään uusi määrä
				if (newCoffee >= 0) {							// Asetetaan uusi määrä vain jos se on kelvollinen
					ja.setKahvi(newCoffee);
					updateAmountsAndColours(ja);	// Päivitetään tiedot näytölle
				}
			}
		});
		upkeepMenu.add(addCoffee);					// Asetetaan valinta valikkoon				
																	 
		JMenuItem addTea = new JMenuItem("Aseta teen määrä");		// Valinta teen määrän asettamiselle
		addTea.addActionListener(new ActionListener() {				// Asetetaan kuuntelija
			public void actionPerformed(ActionEvent arg0) {

				int newTea = inputAmount();						// Pyydetään uusi määrä			
				if (newTea >= 0) {								// Asetetaan uusi määrä vain jos se on kelvollinen
					ja.setTee(newTea);
					updateAmountsAndColours(ja);		// Päivitetään tiedot näytölle
				}
			}
		});
		upkeepMenu.add(addTea);						// Asetetaan valinta valikkoon		

		JMenuItem addCocoa = new JMenuItem("Aseta kaakaon määrä");	// Valinta kaakaon määrän asettamiselle
		addCocoa.addActionListener(new ActionListener() {			// Asetetaan kuuntelija
			public void actionPerformed(ActionEvent arg0) {

				int newCocoa = inputAmount();					// Pyydetään uusi määrä		
				if (newCocoa >= 0) {							// Asetetaan uusi määrä vain jos se on kelvollinen
					ja.setKaakao(newCocoa);
					updateAmountsAndColours(ja);		// Päivitetään tiedot näytölle
				}
			}
		});
		upkeepMenu.add(addCocoa);					// Asetetaan valinta valikkoon	

		JMenuItem saveAmounts = new JMenuItem("Tallenna automaatin tila");	// Valinta automaatin tilan tallentamiselle
		saveAmounts.addActionListener(new ActionListener() {				// Asetetaan kuuntelija
			public void actionPerformed(ActionEvent arg0) {
				try {											// Käytetään Sarjallistamista-luokkaa tietojen
					Sarjallistamista.kirjoitaTiedostoon(ja);	// kirjoittamiseen ja tulostetaan viesti onnistumisesta
					JOptionPane.showMessageDialog(null, "Tiedot tallennettu", "", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e) {					// Virhetilanteessa tulostetaan virheilmoitus
					JOptionPane.showMessageDialog(null, "Virhe tietojen kirjoittamisessa!", "VIRHE!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		upkeepMenu.add(saveAmounts);				// Asetetaan valinta valikkoon

		JMenuItem loadAmounts = new JMenuItem("Lataa automaatti");	// Valinta tallennetun tilanteen lataamiselle
		loadAmounts.addActionListener(new ActionListener() {		// Asetetaan kuuntelija
			public void actionPerformed(ActionEvent arg0) {
				try {															// Käytetään Sarjallistamista-luokkaa
					JuomaAutomaatti uusi = Sarjallistamista.lueTiedostosta();	// tietojen lataamiseen
					ja.setKahvi(uusi.getKahvi());
					ja.setTee(uusi.getTee());								// Luetaan olion tila tiedostosta,
					ja.setKaakao(uusi.getKaakao());							// asetetaan ladatut arvot automaattiin
					updateAmountsAndColours(ja);							// ja päivitetään ne ruudulle
					
					JOptionPane.showMessageDialog(null, "Tiedot ladattu onnistuneesti", "", 
							JOptionPane.INFORMATION_MESSAGE);	// Ilmoitetaan onnistuneesta latauksesta
					
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "Virhe tietojen lukemisessa!", "VIRHE!",
							JOptionPane.ERROR_MESSAGE);			// Virheen sattuessa ilmoitetaan siitä käyttäjälle
				}
			}
		});
		upkeepMenu.add(loadAmounts);				// Asetetaan valinta valikkoon

		JMenuItem quit = new JMenuItem("Lopeta");				// Valinta ohjelman lopettamiselle
		quit.addActionListener(new ActionListener() {			// Asetetaan kuuntelija
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);									// Lopetetaan ohjelma
			}
		});			
		upkeepMenu.add(quit);						// Asetetaan valinta valikkoon

		JMenu helpMenu = new JMenu("Tietoja ohjelmasta");	// Luodaan toinen valikko ja annetaan sille 2 alavalintaa
		menuBar.add(helpMenu);

		JMenuItem versionInfo = new JMenuItem("Versiotiedot");	// Valinta versiotietojen tulostamiselle
		versionInfo.addActionListener(new ActionListener() {	// Lisätään kuuntelija
			public void actionPerformed(ActionEvent arg0) {
				Versiotiedot ikkuna = new Versiotiedot();		// Luodaan versiotietoikkuna ja asetetaan se näkyväksi
				ikkuna.setVisible(true);
			}
		});
		helpMenu.add(versionInfo);					// Asetetaan valinta valikkoon

		JMenuItem help = new JMenuItem("Ohje");					// Valinta ohjeikkunan tulostamiselle
		help.addActionListener(new ActionListener() {			// Lisätään kuuntelija
			public void actionPerformed(ActionEvent arg0) {
				Ohje ikkuna = new Ohje();						// Luodaan ohjeikkuna ja asetetaan se näkyväksi
				ikkuna.setVisible(true);
			}
		});
		helpMenu.add(help);							// Asetetaan valinta valikkoon

		contentPane = new JPanel();								// Luodaan käyttöliittymän pääpaneeli
		contentPane.setBorder(new EmptyBorder(5, 10, 5, 5));	// Pääosa koodista on WindowBuilderin generoimaa
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 173, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 36, 143, 14, 31, 149, 14, 31, 143, 14, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JButton coffeeButton = new JButton("");					// Kahvintilausnappi
		coffeeButton.setIcon(new ImageIcon(GUI_Automaatti.class.getResource("/img/coffee.jpg")));	// Napin kuva
		GridBagConstraints gbc_coffeeButton = new GridBagConstraints();
		gbc_coffeeButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_coffeeButton.insets = new Insets(0, 0, 5, 5);
		gbc_coffeeButton.gridx = 0;
		gbc_coffeeButton.gridy = 1;
		coffeeButton.addActionListener(new ActionListener() {	// Asetetaan napille kuuntelija
			public void actionPerformed(ActionEvent arg0) {
				ja.valmistaKahvi();								// Tilataan kahvia
				updateAmountsAndColours(ja);					// Päivitetään tiedot ruudulle
			}
		});
		contentPane.add(coffeeButton, gbc_coffeeButton);

		coffeeAmount = new JLabel("Kahvia: " + ja.getKahvi());			// Kahvin määrän näyttävä kenttä
		coffeeAmount.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
		GridBagConstraints gbc_coffeeAmount = new GridBagConstraints();
		gbc_coffeeAmount.insets = new Insets(0, 0, 5, 5);
		gbc_coffeeAmount.gridx = 2;
		gbc_coffeeAmount.gridy = 1;
		contentPane.add(coffeeAmount, gbc_coffeeAmount);

		JLabel coffeeLabel = new JLabel("Kahvi");						// Kahvinapin alla oleva teksti
		coffeeLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		GridBagConstraints gbc_coffeeLabel = new GridBagConstraints();
		gbc_coffeeLabel.anchor = GridBagConstraints.NORTH;
		gbc_coffeeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_coffeeLabel.gridx = 0;
		gbc_coffeeLabel.gridy = 2;
		contentPane.add(coffeeLabel, gbc_coffeeLabel);

		JButton teaButton = new JButton("");					// Teentilausnappi
		teaButton.setIcon(new ImageIcon(GUI_Automaatti.class.getResource("/img/tea.jpg")));	// Napin kuva
		GridBagConstraints gbc_teaButton = new GridBagConstraints();
		gbc_teaButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_teaButton.insets = new Insets(0, 0, 5, 5);
		gbc_teaButton.gridx = 0;
		gbc_teaButton.gridy = 4;
		teaButton.addActionListener(new ActionListener() {		// Asetetaan napille kuuntelija
			public void actionPerformed(ActionEvent arg0) {		
				ja.valmistaTee();								// Tilataan teetä
				updateAmountsAndColours(ja);					// ja päivitetään tiedot ruudulle
			}
		});
		contentPane.add(teaButton, gbc_teaButton);

		teaAmount = new JLabel("Teetä: " + ja.getTee());		// Teen määrän näyttävä kenttä
		teaAmount.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
		GridBagConstraints gbc_teaAmount = new GridBagConstraints();
		gbc_teaAmount.insets = new Insets(0, 0, 5, 5);
		gbc_teaAmount.gridx = 2;
		gbc_teaAmount.gridy = 4;
		contentPane.add(teaAmount, gbc_teaAmount);

		JLabel teaLabel = new JLabel("Tee");					// Teenapin alla oleva teksti
		teaLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		GridBagConstraints gbc_teaLabel = new GridBagConstraints();
		gbc_teaLabel.anchor = GridBagConstraints.NORTH;
		gbc_teaLabel.insets = new Insets(0, 0, 5, 5);
		gbc_teaLabel.gridx = 0;
		gbc_teaLabel.gridy = 5;
		contentPane.add(teaLabel, gbc_teaLabel);

		JButton cocoaButton = new JButton("");					// Kaakaontilausnappi
		cocoaButton.setIcon(new ImageIcon(GUI_Automaatti.class.getResource("/img/cocoa.jpg")));	// Napin kuva
		GridBagConstraints gbc_cocoaButton = new GridBagConstraints();
		gbc_cocoaButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_cocoaButton.insets = new Insets(0, 0, 5, 5);
		gbc_cocoaButton.gridx = 0;
		gbc_cocoaButton.gridy = 7;
		cocoaButton.addActionListener(new ActionListener() {	// Asetetaan napille kuuntelija
			public void actionPerformed(ActionEvent arg0) {
				ja.valmistaKaakao();							// Tilataan kaakaota
				updateAmountsAndColours(ja);					// ja päivitetään tiedot ruudulle
			}
		});
		contentPane.add(cocoaButton, gbc_cocoaButton);

		cocoaAmount = new JLabel("Kaakaota: " + ja.getKaakao());	// Kaakaon määrän näyttävä kenttä
		cocoaAmount.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
		GridBagConstraints gbc_cocoaAmount = new GridBagConstraints();
		gbc_cocoaAmount.insets = new Insets(0, 0, 5, 5);
		gbc_cocoaAmount.gridx = 2;
		gbc_cocoaAmount.gridy = 7;
		contentPane.add(cocoaAmount, gbc_cocoaAmount);

		JLabel cocoaLabel = new JLabel("Kaakao");					// Kaakaonapin alla oleva teksti
		cocoaLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		GridBagConstraints gbc_cocoaLabel = new GridBagConstraints();
		gbc_cocoaLabel.insets = new Insets(0, 0, 0, 5);
		gbc_cocoaLabel.anchor = GridBagConstraints.NORTH;
		gbc_cocoaLabel.gridx = 0;
		gbc_cocoaLabel.gridy = 8;
		contentPane.add(cocoaLabel, gbc_cocoaLabel);
	}

	/**
	 * Metodi raaka-ainemäärien asettamiseen
	 * 
	 * @return Metodissa annettu positiivinen kokonaisluku, tai virhetilanteessa -1
	 */
	private int inputAmount() {
		try {
			String newAmount = JOptionPane.showInputDialog(null, "Anna uusi arvo: ");	// Kysytään uutta arvoa
			int newAmountInt = Integer.parseInt(newAmount);				// Muutetaan vastausteksti kokonaisluvuksi

			if (newAmountInt < 0 || newAmountInt > 100000) {	// Jos luku on negatiivinen tai liian iso, heitetään poikkeus
				throw new Exception();
			}

			return newAmountInt; 	// Palautetaan annettu luku
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Et antanut sopivan kokoista kokonaislukua! "
					+ "(Hyväksyttävät syötteet välillä 0-100000)", "EPÄKELPO SYÖTE!",
					JOptionPane.ERROR_MESSAGE);	// Virhetilanteessa annetaan virheilmoitus
			return -1;							// ja palautetaan -1
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
		
		coffeeAmount.setText("Kahvia: " + ja.getKahvi());		// Påivitetään ruudulla näkyvät määrät
		teaAmount.setText("Teetä: " + ja.getTee());
		cocoaAmount.setText("Kaakaota: " + ja.getKaakao());
		
		if (ja.getKahvi() < 20) {								// Muutetaan ainemäärien väri ainetietojen mukaan
			coffeeAmount.setForeground(Color.RED);				
		} else {
			coffeeAmount.setForeground(Color.BLACK);
		}
		
		if (ja.getTee() < 20) {
			teaAmount.setForeground(Color.RED);
		} else {
			teaAmount.setForeground(Color.BLACK);
		}
		
		if (ja.getKaakao() < 20) {
			cocoaAmount.setForeground(Color.RED);
		} else {
			cocoaAmount.setForeground(Color.BLACK);
		}
	}
}
