package com.javagda17.students;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDAO {
    public boolean saveStudentWithGradesIntoDb(Student student) {
        // pobieramy session factory (fabryka połączenia z bazą)
        SessionFactory sesssionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;

        try (Session session = sesssionFactory.openSession()) {
            // otwieram transakcję
            transaction = session.beginTransaction();

            for (Ocena oc : student.getOceny()) {
                session.save(oc);
            }

            session.save(student); // dokonujemy zapisu na bazie

            // zamykam transakcję i zatwierdzam zmiany
            transaction.commit();
        } catch (SessionException se) {
            // w razie błędu przywróć stan sprzed transakcji
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
        return true;
    }

    public boolean saveIntoDatabase(BaseEntity entity) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            // otwieramy
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();

        } catch (SessionException se) {
            // w razie bledu zrob rollback sprzed wykonania transakcji
            if (transaction != null) {
                transaction.rollback();
                return false;
            }
        }
        return true;
    }

    public List<Student> getAllStudentsFromDatabase() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try (Session session = sessionFactory.openSession()) {

            //stworz zapytanie

            Query<Student> query = session.createQuery("from Student ", Student.class);

            //wywolaj zapytanie

            List<Student> students = query.list();
            System.out.println(students);
            return students;
        } catch (SessionException se) {

            // jezeli cos pojdzie nie tak - wypiszmy komunikat z loggerem
            // todo: logger
            System.err.println();
        }
        // zwracam pusta liste jezeli niczego nie uda sie znalezc
        return new ArrayList<>();
    }

    public Optional<Student> getById(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query = session.createQuery("from Student where id =:id", Student.class);
            query.setParameter("id", id);
            System.out.println(Optional.ofNullable(query.getSingleResult()));
            return Optional.ofNullable(query.getSingleResult());
        } catch (PersistenceException pe) {
            System.out.println("Nie udalo sie pobrac z bazy");
        }
        return Optional.empty();
    }

    public List<Student> getById(List<Long> ids) {
        //1. Komenda znajdz
        //2. W metodzie znajdz odpytaj uzytkownika o ilosc szukanych studentow
        //3. Nastepnie pobierz n (gdzie n to liczba wpisana wyzej identyfikatorow )
        //4. wywolaj metode z studentDAO(wyszukaj wybranych id)
        //5. zwroc wynik i wypisz go na ekran

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query = session.createQuery("from Student where id in :ids", Student.class);
            query.setParameterList("ids", ids);
            System.out.println(query.list());
            return query.list();
        } catch (PersistenceException pe) {
            System.out.println("Nie udalo sie pobrac z bazy");
            return new ArrayList<>();
        }
    }

    public boolean removeByID(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        Optional<Student> studentOptional = getById(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                // za pomoca usuwania kaskadowego ( adnotacja przy studencie nie musimy wczesniej usuwać relacji student-> ocena
//                for (Ocena ocena: student.getOceny()){
//                    // czyscimy relacje zanim usuniemy studenta
//                    session.delete(ocena);
//                }
                // usuwanie studenta
                session.delete(student);

                transaction.commit();
                return true;
            } catch (SessionException se) {
                // w razie bledu zrob rollback sprzed wykonania transakcji
                if (transaction != null) {
                    transaction.rollback();
                    return false;
                }
            }
        }
        return false;
    }

    public boolean addTeacherToStudent(Long teacherID, Long studentID) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            //stworz zapytanie

            Query<Nauczyciel> queryT = session.createQuery("from Nauczyciel where id = :zmienna", Nauczyciel.class);
            queryT.setParameter("zmienna", teacherID);

            Query<Student> queryS = session.createQuery("from Student where id = :zmienna", Student.class);
            queryS.setParameter("zmienna", studentID);

            Student student = queryS.getSingleResult();
            Nauczyciel nauczyciel = queryT.getSingleResult();

            nauczyciel.getNauczaniStudenci().add(student);
            student.getNauczyciele().add(nauczyciel);

            session.save(nauczyciel);
            session.save(student);

            transaction.commit();

            return true;

        } catch (SessionException se) {

            if (transaction != null) {
                transaction.rollback();

            }
            return false;
        }
    }

}




