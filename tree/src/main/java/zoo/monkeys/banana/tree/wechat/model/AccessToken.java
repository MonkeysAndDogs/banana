package zoo.monkeys.banana.tree.wechat.model;

/**
 * @author Hofoo
 * @since 2017-10-18 20:16
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity
public class AccessToken {

    @Id
    private Long id;

    private String accessToken;

    private int expiresIn;

    private Date updateTime;
}
