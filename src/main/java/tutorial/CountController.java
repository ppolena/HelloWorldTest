package tutorial;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountController {

    @RequestMapping("/count")
    public Count count(@RequestParam(value="num1", defaultValue="1") int num1,
                       @RequestParam(value="num2", defaultValue="1") int num2) {
        return new Count(num1, num2);
    }
}
