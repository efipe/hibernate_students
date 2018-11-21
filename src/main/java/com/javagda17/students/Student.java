package com.javagda17.students;

import lombok.*;

import javax.persistence.*;
import java.util.List;

//@Setter
//@Getter
//@ToString
//@EqualsAndHashCode  - to wszystko mozna uniknac przez @Data
@Data// <---- hibernate
@AllArgsConstructor
@NoArgsConstructor
@Entity


public class Student extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //identity - pobiera ID a nastepnie przypisuje wartosc do obiektu i zapisuje obiekt
    // sequence - zapisuje obiekt, pobiera z powrotem i sprawdza ID
    private Long id;
    private String imie;
    private String nazwisko;
//    @Column(name = "indeks number", unique = true)
    private String indeks;

    // this class object to many class objects
    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    private List<Ocena> oceny;

}

