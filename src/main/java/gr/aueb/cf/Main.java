package gr.aueb.cf;

import gr.aueb.cf.model.Course;
import gr.aueb.cf.model.Teacher;
import jakarta.persistence.*;

import java.util.List;

public class Main {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("SchoolPU");

    public static void main(String[] args) {
        EntityManager em =  emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();


            // JPQL

            // Select all teachers
            String query = "SELECT t FROM Teacher t";
            TypedQuery<Teacher> typedQuery = em.createQuery(query, Teacher.class);
            List<Teacher> teachers = typedQuery.getResultList();
            teachers.forEach(System.out::println);

            // Select all courses
            String query2 = "SELECT c FROM Course c";
            List<Course> courses = em.createQuery(query2, Course.class).getResultList();
            courses.forEach(System.out::println);

            // Select courses of Kapeti
            String query3 = "SELECT c FROM Course c WHERE c.teacher.lastname = 'Καπέτης'";
            List<Course> kapetiCourses = em.createQuery(query3, Course.class)
                    .getResultList();
            kapetiCourses.forEach(System.out::println);

            // Select courses of Kapetis SQL Injection free
            String query4 = "SELECT c FROM Course c WHERE c.teacher.lastname = ?1"; // or .lastname = :lastname with setParameter("lastname", "Καπέτης")
            List<Course> kapetiCourses2 = em.createQuery(query4, Course.class)
                    .setParameter(1, "Καπέτης")
                    .getResultList();
            kapetiCourses2.forEach(System.out::println);

            tx.commit();

//            REMOVE =========================
//            Course java = em.find(Course.class, 1L);
//            Teacher alice = em.find(Teacher.class, 1L);
//
//            alice.removeCourse(java);
//            em.remove(java);  // remove the course from the database

//            MERGE =========================
//            Teacher alice = em.find(Teacher.class, 1L);
//            Course java = new Course("java");
//            alice.addCourse(java);
//            alice.setLastname("Wonderland");
//
//            em.persist(java);  // persist the new course
//            // em.merge(alice);  dirty checking - checks for updates

//            PERSIST =========================
//            Teacher alice = new Teacher("Alice", "Johnson");
//            em.persist(alice);

        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }
}
