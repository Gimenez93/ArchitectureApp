package cat.udl.eps.softarch.hello.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Serie {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	//@JoinColumn
	@NotBlank
	private String title;
	
	public Serie() {}

    public Serie(String title) {
        this.title = title;
    }

    public long getId() { return id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
	
}
