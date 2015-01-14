package cat.udl.eps.softarch.hello.model;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Alert {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@NotBlank
	//@OneToOne
	//@PrimaryKeyJoinColumn
	private String title;
	
	@NotBlank
	private String email;
	
	public Alert() {}

    public Alert(String title, String email) {
        this.title = title;
        this.email = email;
    }

    public long getId() { return id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
	
}
