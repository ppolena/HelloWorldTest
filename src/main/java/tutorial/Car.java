package tutorial;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "car")
public class Car implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String carId;

    @NotNull
    private String name;

    @NotNull
    private String brand;

    @NotNull
    private int year;

    @NotNull
    private Status condition;

    @JsonIgnore
    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Person owner;

    public Car(){}

    public Car(String name,
        String brand,
        int year,
        Status condition,
        Person owner){
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.condition = condition;
        this.owner = owner;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{\"carId\":\"").append(carId).append("\", ");
        sb.append("\"name\":\"").append(name).append("\", ");
        sb.append("\"brand\":\"").append(brand).append("\", ");
        sb.append("\"year\":").append(year).append(", ");
        sb.append("\"condition\":\"").append(condition).append("\", ");
        sb.append("\"ownerId\":\"").append(owner.getPersonId()).append("\"}");
        return sb.toString();
    }
}
