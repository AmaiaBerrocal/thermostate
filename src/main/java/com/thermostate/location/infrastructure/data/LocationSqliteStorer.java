package com.thermostate.location.infrastructure.data;

import com.thermostate.location.domain.Location;
import com.thermostate.location.domain.LocationStore;
import com.thermostate.shared.domain.criteria.Criteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
public class LocationSqliteStorer implements LocationStore {
    final EntityManager entityManager;
    final LocationJpaRepo repo;

    @Override
    public void save(Location location) {
        try {
            var res = repo.save(LocationJpa.fromDomain(location));
            System.out.println("Saved " + res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Location> matching(Criteria criteria) {
        StringBuilder hql = new StringBuilder("SELECT * FROM Locations l WHERE ");
        hql.append(criteria.toSql());

        System.out.println(hql);

        Query query = entityManager.createNativeQuery(hql.toString(), LocationJpa.class);

        return query.getResultList().stream().map(
                jpa ->
                        ((LocationJpa)jpa).toDomain()).toList(); //query.getResultList().stream().map(LocationJpa::toDomain).toList();
    }
}
