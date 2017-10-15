package zoo.monkeys.banana.tree.wechat.message.cmm;

import lombok.Data;
import zoo.monkeys.banana.tree.wechat.message.BaseMessage;

import java.util.Date;

/**
 * @author Hofoo
 * @since 2017-10-03 18:35
 */
@Data
public class TextMessage extends BaseMessage {

    /* 文本消息内容 */
    private String Content;

//    /* 消息id，64位整型 */
//    private String MsgId;

    public TextMessage(String msgType) {
        this.setMsgType(msgType);
    }
}
