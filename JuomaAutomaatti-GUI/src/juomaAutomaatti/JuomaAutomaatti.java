/*
 *  Käyttöliittymän pohjalla oleva juoma-automaatti -luokka
 *  
 *  Valmiiksi annettua pohjaa muokattu sen verran, että se antaa virheilmoitukset
 *  konsolin sijaan suoraan ruudulle
 */
package juomaAutomaatti;

import javax.swing.JOptionPane;		// Virheilmoituksia varten

public class JuomaAutomaatti {

	private int tee;
	private int kahvi;
	private int kaakao;

	public JuomaAutomaatti() {
		this.tee = 50;
		this.kahvi = 50;
		this.kaakao = 50;
	}

	public int getTee() {
		return tee;
	}

	public void setTee(int tee) {
		this.tee = tee;
	}

	public int getKahvi() {
		return kahvi;
	}

	public void setKahvi(int kahvi) {
		this.kahvi = kahvi;
	}

	public int getKaakao() {
		return kaakao;
	}

	public void setKaakao(int kaakao) {
		this.kaakao = kaakao;
	}

	public void valmistaKahvi() {		// Tulostetaan ilmoitukset juomien loppumisesta ruudulle.
		if (this.kahvi - 10 < 0) {
			this.kahvi = 0;
			JOptionPane.showMessageDialog(null, "Kahvi loppu! Lisää saa yläpalkista.", "", JOptionPane.WARNING_MESSAGE);
		} else {
			this.kahvi -= 10;
		}
	}

	public void valmistaTee() {
		if (this.tee - 10 < 0) {
			this.tee = 0;
			JOptionPane.showMessageDialog(null, "Tee loppu! Lisää saa yläpalkista.", "", JOptionPane.WARNING_MESSAGE);
		} else {
			this.tee -= 10;
		}
	}

	public void valmistaKaakao() {
		if (this.kaakao - 10 < 0) {
			this.kaakao = 0;
			JOptionPane.showMessageDialog(null, "Kaakao loppu! Lisää saa yläpalkista.", "", JOptionPane.WARNING_MESSAGE);
		} else {
			this.kaakao -= 10;
		}
	}

	@Override
	public String toString() {
		return "JuomaAutomaatti [tee=" + tee + ", kahvi=" + kahvi + ", kaakao=" + kaakao + "]";
	}
}