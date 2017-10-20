package zoo.monkeys.banana.tree.wechat.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import zoo.monkeys.banana.tree.config.WeChatConfig;
import zoo.monkeys.banana.tree.wechat.dao.AccessTokenRepository;
import zoo.monkeys.banana.tree.wechat.model.AccessToken;
import zoo.monkeys.banana.tree.wechat.model.MenuResponse;
import zoo.monkeys.banana.tree.wechat.service.WeChatService;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * @author Hofoo
 * @since 2017-10-19 15:11
 */
@Slf4j
@Service
public class WeChatServiceImpl implements WeChatService {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private WeChatConfig config;

    @Resource
    private AccessTokenRepository accessTokenRepository;

    @Override
    public MenuResponse createMenu() throws JSONException, IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={token}";
        String accessToken = config.getAccessToken();
        String menu = IOUtils.toString(FileUtils.openInputStream(ResourceUtils.getFile("classpath:menu.json")), StandardCharsets.UTF_8);
        JSONObject root = new JSONObject(menu);
        return restTemplate.postForObject(url, root.toString(), MenuResponse.class, accessToken);
    }

    @Override
    public void refreshAccessToken(boolean isForce) {
        AccessToken accessToken;
        if (isForce) {
            accessToken = getAccessTokenFromWeChat();
            accessTokenRepository.save(accessToken);
        } else {
            accessToken = accessTokenRepository.findOne(1L);
            if (null == accessToken) {
                accessToken = getAccessTokenFromWeChat();
                accessTokenRepository.save(accessToken);
            } else {
                Instant updateTime = accessToken.getUpdateTime().toInstant();
                if (Duration.between(updateTime, Instant.now()).toMillis() > accessToken.getExpiresIn() * 1000) {
                    accessToken = getAccessTokenFromWeChat();
                    accessTokenRepository.save(accessToken);
                }
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
