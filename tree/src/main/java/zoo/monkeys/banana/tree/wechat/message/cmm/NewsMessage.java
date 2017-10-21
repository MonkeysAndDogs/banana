package zoo.monkeys.banana.tree.wechat.message.cmm;

import lombok.Data;
import zoo.monkeys.banana.tree.wechat.message.BaseMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hofoo
 * @since 2017-10-20 14:29
 */
@Data
public class NewsMessage extends BaseMessage {
    private int ArticleCount;
    private List<NewsItem> Articles = new ArrayList<>();

    public NewsMessage(String msgType) {
        this.setMsgType(msgType);
    }
}
