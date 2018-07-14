package tutorial;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, String>{
    List<Car> findByName(String name);
    List<Car> findByBrand(String brand);
    List<Car> findByYear(int year);
    List<Car> findByCondition(Status condition);

}

