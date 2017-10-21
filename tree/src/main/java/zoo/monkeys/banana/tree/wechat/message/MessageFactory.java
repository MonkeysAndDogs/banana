package zoo.monkeys.banana.tree.wechat.message;

import zoo.monkeys.banana.tree.wechat.message.cmm.NewsMessage;
import zoo.monkeys.banana.tree.wechat.message.cmm.TextMessage;
import zoo.monkeys.banana.tree.wechat.message.constants.MsgType;

/**
 * @author Hofoo
 * @since 2017-10-15 17:29
 */
public class MessageFactory {

    public static Message createTextMessage(String toUserName, String fromUserName, String content) {
        TextMessage textMessage = new TextMessage(MsgType.TEXT);
        textMessage.setToUserName(toUserName);
        textMessage.setFromUserName(fromUserName);
        textMessage.setContent(content);
        return textMessage;
    }


    public static NewsMessage createNewsMessage(String toUserName, String fromUserName) {
        NewsMessage msg = new NewsMessage(MsgType.NEWS);
        msg.setToUserName(toUserName);
        msg.setFromUserName(fromUserName);
        return msg;
    }

    public static String getMenuText() {
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
        sb.append("1. 公众号介绍\n");
        sb.append("回复?调出此菜单。");
        return sb.toString();
    }
}
