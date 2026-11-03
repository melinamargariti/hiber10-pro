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
            String sql = "SELECT t FROM Teacher t"; // select all teachers
            String sql2 = "SELECT t.lastname FROM Teacher t"; // select all teachers' lastnames
            String sql3 = "SELECT t FROM Teacher t WHERE t.lastname = 'Alistair'"; // select teachers with lastname = 'AListair'- or :lastname with setParameter("lastname", "Alistair")
            String sql4 = "SELECT c FROM Course c WHERE c.title LIKE 'Java%' AND c.teacher IS NOT NULL"; // select courses with title starting with 'Java' and have a teacher assigned
            String sql5 = "SELECT t FROM Teacher t WHERE t.lastname IN :lastnames";
            String sql6 = "SELECT t FROM Teacher t ORDER BY t.lastname ASC, t.firstname ASC";



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
            String query3 = "SELECT c FROM Course c WHERE c.teacher.lastname = 'Καπέτης'";  // implicit join
            List<Course> kapetiCourses = em.createQuery(query3, Course.class)
                    .getResultList();
            kapetiCourses.forEach(System.out::println);

            // Select courses of Kapetis SQL Injection free
            String query4 = "SELECT c FROM Course c WHERE c.teacher.lastname = ?1"; // or .lastname = :lastname with setParameter("lastname", "Καπέτης")
            List<Course> kapetiCourses2 = em.createQuery(query4, Course.class)
                    .setParameter(1, "Καπέτης")
                    .getResultList();
            kapetiCourses2.forEach(System.out::println);

            //Select teacher that teaches java
            String query5 = "SELECT t FROM Teacher t JOIN t.courses c WHERE c.title = :title"; // explicit join
            List<Teacher> javaTeachers = em.createQuery(query5, Teacher.class)
                    .setParameter("title", "java")
                    .getResultList();
            javaTeachers.forEach(System.out::println);

            // Select teacher lastname and the count of his courses
            String query6 = "SELECT t.id, t.lastname, COUNT(c) FROM Teacher t JOIN t.courses c GROUP BY t.id";
            List<Object[]> teachers6 = em.createQuery(query6, Object[].class).getResultList();

            for (Object[] teacher : teachers6) {
                Long id = (Long) teacher[0];
                String lastname = (String) teacher[1];
                Long count = (Long) teacher[2];
                System.out.println(teacher[0] + " " + teacher[1] + " " + teacher[2]);
            }

            // Select teachers that do not teach any course
            String query7 = "SELECT t FROM Teacher t LEFT JOIN t.courses c WHERE c.id IS NULL";
            List<Teacher> teachers7 = em.createQuery(query7, Teacher.class).getResultList();
            teachers7.forEach(System.out::println);

            // Select teachers and their courses
            // EAGER FETCH
            String query8 = "SELECT t, c FROM Teacher t LEFT JOIN FETCH t.courses c";

            // Native Query
            String query9 = "SELECT * FROM teachers";
            var teachers9 = em.createNativeQuery(query9, Teacher.class).getResultList();
            teachers9.forEach(System.out::println);

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
