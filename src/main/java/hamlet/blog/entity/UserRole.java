package hamlet.blog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_roles")
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "userRole")
    private List<User> users;

    private String name;

    @Column(name = "service_name")
    private String serviceName;

}

