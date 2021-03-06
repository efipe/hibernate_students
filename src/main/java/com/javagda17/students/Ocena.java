package com.javagda17.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "student")


public class Ocena extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private Integer ocena;
    @Enumerated(value = EnumType.STRING)
    private Przedmiot przedmiot;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

}
