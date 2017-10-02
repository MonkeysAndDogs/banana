package zoo.monkeys.banana.tree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TreeApplication {

    private int count;

    @RequestMapping("/")
    public String home() {
        System.out.println("count:" + ++count);
        return "Hello Docker World";
    }

    public static void main(String[] args) {
        SpringApplication.run(TreeApplication.class, args);
    }
}
