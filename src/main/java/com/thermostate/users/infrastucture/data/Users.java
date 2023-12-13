package com.thermostate.users.infrastucture.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Users extends JpaRepository<UserJpa, Integer> {
    Optional<UserJpa> findByName(String name);
}
