package zoo.monkeys.banana.tree.wechat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zoo.monkeys.banana.tree.wechat.model.AccessToken;

/**
 * @author Hofoo
 * @since 2017-10-19 14:16
 */
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
}
