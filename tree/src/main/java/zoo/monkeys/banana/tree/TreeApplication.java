package zoo.monkeys.banana.tree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@RestController
@EnableScheduling
public class TreeApplication {

    private AtomicInteger count;

    @RequestMapping("/")
    public String home() {
        return "Hello Docker World, access times:" + count.incrementAndGet();
    }

    public static void main(String[] args) {
        SpringApplication.run(TreeApplication.class, args);
    }
}
