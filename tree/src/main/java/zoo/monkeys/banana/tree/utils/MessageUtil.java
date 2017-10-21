package zoo.monkeys.banana.tree.utils;

import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import zoo.monkeys.banana.tree.wechat.message.Message;
import zoo.monkeys.banana.tree.wechat.message.cmm.NewsItem;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hofoo
 * @since 2017-10-03 18:07
 */
public class MessageUtil {

    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, String> rtnMap = new HashMap<>();

        SAXReader reader = new SAXReader();

        try (ServletInputStream in = request.getInputStream()) {
            Document root = reader.read(in);
            Element rootElement = root.getRootElement();

            List<Element> elements = rootElement.elements();
            elements.forEach(e -> rtnMap.put(e.getName(), e.getText()));
        }

        return rtnMap;
    }

    public static String textMessageToXml(Message msg) {
        if (null == msg) {
            return "";
        }
        XStream xStream = new XStream();
        xStream.alias("xml", msg.getClass());
        xStream.alias("item", new NewsItem().getClass());
        return xStream.toXML(msg);
    }
}
