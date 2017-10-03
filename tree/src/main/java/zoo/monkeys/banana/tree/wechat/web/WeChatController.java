package zoo.monkeys.banana.tree.wechat.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zoo.monkeys.banana.tree.config.WeChatConfig;
import zoo.monkeys.banana.tree.utils.WeChatUtil;

import javax.annotation.Resource;

import static zoo.monkeys.banana.tree.utils.WeChatUtil.checkSignature;

/**
 * @author Hofoo
 * @since 2017-10-03 14:48
 */
@RestController
@RequestMapping("wx")
@Slf4j
public class WeChatController {

    @Resource
    private WeChatConfig weChatConfig;

    @GetMapping("dock")
    public String dock(String signature, String timestamp, String nonce, String echostr) {
        log.debug("signature:{}, timestamp:{}, nonce:{}, echostr:{}", signature, timestamp, nonce, echostr);
        boolean isOk = checkSignature(weChatConfig.getToken(), signature, timestamp, nonce);
        log.info("isOk:{}", isOk);
        if (!isOk) {
            log.error("dock wechat error: signature:{}, timestamp:{}, nonce:{}, echostr:{}", signature, timestamp, nonce, echostr);
            return "dock wechat error";
        }
        return echostr;
    }
}
