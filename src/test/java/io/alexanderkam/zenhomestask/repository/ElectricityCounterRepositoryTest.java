package io.alexanderkam.zenhomestask.repository;

import io.alexanderkam.zenhomestask.entity.ElectricityCounterEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ElectricityCounterRepositoryTest {

    private static final long ID = 1;
    private static final String VILLAGE_NAME = "village";
    private static final BigDecimal CONSUMPTION = BigDecimal.valueOf(1000.77);

    @Autowired
    private ElectricityCounterRepository repository;

    @Test
    void shouldSaveCounter() {
        //given
        ElectricityCounterEntity entity = createCounter();

        //when
        long id = repository.save(entity).getId();

        //then
        Optional<ElectricityCounterEntity> targetEntityOptional = repository.findById(id);
        assertTrue(targetEntityOptional.isPresent());

        //and
        ElectricityCounterEntity targetEntity = targetEntityOptional.get();
        assertEquals(VILLAGE_NAME, targetEntity.getVillageName());
        assertEquals(CONSUMPTION, targetEntity.getConsumption());
    }

    @Test
    void shouldGetCounterInTimeFrameRange() {
        //given
        ElectricityCounterEntity entity = createCounter();
        //and
        LocalDateTime weekAgo = LocalDateTime.now().minusWeeks(1);

        //when
        repository.save(entity);

        //then
        Collection<ElectricityCounterEntity> entities = repository.findByTimestampLargerThan(weekAgo);
        assertFalse(entities.isEmpty());
    }

    private ElectricityCounterEntity createCounter() {
        return ElectricityCounterEntity.builder()
                .id(ID)
                .villageName(VILLAGE_NAME)
                .consumption(CONSUMPTION)
                .build();
    }

}