package com.javagda17.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor


public class Nauczyciel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String imie;
    private String nazwisko;
    private double sredniaOcenaNauczyciela;
    @ManyToMany
    private List<Student> nauczaniStudenci;

}
