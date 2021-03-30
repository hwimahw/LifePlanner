package ru.nsd.models;

import com.fasterxml.uuid.Generators;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SPIRIT")
public class Spirit {

    @Id
    private long id;

    private String item;

    public Spirit(String item){
        id = Generators.timeBasedGenerator().generate()
                .getMostSignificantBits();
        this.item = item;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public long getId() {
        return id;
    }
}
