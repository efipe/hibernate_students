package com.javagda17.students;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO();
//        Student st = new Student(null, "Marian", "Kowalski", "123");
        String pick;
        Scanner scanner = new Scanner(System.in);
        boolean condition = true;


        while (condition) {
            System.out.println("Podaj wybor \r\n 'save' aby utworzyc nowego studenta \r\n 'list' aby wypisac wszystkich uzytkownikow " +
                    "\r\n 'wyszukaj' aby znalezc konkretnego studenta \r\n 'znajdz' aby wyszukać kilku studentów po ich identyfikatorze \r\n " +
                    "'zatrudnij' aby dodac nowego nauczyciela do bazy \r\n" +
                    "'polacz' aby przypisac nauczyciela do grupy uczniow\r\n" +
                    "'usun' aby usunac studenta według identyfikatora " +
                    "'exit' aby zakonczyc dzialanie programu \r\n ");
            pick = scanner.next();
            switch (pick) {
                case "polacz":
                    System.out.println("Podaj ID studenta");
                    Long idS = scanner.nextLong();
                    System.out.println("Podaj ID Nauczyciela");
                    Long idT = scanner.nextLong();
                    studentDAO.addTeacherToStudent(idT, idS);
                    break;
                case "zatrudnij":
                    System.out.println("podaj Imie Nauczyciela");
                    String nameT = scanner.next();
                    System.out.println("Podaj nazwisko nauczyciela");
                    String surnameT = scanner.next();
                    Nauczyciel nauczyciel = new Nauczyciel(null, nameT, surnameT, 0.0, null);
                    studentDAO.saveIntoDatabase(nauczyciel);
                    break;
                case "usun":
                    System.out.println("podaj identyfikator studenta do usuniecia");
                    Long idStud = scanner.nextLong();
                    studentDAO.removeByID(idStud);
                    break;
                case "wyszukaj":
                    System.out.println("Podaj id studenta, ktorego chcesz wyszukac");
                    long id = scanner.nextLong();
                    studentDAO.getById(id);
                    break;
                case "znajdz":
                    System.out.println("Podaj ilosc studentów");
                    int qtyStuds = scanner.nextInt();
                    List<Long> ids = new ArrayList<>();
                    for (int i = 0; i < qtyStuds; i++) {
                        System.out.println("Podaj identyfikator studenta");
                        ids.add(scanner.nextLong());
                    }
                    studentDAO.getById(ids);
                    break;
                case "save":
                    System.out.println("Podaj imie");
                    String name = scanner.next();
                    System.out.println("Podaj nazwisko");
                    String surname = scanner.next();
                    System.out.println("Podaj nr indeksu");
                    String indeks = scanner.next();
                    // dodawanie ocen
                    System.out.println("Podaj ilosc ocen");
                    int iloscOcen = scanner.nextInt();
                    List<Ocena> ocenaList = new ArrayList<>();
                    Student student = new Student();
                    for (int i = 0; i < iloscOcen; i++) {
                        //tutaj dochodzi do parsowania String-> ENUM(ocena)
                        System.out.println("Podaj Nazwe przedmiotu");
                        Przedmiot przedmiot = Przedmiot.valueOf(scanner.next().toUpperCase());
                        System.out.println("Podaj Ocene");
                        int ocena = scanner.nextInt();
                        ocenaList.add(new Ocena(null, ocena, przedmiot, student));
                    }
//                Student student = new Student(null, name, surname, indeks, ocenaList);
                    student.setImie(name);
                    student.setNazwisko(surname);
                    student.setIndeks(indeks);
                    student.setOceny(ocenaList);
                    studentDAO.saveStudentWithGradesIntoDb(student);
                    break;
                case "list":
                    studentDAO.getAllStudentsFromDatabase();
                    break;
                case "exit":
                    condition = false;
                    HibernateUtil.getSessionFactory().close();
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
