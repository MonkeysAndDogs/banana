package zoo.monkeys.banana.tree.user.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Hofoo
 * @since 2017-11-07 15:58
 */
@Entity
@Table
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private Date birthday;
}
