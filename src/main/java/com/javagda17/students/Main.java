package com.javagda17.students;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO();
//        Student st = new Student(null, "Marian", "Kowalski", "123");
        String pick;
        Scanner scanner = new Scanner(System.in);
        boolean condition = true;


        while (condition) {
            System.out.println("Podaj wybor \r\n 'save' aby utworzyc nowego studenta \r\n 'list' aby wypisac wszystkich uzytkownikow \r\n 'exit' aby zakonczyc");
            pick = scanner.nextLine();
            switch (pick) {
                case "save":
                    System.out.println("Podaj imie");
                    String name = scanner.nextLine();
                    System.out.println("Podaj nazwisko");
                    String surname = scanner.nextLine();
                    System.out.println("Podaj nr indeksu");
                    String indeks = scanner.nextLine();
                    Student student = new Student(null, name, surname, indeks);
                    studentDAO.saveStudentsIntoDatabase(student);
                    break;
                case "list":
                    studentDAO.getAllStudentsFromDatabase();
                    break;
                case "exit":
                    condition = false;
                    break;
                default:
                    System.out.println("wybrales niedozwolona opcje, zamykanie");
                    condition = false;
                    break;
            }
        }

//        studentDAO.getAllStudentsFromDatabase();


    }


}
