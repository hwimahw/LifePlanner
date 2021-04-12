package ru.nsd.models.spiritModels;

import com.fasterxml.uuid.Generators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//@Entity
//@Table(name = "SPIRIT")
//@NoArgsConstructor
//@SecondaryTable(name = "SPIRIT",
//        pkJoinColumns = @PrimaryKeyJoinColumn(
//                name = "id",
//                referencedColumnName = "category_id"))
public class Spirit {

//    @Id
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID")
    private long id;

//    @Column(name = "CATEGORY_ID")
    private long categoryId;

    private String item;


    public Spirit(String item, long id){
        id = Generators.timeBasedGenerator().generate()
                .getMostSignificantBits();
        this.id = id;
        this.item = item;
    }

}
