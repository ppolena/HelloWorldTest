package tutorial;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Table(name = "person")
public class Person {

    @Id
    @NotNull
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String personId;

    @NotNull
    private String name;

    @NotNull
    private String telnum;

    @OneToMany(mappedBy="owner")
    private List<Car> ownedCars;

    public Person(){}

    public Person(String name, String telnum){
        this.name = name;
        this.telnum = telnum;
        this.ownedCars = new LinkedList<>();
    }

    public String addCar(Car car){
        ownedCars.add(car);
        return "Done";
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{\"personId\":\"").append(personId).append("\", ");
        sb.append("\"name\":\"").append(name).append("\", ");
        sb.append("\"telnum\":\"").append(telnum).append("\", ");
        sb.append("\"ownedCars\":");
        List<String> resultList = new LinkedList<>();
        for(Car c : ownedCars){
            resultList.add("\"" + c.getCarId() + "\"");
        }
        sb.append(resultList.toString());
        sb.append("}");
        return sb.toString();
    }
}
