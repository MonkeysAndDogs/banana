package zoo.monkeys.banana.tree.wechat.web;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zoo.monkeys.banana.tree.config.WeChatConfig;
import zoo.monkeys.banana.tree.utils.MessageUtil;
import zoo.monkeys.banana.tree.wechat.message.Message;
import zoo.monkeys.banana.tree.wechat.message.MessageFactory;
import zoo.monkeys.banana.tree.wechat.message.cmm.NewsItem;
import zoo.monkeys.banana.tree.wechat.message.cmm.NewsMessage;
import zoo.monkeys.banana.tree.wechat.message.constants.MsgType;
import zoo.monkeys.banana.tree.wechat.model.MenuResponse;
import zoo.monkeys.banana.tree.wechat.service.WeChatService;

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
@RequestMapping("/wechat")
@Slf4j
public class WeChatController {

    @Resource
    private WeChatConfig weChatConfig;

    @Resource
    private WeChatService weChatService;

    @GetMapping("/dock")
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

    @PostMapping("/dock")
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
            } else if ("2".equals(strContent)) {
                NewsMessage news = MessageFactory.createNewsMessage(strFromUserName, strToUserName);

                NewsItem item = new NewsItem();
                item.setTitle("世界最帅");
                item.setDescription("世界，亦稱天地、天下、人间、世间、万物、世上等，是對所有事物的代稱。可以有下列意思：. 人類文明所有一切的代稱。在許多場合下，也以'全球、環球（寰球）、寰宇'");
                item.setPicUrl("http://" + weChatConfig.getDomain() + "/image/world.jpeg");
                item.setUrl("http://" + weChatConfig.getDomain());
                news.getArticles().add(item);

                NewsItem item2 = new NewsItem();
                item2.setTitle("iphonex");
                item2.setDescription("世界，亦稱天地、天下、人间、世间、万物、世上等，是對所有事物的代稱。可以有下列意思：. 人類文明所有一切的代稱。在許多場合下，也以'全球、環球（寰球）、寰宇'");
                item2.setPicUrl("http://" + weChatConfig.getDomain() + "/image/iphonex.png");
                item2.setUrl("http://" + weChatConfig.getDomain());

                news.getArticles().add(item2);
                news.setArticleCount(news.getArticles().size());
                msg = news;
            } else {
                msg = MessageFactory.createTextMessage(strFromUserName, strToUserName, "您发的消息是:" + strContent);
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

    @GetMapping("/accesstoken/refresh")
    public void refreshAccessToken() {
        weChatService.refreshAccessToken(true);
    }

    @GetMapping("/menu")
    public MenuResponse createMenu() throws Exception {
        return weChatService.createMenu();
    }
}
