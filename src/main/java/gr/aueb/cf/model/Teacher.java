package gr.aueb.cf.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A java class mapped to a database using JPA.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
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

    @Override
    public String toString() {
        return String.format("%d %s %s", id, firstname, lastname);
    }
}
