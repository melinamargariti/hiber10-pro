package gr.aueb.cf.model;
import jakarta.persistence.*;

/**
 * A java class mapped to a database using JPA.
 */
@Entity
@Table(name = "teachers")
public class Teacher {

    @Id  // id = the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // lets the database generate the ID using auto-increment.
    private Long id;

    @Column(name= "firstname", length = 255, nullable = false, unique = false)  // the default values aren't necessary
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    public Teacher(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return String.format("%d %s %s", id, firstname, lastname);
    }
}
