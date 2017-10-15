package zoo.monkeys.banana.tree.wechat.message;

import lombok.Data;

import java.util.Date;

/**
 * @author Hofoo
 * @since 2017-10-15 17:22
 */
@Data
public abstract class BaseMessage implements WeChatMessage{

    /* 开发者微信号 */
    private String ToUserName;

    /* 发送方帐号（一个OpenID）*/
    private String FromUserName;

    /* CreateTime */
    private Long CreateTime = new Date().getTime();

    /* 消息类型 */
    private String MsgType;

}
