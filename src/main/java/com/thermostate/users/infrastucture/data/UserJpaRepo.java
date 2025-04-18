package com.thermostate.users.infrastucture.data;

import com.thermostate.users.domain.User;
import com.thermostate.users.domain.UserRepo;
import org.springframework.stereotype.Component;

@Component
public class UserJpaRepo implements UserRepo {
    final Users repo;

    public UserJpaRepo(Users repo) {
        this.repo = repo;
    }

    @Override
    public void create(User user) {
        repo.save(UserJpa.fromDomain(user));

    }

    @Override
    public User getByName(String name) {
        return repo.findByNameIgnoreCase(name).map(UserJpa::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
