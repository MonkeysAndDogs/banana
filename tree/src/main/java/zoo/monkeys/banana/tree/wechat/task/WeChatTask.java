package zoo.monkeys.banana.tree.wechat.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zoo.monkeys.banana.tree.wechat.service.WeChatService;

import javax.annotation.Resource;

/**
 * @author Hofoo
 * @since 2017-10-19 09:54
 */
@Component
@Slf4j
public class WeChatTask {
    @Resource
    private WeChatService weChatService;

    @Scheduled(fixedRate = 7200 * 1000)
    public void refreshAccessToken() {
        weChatService.refreshAccessToken(false);
    }
}
