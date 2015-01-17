package cat.udl.eps.softarch.hello.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Serie {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	//@JoinColumn
	@NotBlank
	private String title;
	
    private int seasons;
    private String country;
    private String status;
    private String airday;
    private String genre;
    private String link;
	
	public Serie() {}

    public Serie(String title) {
        this.title = title;
    }

    public long getId() { return id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

	public int getSeasons() {
		return seasons;
	}

	public void setSeasons(int seasons) {
		this.seasons = seasons;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAirday() {
		return airday;
	}

	public void setAirday(String airday) {
		this.airday = airday;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}   
	
}
