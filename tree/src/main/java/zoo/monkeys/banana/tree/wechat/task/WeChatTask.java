package zoo.monkeys.banana.tree.wechat.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import zoo.monkeys.banana.tree.config.WeChatConfig;
import zoo.monkeys.banana.tree.wechat.dao.AccessTokenRepository;
import zoo.monkeys.banana.tree.wechat.model.AccessToken;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * @author Hofoo
 * @since 2017-10-19 09:54
 */
@Component
@Slf4j
public class WeChatTask {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private WeChatConfig config;

    @Resource
    private AccessTokenRepository accessTokenRepository;

    @Scheduled(fixedRate = 7200 * 1000)
    public void refreshAccessToken() {
        AccessToken accessToken = accessTokenRepository.findOne(1L);
        if (null == accessToken) {
            accessToken = getAccessTokenFromWeChat();
            accessTokenRepository.save(accessToken);
        } else {
            Instant updateTime = accessToken.getUpdateTime().toInstant();
            if (Duration.between(updateTime, Instant.now()).toMillis() > 7200 * 1000) {
                accessToken = getAccessTokenFromWeChat();
                accessTokenRepository.save(accessToken);
            }
        }
        config.setAccessToken(accessToken.getAccessToken());
        log.info("current access token is {}", accessToken.getAccessToken());
    }

    private AccessToken getAccessTokenFromWeChat() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + config.getAppID() + "&secret=" + config.getAppSecret();
        AccessToken accessToken = restTemplate.getForObject(url, AccessToken.class);
        accessToken.setId(1L);
        accessToken.setUpdateTime(new Date());
        return accessToken;
    }

}
