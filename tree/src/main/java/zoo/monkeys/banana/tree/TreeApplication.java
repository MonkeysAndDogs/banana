package zoo.monkeys.banana.tree;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zoo.monkeys.banana.tree.wechat.service.WeChatService;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@RestController
@EnableScheduling
public class TreeApplication implements CommandLineRunner {

    private AtomicInteger count = new AtomicInteger();

    @Resource
    private WeChatService weChatService;

    @RequestMapping("/")
    public String home() {
        return "Hello Docker World, access times:" + count.incrementAndGet();
    }

    public static void main(String[] args) {
        SpringApplication.run(TreeApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        weChatService.refreshAccessToken(false);
    }
}
