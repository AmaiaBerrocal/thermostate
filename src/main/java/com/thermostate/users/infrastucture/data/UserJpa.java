package com.thermostate.users.infrastucture.data;

import com.thermostate.users.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "Users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserJpa {
    @Id
    private String uuid;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String salt;
    @Column
    private String email;
    @Column
    private Boolean active;
    @Column
    private Integer role;

    public static UserJpa fromDomain(User user) {
        return new UserJpa(
                user.getId().toString(),
                user.getName(),
                user.getPassword(),
                user.getSalt(),
                user.getEmail(),
                user.getIsActive(),
                user.getRole().value);
    }

    public User toDomain() {
        return User.with(UUID.fromString(this.uuid),
                this.name,
                this.password,
                this.email,
                this.salt,
                UserRole.valueOf(this.role),
                this.active);
    }
}
