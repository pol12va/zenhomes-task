package io.alexanderkam.zenhomestask.service;

import io.alexanderkam.zenhomestask.dto.ConsumptionReportDto;
import io.alexanderkam.zenhomestask.dto.ElectricityCounterCallbackDto;
import io.alexanderkam.zenhomestask.dto.ElectricityCounterDto;
import io.alexanderkam.zenhomestask.entity.ElectricityCounterEntity;
import io.alexanderkam.zenhomestask.exception.CounterNotFoundException;
import io.alexanderkam.zenhomestask.repository.ElectricityCounterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElectricityConsumptionServiceImplTest {

    private static final long ID = 1;
    private static final double AMOUNT = 100.73;
    private static final double OLD_AMOUNT = 80.73;
    private static final String VILLAGE_NAME = "village";
    private static final long ID_2 = 2;
    private static final double AMOUNT_2 = 122.73;
    private static final String VILLAGE_NAME_2 = "village2";
    private static final long FIXED_TS = 1576087055000L;

    @Captor
    private ArgumentCaptor<ElectricityCounterEntity> entityArgumentCaptor;

    private Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(FIXED_TS), ZoneId.of("UTC"));
    private ElectricityCounterRepository repository = Mockito.mock(ElectricityCounterRepository.class);
    private ElectricityConsumptionServiceImpl electricityConsumptionService
            = new ElectricityConsumptionServiceImpl(repository, fixedClock);

    @Test
    void shouldUpdateCounterInCallbackIfPresentInDb() {
        //given
        ElectricityCounterCallbackDto dto = createCallbackDto();

        //and
        when(repository.findById(ID)).thenReturn(Optional.of(createEntity(ID, VILLAGE_NAME, OLD_AMOUNT)));

        //when
        electricityConsumptionService.counterCallback(dto);

        //then
        verify(repository).findById(ID);
        verify(repository).save(entityArgumentCaptor.capture());

        //and
        ElectricityCounterEntity captured = entityArgumentCaptor.getValue();
        assertEquals(ID, captured.getId());
        assertEquals(VILLAGE_NAME, captured.getVillageName());
        assertEquals(BigDecimal.valueOf(AMOUNT), captured.getConsumption());
    }

    @Test
    void shouldThrowExceptionIfCounterWithSpecifiedIdNotFound() {
        //given
        ElectricityCounterCallbackDto dto = createCallbackDto();

        //and
        when(repository.findById(ID)).thenReturn(Optional.empty());

        //when
        assertThrows(CounterNotFoundException.class, () ->
                electricityConsumptionService.counterCallback(dto));
    }

    @Test
    void shouldGetCounterById() {
        //given
        when(repository.findById(ID)).thenReturn(Optional.of(createEntity()));

        //when
        ElectricityCounterDto dto = electricityConsumptionService.getCounter(ID);

        //then
        verify(repository).findById(ID);

        //and
        assertEquals(ID, dto.getId());
        assertEquals(VILLAGE_NAME, dto.getVillageName());
    }

    @Test
    void shouldThrowExceptionIfCounterNotFound() {
        //given
        when(repository.findById(ID)).thenReturn(Optional.empty());

        //expect
        assertThrows(CounterNotFoundException.class,
                () -> electricityConsumptionService.getCounter(ID));
    }

    @Test
    void shouldBuildReportOfElectricityConsumption() {
        //given
        String duration = "24h";
        //and
        LocalDateTime expectedLastHours = LocalDateTime.now(fixedClock).minus(Duration.ofHours(24));
        //and
        Collection<ElectricityCounterEntity> foundEntities = Arrays.asList(
                createEntity(), createEntity(ID_2, VILLAGE_NAME_2, AMOUNT_2)
        );
        //and
        when(repository.findByTimestampLargerThan(expectedLastHours))
                .thenReturn(foundEntities);

        //when
        ConsumptionReportDto report = electricityConsumptionService.buildReport(duration);

        //then
        verify(repository).findByTimestampLargerThan(expectedLastHours);

        //and
        assertEquals(2, report.getVillages().size());
        //and
        assertTrue(report.getVillages().contains(createItem(VILLAGE_NAME, AMOUNT)));
        assertTrue(report.getVillages().contains(createItem(VILLAGE_NAME_2, AMOUNT_2)));
    }

    static ElectricityCounterCallbackDto createCallbackDto() {
        return ElectricityCounterCallbackDto.builder()
                .counterId(ID)
                .amount(AMOUNT)
                .build();
    }

    static ElectricityCounterEntity createEntity() {
        return createEntity(ID, VILLAGE_NAME, AMOUNT);
    }

    static ElectricityCounterEntity createEntity(long id, String villageName, double consumption) {
        return ElectricityCounterEntity.builder()
                .id(id)
                .villageName(villageName)
                .consumption(BigDecimal.valueOf(consumption))
                .build();
    }

    static ConsumptionReportDto.Item createItem(String villageName, double consumption) {
        return ConsumptionReportDto.Item.builder()
                .villageName(villageName)
                .consumption(consumption)
                .build();
    }

}