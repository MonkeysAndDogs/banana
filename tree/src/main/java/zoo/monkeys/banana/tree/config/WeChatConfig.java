package zoo.monkeys.banana.tree.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author Hofoo
 * @since 2017-10-03 16:10
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WeChatConfig {

    @NotNull
    private String token;
}
