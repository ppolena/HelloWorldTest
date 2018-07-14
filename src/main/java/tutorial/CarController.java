package tutorial;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CarController {

    private final CarRepository carRepository;

    @GetMapping("/cars")
    public String listCars(){

        StringBuilder sb = new StringBuilder();
        sb.append("{\"cars\":");
        List<String> resultList = new LinkedList<>();
        for(Car c : carRepository.findAll()){
            resultList.add(c.toString());
        }
        sb.append(resultList.toString());
        sb.append("}");
        return sb.toString();
    }

    @GetMapping("cars/car")
    public String find(@RequestParam(value = "car_id", defaultValue="") String car_id,
                       @RequestParam(value = "name", defaultValue="") String name,
                       @RequestParam(value = "brand", defaultValue="") String brand,
                       @RequestParam(value = "year", defaultValue="") String year,
                       @RequestParam(value = "condition", defaultValue="") String condition){
        StringBuilder sb = new StringBuilder();
        sb.append("{\"result\":");
        List<String> resultList = new LinkedList<>();
        for(Car c : carRepository.findAll()){
            if((car_id.isEmpty()    || c.getCarId().equals(car_id)) &&
               (name.isEmpty()      || c.getName().equals(name)) &&
               (brand.isEmpty()     || c.getBrand().equals(brand)) &&
               (year.isEmpty()      || c.getYear() == (Integer.parseInt(year))) &&
               (condition.isEmpty() || c.getCondition().equals(Status.valueOf(condition))) &&
               (!(car_id+name+brand+year+condition).equals(""))){
                resultList.add(c.getCarId());
            }
        }
        sb.append(resultList.toString());
        sb.append("}");
        return sb.toString();
    }

    @DeleteMapping("cars/car")
    public String deleteId(@RequestParam(value = "car_id") String car_id){
        carRepository.deleteById(car_id);
        return "Done";
    }

    @PatchMapping("/cars/car/")
    public String update(String car_id,
                         @RequestParam(value = "name", defaultValue="") String name,
                         @RequestParam(value = "brand", defaultValue="") String brand,
                         @RequestParam(value = "year", defaultValue="") String year,
                         @RequestParam(value = "condition", defaultValue="") String condition) {
        Car car = carRepository.findById(car_id).get();
        if(!name.isEmpty()) car.setName(name);
        if(!brand.isEmpty()) car.setBrand(brand);
        if(!year.isEmpty()) car.setYear(Integer.parseInt(year));
        if(!condition.isEmpty()) car.setCondition(Status.valueOf(condition));
        carRepository.flush();
        return "Done";
    }

    @PutMapping("/cars/car/")
    public String updateAll(@RequestBody Car car) {
        Car oldCar = carRepository.findById(car.getCarId()).get();
        oldCar.setName(car.getName());
        oldCar.setBrand(car.getBrand());
        oldCar.setYear(car.getYear());
        oldCar.setCondition(car.getCondition());
        carRepository.flush();
        return "Done";
    }
}
