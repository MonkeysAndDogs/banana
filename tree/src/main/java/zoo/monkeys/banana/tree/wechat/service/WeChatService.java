package zoo.monkeys.banana.tree.wechat.service;

import org.json.JSONException;
import zoo.monkeys.banana.tree.wechat.model.MenuResponse;

import java.io.IOException;
import java.util.Map;

public interface WeChatService {

    MenuResponse createMenu() throws JSONException, IOException;

    void refreshAccessToken(boolean isForce);
}
