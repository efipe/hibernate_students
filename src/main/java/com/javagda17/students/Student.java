package com.javagda17.students;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Setter
//@Getter
//@ToString
//@EqualsAndHashCode  - to wszystko mozna uniknac przez @Data
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity


public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //identity - pobiera ID a nastepnie przypisuje wartosc do obiektu i zapisuje obiekt
    // sequence - zapisuje obiekt, pobiera z powrotem i sprawdza ID
    private Long id;

    private String imie;
    private String nazwisko;
    private String indeks;

}
