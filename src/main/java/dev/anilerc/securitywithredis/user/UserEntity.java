package dev.anilerc.securitywithredis.user;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.*;

@Entity
@Builder
@Table(name = "USERS")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Setter
    private String username;

    @Setter
    private String password;


}
