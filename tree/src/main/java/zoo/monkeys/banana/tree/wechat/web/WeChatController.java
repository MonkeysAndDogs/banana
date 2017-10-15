package zoo.monkeys.banana.tree.wechat.web;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zoo.monkeys.banana.tree.config.WeChatConfig;
import zoo.monkeys.banana.tree.utils.MessageUtil;
import zoo.monkeys.banana.tree.wechat.model.TextMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

import static zoo.monkeys.banana.tree.utils.WeChatUtil.checkSignature;

/**
 * @author Hofoo
 * @since 2017-10-03 14:48
 */
@RestController
@RequestMapping("wx/dock")
@Slf4j
public class WeChatController {

    @Resource
    private WeChatConfig weChatConfig;

    @GetMapping
    public String dock(String signature, String timestamp, String nonce, String echostr) {
        log.debug("signature:{}, timestamp:{}, nonce:{}, echostr:{}", signature, timestamp, nonce, echostr);
        boolean isOk = checkSignature(weChatConfig.getToken(), signature, timestamp, nonce);
        log.info("dock wechat is {}", isOk);
        if (!isOk) {
            log.error("dock wechat error: signature:{}, timestamp:{}, nonce:{}, echostr:{}", signature, timestamp, nonce, echostr);
            return "dock wechat error";
        }
        return echostr;
    }

    @PostMapping
    public String message(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, String> mapRequest = MessageUtil.xmlToMap(request);
        String strToUserName = mapRequest.get("ToUserName");
        String strFromUserName = mapRequest.get("FromUserName");
        String strCreateTime = mapRequest.get("CreateTime");
        String strMsgType = mapRequest.get("MsgType");
        String strContent = mapRequest.get("Content");
        String strMsgId = mapRequest.get("MsgId");

        TextMessage rtnMsg = new TextMessage();
        if ("text".equals(strMsgType)) {
            rtnMsg.setToUserName(strFromUserName);
            rtnMsg.setFromUserName(strToUserName);
            rtnMsg.setMsgType("text");
            rtnMsg.setContent("echo:" + strContent);
        }
        String xml = MessageUtil.textMessageToXml(rtnMsg);
        log.info("msg to xml:{}", xml);
        return xml;
    }
}
