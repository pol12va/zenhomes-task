package io.alexanderkam.zenhomestask.repository;

import io.alexanderkam.zenhomestask.entity.ElectricityCounterEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface ElectricityCounterRepository extends CrudRepository<ElectricityCounterEntity, Long> {

    @Query("SELECT ec FROM ElectricityCounterEntity ec where ec.updateTs >= :updateTs")
    Collection<ElectricityCounterEntity> findByTimestampLargerThan(@Param("updateTs") LocalDateTime updateTs);

}
