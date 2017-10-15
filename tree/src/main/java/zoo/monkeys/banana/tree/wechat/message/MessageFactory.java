package zoo.monkeys.banana.tree.wechat.message;

import zoo.monkeys.banana.tree.wechat.message.cmm.TextMessage;

/**
 * @author Hofoo
 * @since 2017-10-15 17:29
 */
public class MessageFactory {

    public static WeChatMessage createTextMessage(String toUserName, String fromUserName, String content) {
        TextMessage textMessage = new TextMessage(MsgType.TEXT);
        textMessage.setToUserName(toUserName);
        textMessage.setFromUserName(fromUserName);
        textMessage.setContent(content);
        return textMessage;
    }
}
