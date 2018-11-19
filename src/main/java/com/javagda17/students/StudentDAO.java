package com.javagda17.students;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public boolean saveStudentsIntoDatabase(Student student) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            // otwieramy
            transaction = session.beginTransaction();
            session.save(student);
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

}


