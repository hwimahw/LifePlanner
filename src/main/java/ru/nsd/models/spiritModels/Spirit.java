package ru.nsd.models.spiritModels;

import com.fasterxml.uuid.Generators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "SPIRIT")
public class Spirit {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Column(name = "ITEM")
    private String item;


    public Spirit(String item, Category category){
//        id = Generators.timeBasedGenerator().generate()
//                .getMostSignificantBits();
//        this.id = id;
        this.item = item;
        this.category = category;
    }

}
