package ru.nsd.models.spiritModels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CATEGORY",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"id", "name"})})
public class Category {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

}
