package gr.aueb.cf.model;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    private Long id;  // Long can be null

    @Column(name= "firstname", length = 255, nullable = false, unique = false)  // the default values aren't necessary
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    public Teacher(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }


    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private Set<Course> courses = new HashSet<>();

    public Set<Course> getAllCourses() {
        return Collections.unmodifiableSet(courses);
    }

    // Helper Methods
    public void addCourse(Course course) {
        courses.add(course);
        course.setTeacher(this);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
        course.setTeacher(null);  // because the Course object still exists
    }


    @Override
    public String toString() {
        return String.format("%d %s %s", id, firstname, lastname);
    }
}
