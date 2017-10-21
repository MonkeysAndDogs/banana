package zoo.monkeys.banana.tree.wechat.message.constants;

/**
 * @author Hofoo
 * @since 2017-10-15 17:37
 */
public class MsgType {
    public static final String TEXT = "text";
    public static final String NEWS = "news";
    public static final String IMAGE = "image";
    public static final String VOICE = "voice";
    public static final String VIDEO = "video";
    public static final String SHORT_VIDEO = "shortvideo";
    public static final String LOCATION = "location";
    public static final String LINK = "link";

    public static final String EVENT = "event";


    public class EventType {
        public static final String SUBSCRIBE = "subscribe";
        public static final String UNSUBSCRIBE = "unsubscribe";
        public static final String SCAN = "SCAN";
        public static final String LOCATION = "LOCATION";
        public static final String CLICK = "CLICK";
        public static final String VIEW = "VIEW";
    }
}
