package gr.aueb.cf;

import gr.aueb.cf.model.Course;
import gr.aueb.cf.model.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("SchoolPU");

    public static void main(String[] args) {
        EntityManager em =  emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            tx.commit();


//            Course java = em.find(Course.class, 1L);
//            Teacher alice = em.find(Teacher.class, 1L);
//
//            alice.removeCourse(java);
//            em.remove(java);  // remove the course from the database

//            Teacher alice = em.find(Teacher.class, 1L);
//            Course java = new Course("java");
//            alice.addCourse(java);
//            alice.setLastname("Wonderland");
//
//            em.persist(java);  // persist the new course

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
