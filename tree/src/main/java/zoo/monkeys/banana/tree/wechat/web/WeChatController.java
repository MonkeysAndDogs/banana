package zoo.monkeys.banana.tree.wechat.web;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zoo.monkeys.banana.tree.config.WeChatConfig;
import zoo.monkeys.banana.tree.utils.MessageUtil;
import zoo.monkeys.banana.tree.wechat.message.MessageFactory;
import zoo.monkeys.banana.tree.wechat.message.constants.MsgType;
import zoo.monkeys.banana.tree.wechat.message.Message;

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

        Message msg = null;
        if (MsgType.TEXT.equals(strMsgType)) {
            if ("1".equals(strContent)) {
                msg = MessageFactory.createTextMessage(strFromUserName, strToUserName, "世界最帅");
            }else{
                msg = MessageFactory.createTextMessage(strFromUserName, strToUserName, "我不是siri，无法识您发的消息:" + strContent);
            }
        } else if (MsgType.EVENT.equals(strMsgType)) {
            String strEvent = mapRequest.get("Event");
            if (MsgType.EventType.SUBSCRIBE.equals(strEvent)) {
                msg = MessageFactory.createTextMessage(strFromUserName, strToUserName, MessageFactory.getMenuText());
            } else if (MsgType.EventType.UNSUBSCRIBE.equals(strEvent)) {
                log.info("{} unsubscribe wechat account", strFromUserName);
            }
        } else {
            throw new RuntimeException("Unrecognized message type: " + strMsgType);
        }
        String xml = MessageUtil.textMessageToXml(msg);
        log.info("msg to xml:{}", xml);
        return xml;
    }
}
