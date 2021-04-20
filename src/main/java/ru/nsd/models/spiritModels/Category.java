package ru.nsd.models.spiritModels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CATEGORY")
public class Category {

    public Category(String name){
        this.name = name;
    }

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(name = "name", unique = true)
    private String name;

}
