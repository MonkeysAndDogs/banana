package zoo.monkeys.banana.tree.user.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import zoo.monkeys.banana.tree.BaseTest;
import zoo.monkeys.banana.tree.user.model.User;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Slf4j
public class UserRepositoryTest extends BaseTest {

    @Resource
    private UserRepository userRepository;

    @Test
    public void testSave() {
        User user = new User();
        user.setName("Jay");
        user.setPassword("123456");
        user.setBirthday(new Date());
        userRepository.save(user);
    }

    @Test
    public void testInsert() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("Jay_" + i);
            user.setPassword("123456");
            user.setBirthday(new Date());
            userRepository.save(user);
        }
    }

    @Test
    public void testFind() {
        User jay = userRepository.findByNameAndPassword("Jay", "123456");
        log.info("testFind:{}", jay.toString());
    }

    @Test
    public void testFindLike() {
        User jay = userRepository.findByNameLike("%ay%");
        log.info("testFindLike:{}", jay.toString());
    }

    @Test
    public void testCount() {
        log.info("testCount:{}", userRepository.count());
    }


    @Test
    public void testPage() {
        int page = 0, size = 10;
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<User> all = userRepository.findAll(pageable);
        all.forEach(e -> log.info("testPage:{}", e));
    }

    @Test
    public void testMyDefineDelete() {
        userRepository.deleteByUserId(1L);
    }

    @Test
    public void testSpecification() {

        LocalDateTime time = LocalDateTime.of(2017, 11, 7, 16, 58, 3);
        Date date = Date.from(time.toInstant(OffsetDateTime.now().getOffset()));
        List<User> list = userRepository.findAll((Specification<User>) (root, query, cb) -> {
            Predicate p1 = cb.like(root.get("name"), "%Jay%");
            Predicate p2 = cb.lessThan(root.get("birthday"), date);
            //将两个查询条件联合起来之后返回Predicate对象
            return cb.and(p1, p2);
        });

        list.forEach(e -> log.info("testSpecification:{}", e));
    }
}
