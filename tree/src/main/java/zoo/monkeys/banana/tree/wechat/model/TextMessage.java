package zoo.monkeys.banana.tree.wechat.model;

import lombok.Data;

/**
 * @author Hofoo
 * @since 2017-10-03 18:35
 */
@Data
public class TextMessage {

    /* 开发者微信号 */
    private String ToUserName;

    /* 发送方帐号（一个OpenID）*/
    private String FromUserName;


    /* CreateTime */
    private Long CreateTime = System.currentTimeMillis()/1000;

    /* text */
    private String MsgType;

    /* 文本消息内容 */
    private String Content;

    /* 消息id，64位整型 */
    private String MsgId;
}
