package com.thermostate.location.infrastructure.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LocationJpaRepo extends JpaRepository<LocationJpa, String> {
}