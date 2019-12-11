package io.alexanderkam.zenhomestask.service;

import io.alexanderkam.zenhomestask.converter.ElectricityCounterConverter;
import io.alexanderkam.zenhomestask.dto.ConsumptionReportDto;
import io.alexanderkam.zenhomestask.dto.ElectricityCounterCallbackDto;
import io.alexanderkam.zenhomestask.dto.ElectricityCounterDto;
import io.alexanderkam.zenhomestask.entity.ElectricityCounterEntity;
import io.alexanderkam.zenhomestask.exception.CounterNotFoundException;
import io.alexanderkam.zenhomestask.repository.ElectricityCounterRepository;
import io.alexanderkam.zenhomestask.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ElectricityConsumptionServiceImpl implements ElectricityConsumptionService {

    private final ElectricityCounterRepository counterRepository;
    private final Clock clock;

    @Override
    @Transactional
    public void counterCallback(ElectricityCounterCallbackDto callbackDto) {
        Optional<ElectricityCounterEntity> entityOptional = counterRepository.findById(callbackDto.getCounterId());

        ElectricityCounterEntity entityToSave;
        if (entityOptional.isPresent()) {
            ElectricityCounterEntity entity = entityOptional.get();
            entityToSave = entity.toBuilder()
                    .consumption(BigDecimal.valueOf(callbackDto.getAmount()))
                    .build();

            counterRepository.save(entityToSave);
        } else {
            log.error("Counter with id=[{}] not found", callbackDto.getCounterId());
            throw new CounterNotFoundException(callbackDto.getCounterId());
        }
    }

    @Override
    public ElectricityCounterDto getCounter(long id) {
        Optional<ElectricityCounterEntity> entityOptional = counterRepository.findById(id);

        return entityOptional
                .map(ElectricityCounterConverter::fromEntityToCounterDto)
                .orElseThrow(() -> new CounterNotFoundException(id));
    }

    @Override
    public ConsumptionReportDto buildReport(String durationString) {
        Duration duration = TimeUtils.convertDuration(durationString);
        LocalDateTime hoursAgo = LocalDateTime.now(clock).minus(duration);
        Collection<ConsumptionReportDto.Item> items = counterRepository.findByTimestampLargerThan(hoursAgo).stream()
                .map(ElectricityCounterConverter::fromEntityToConsumptionReportItem)
                .collect(Collectors.toList());
        return ConsumptionReportDto.builder().villages(items).build();
    }
}
