package com.javagda17.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Ocena extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer ocena;
    private Przedmiot przedmiot;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;
}
