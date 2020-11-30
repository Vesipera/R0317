/*
 *  Luokka yksittäistä pelitietoa varten, ei sisällä mitäänä erikoista toiminnallisuutta
 *  
 *  Viimeksi muokattu 30.11.2020
 */
package tietokanta;

/**
 * @author Janne Lahdenperä <janne.lahdenpera@student.laurea.fi>
 * @version 1.0
 */
public class Pelitieto {
	
	// Luokan muuttujat
	private String nimi;
	private int julkaisuvuosi;
	private String kehittäjä;
	private String julkaisija;
	private String genre;
	
	// Konstruktori
	public Pelitieto(String nimi, int julkaisuvuosi, String kehittäjä, String julkaisija, String genre) {
		this.nimi = nimi;
		this.julkaisuvuosi = julkaisuvuosi;
		this.kehittäjä = kehittäjä;
		this.julkaisija = julkaisija;
		this.genre = genre;
	}
	
	// Getterit ja setterit muuttujien käsittelyyn
	
	public String getNimi() {
		return nimi;
	}
	
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}
	
	public int getJulkaisuvuosi() {
		return julkaisuvuosi;
	}
	
	public void setJulkaisuvuosi(int julkaisuvuosi) {
		this.julkaisuvuosi = julkaisuvuosi;
	}
	
	public String getKehittäjä() {
		return kehittäjä;
	}
	
	public void setKehittäjä(String kehittäjä) {
		this.kehittäjä = kehittäjä;
	}
	
	public String getJulkaisija() {
		return julkaisija;
	}
	
	public void setJulkaisija(String julkaisija) {
		this.julkaisija = julkaisija;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
}
