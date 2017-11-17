package zoo.monkeys.banana.tree.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import zoo.monkeys.banana.tree.user.model.User;

import javax.transaction.Transactional;

/**
 * @author Hofoo
 * @since 2017-11-07 16:02
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor {

    User findByNameAndPassword(String name, String password);

    User findByNameLike(String name);

    @Transactional
    @Modifying
    @Query("delete from User where id = ?1")
    void deleteByUserId(Long id);

}
