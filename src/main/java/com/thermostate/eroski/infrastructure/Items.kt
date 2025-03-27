package com.thermostate.eroski.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface Items : JpaRepository<ItemJpa, UUID>
