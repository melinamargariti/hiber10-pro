package gr.aueb.cf;

import gr.aueb.cf.model.Course;
import gr.aueb.cf.model.Teacher;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;

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
            String sql4 = "SELECT c FROM Course c WHERE c.title LIKE :title AND c.teacher IS NOT NULL"; // setParameter("title", "java%") - select courses that start with java and have a teacher assigned
            String sql5 = "SELECT t FROM Teacher t WHERE t.lastname IN :lastnames";
            String sql6 = "SELECT t FROM Teacher t ORDER BY t.lastname ASC, t.firstname ASC";

            String sql7 = "SELECT c FROM Course c WHERE c.title LIKE 'Java%' AND c.teacher.lastname = :lastname";
            String sql8 = "SELECT t FROM Teacher t JOIN FETCH t.courses c WHERE c.title LIKE 'Java%'";
            String sql9 = "SELECT t FROM Teacher t LEFT JOIN t.courses c WHERE c IS NULL";
            String sql10 = "SELECT t FROM Teacher t WHERE t.courses IS EMPTY";
//            String query11 = "SELECT t FROM Teacher t LEFT JOIN t.courses";   // LAZY FETCH
//            List<Teacher> teachers11 = em.createQuery(query11, Teacher.class).getResultList();
//            teachers11.forEach(t -> System.out.println(t + " " + t.getAllCourses()));  // N queries

            String query11 = "SELECT t FROM Teacher t LEFT JOIN FETCH t.courses";   // EAGER FETCH
            List<Teacher> teachers11 = em.createQuery(query11, Teacher.class).getResultList();
            teachers11.forEach(t -> System.out.println(t + " " + t.getAllCourses()));

//            // Criteria API
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Teacher> cq = cb.createQuery(Teacher.class);
//            Root<Teacher> root = cq.from(Teacher.class);
//            // query build
//            List<Teacher> teachers = em.createQuery(cq).getResultList();

//            // Select all teachers
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Teacher> cq = cb.createQuery(Teacher.class);
//            Root<Teacher> root = cq.from(Teacher.class);
//            cq.select(root);

//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Teacher> cq = cb.createQuery(Teacher.class);
//            Root<Teacher> root = cq.from(Teacher.class);
//            cq.select(root.get("lastname"));

//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Teacher> cq = cb.createQuery(Teacher.class);
//            Root<Teacher> t = cq.from(Teacher.class);
//            cq.select(t).where(cb.equal(t.get("lastname"), "Alistair"));

//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Teacher> cq = cb.createQuery(Teacher.class);
//            Root<Teacher> t = cq.from(Teacher.class);
//            ParameterExpression<String> lastname = cb.parameter(String.class, "lastname");
//            cq.select(t).where(cb.equal(t.get("lastname"),lastname));
//            List<Teacher> teachers = em.createQuery(cq).setParameter("lastname", "Alistair").getResultList();

//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Teacher> cq = cb.createQuery(Teacher.class);
//            Root<Teacher> t = cq.from(Teacher.class);
//            ParameterExpression<String> lastname = cb.parameter(String.class, "lastname");
//            cq.select(t).where(cb.like(t.get("lastname"),"Ανδρού%"));
//            List<Teacher> teachers = em.createQuery(cq).setParameter("lastname", "Ανδρούτσος").getResultList();





//
//            // Select all teachers
//            String query = "SELECT t FROM Teacher t";
//            TypedQuery<Teacher> typedQuery = em.createQuery(query, Teacher.class);
//            List<Teacher> teachers = typedQuery.getResultList();
//            teachers.forEach(System.out::println);

//            // Select all courses
//            String query2 = "SELECT c FROM Course c";
//            List<Course> courses = em.createQuery(query2, Course.class).getResultList();
//            courses.forEach(System.out::println);
//
//            // Select courses of Kapeti
//            String query3 = "SELECT c FROM Course c WHERE c.teacher.lastname = 'Καπέτης'";  // implicit join
//            List<Course> kapetiCourses = em.createQuery(query3, Course.class)
//                    .getResultList();
//            kapetiCourses.forEach(System.out::println);
//
//            // Select courses of Kapetis SQL Injection free
//            String query4 = "SELECT c FROM Course c WHERE c.teacher.lastname = ?1"; // or .lastname = :lastname with setParameter("lastname", "Καπέτης")
//            List<Course> kapetiCourses2 = em.createQuery(query4, Course.class)
//                    .setParameter(1, "Καπέτης")
//                    .getResultList();
//            kapetiCourses2.forEach(System.out::println);
//
//            //Select teacher that teaches java
//            String query5 = "SELECT t FROM Teacher t JOIN t.courses c WHERE c.title = :title"; // explicit join
//            List<Teacher> javaTeachers = em.createQuery(query5, Teacher.class)
//                    .setParameter("title", "java")
//                    .getResultList();
//            javaTeachers.forEach(System.out::println);
//
//            // Select teacher lastname and the count of his courses
//            String query6 = "SELECT t.id, t.lastname, COUNT(c) FROM Teacher t JOIN t.courses c GROUP BY t.id";
//            List<Object[]> teachers6 = em.createQuery(query6, Object[].class).getResultList();
//
//            for (Object[] teacher : teachers6) {
//                Long id = (Long) teacher[0];
//                String lastname = (String) teacher[1];
//                Long count = (Long) teacher[2];
//                System.out.println(teacher[0] + " " + teacher[1] + " " + teacher[2]);
//            }
//
//            // Select teachers that do not teach any course
//            String query7 = "SELECT t FROM Teacher t LEFT JOIN t.courses c WHERE c.id IS NULL";
//            List<Teacher> teachers7 = em.createQuery(query7, Teacher.class).getResultList();
//            teachers7.forEach(System.out::println);
//
//            // Select teachers and their courses
//            // EAGER FETCH
//            String query8 = "SELECT t, c FROM Teacher t LEFT JOIN FETCH t.courses c";
//
//            // Native Query
//            String query9 = "SELECT * FROM teachers";
//            var teachers9 = em.createNativeQuery(query9, Teacher.class).getResultList();
//            teachers9.forEach(System.out::println);

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
