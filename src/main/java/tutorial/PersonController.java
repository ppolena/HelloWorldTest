package tutorial;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PersonController {

    private final CarRepository carRepository;

    private final PersonRepository personRepository;

    @PostMapping("/people")
    public String addPerson(@RequestParam(value="name") String name,
                            @RequestParam(value="telnum") String telnum){
        personRepository.save(new Person(name, telnum));
        return "Done";
    }

    @GetMapping("/people")
    public String listPeople(){

        StringBuilder sb = new StringBuilder();
        sb.append("{\"people\":");
        List<String> resultList = new LinkedList<>();
        for(Person p : personRepository.findAll()){
            resultList.add(p.toString());
        }
        sb.append(resultList.toString());
        sb.append("}");
        return sb.toString();
    }

    @PostMapping("/people/person")
    public String addCar(@RequestParam(value = "id", defaultValue="") String person_id,
                         @RequestParam(value = "name") String name,
                         @RequestParam(value = "brand") String brand,
                         @RequestParam(value = "year") int year,
                         @RequestParam(value = "condition") Status condition){
        Car car = new Car(name, brand, year, condition, personRepository.findById(person_id).get());
        personRepository.findById(person_id).get().addCar(car);
        carRepository.save(car);
        return "Done";
    }

    @GetMapping("/people/person")
    public String find(@RequestParam(value = "id", defaultValue="") String person_id,
                       @RequestParam(value = "name", defaultValue="") String name,
                       @RequestParam(value = "telnum", defaultValue="") String telnum) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"result\":");
        List<String> resultList = new LinkedList<>();
        for (Person p : personRepository.findAll()) {
            if ((person_id.isEmpty() || p.getPersonId().equals(person_id) &&
                (name.isEmpty() || p.getName().equals(name)) &&
                (telnum.isEmpty() || p.getTelnum().equals(telnum)) &&
                (!(person_id + name + telnum).equals("")))) {
                resultList.add(p.toString());
            }
        }
        sb.append(resultList.toString());
        sb.append("}");
        return sb.toString();
    }

    @DeleteMapping("/people/person")
    public String deleteById(@RequestParam(value = "id") String person_id){
        personRepository.deleteById(person_id);
        return "Done";
    }

    @PatchMapping("/people/person/")
    public String updateById(String person_id,
                            @RequestParam(value = "name", defaultValue="") String name,
                            @RequestParam(value = "telnum", defaultValue="") String telnum) {
        Person person = personRepository.findById(person_id).get();
        if(!name.isEmpty()) person.setName(name);
        if(!telnum.isEmpty()) person.setTelnum(telnum);
        personRepository.flush();
        return "Done";
    }

    @PutMapping("/people/person/")
    public String updateAll(@RequestBody Person person) {
        Person oldPerson = personRepository.findById(person.getPersonId()).get();
        oldPerson.setName(person.getName());
        oldPerson.setTelnum(person.getTelnum());
        personRepository.flush();
        return "Done";
    }
}
