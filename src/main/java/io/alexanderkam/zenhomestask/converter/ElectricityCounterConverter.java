package io.alexanderkam.zenhomestask.converter;

import io.alexanderkam.zenhomestask.dto.ConsumptionReportDto;
import io.alexanderkam.zenhomestask.dto.ElectricityCounterDto;
import io.alexanderkam.zenhomestask.entity.ElectricityCounterEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ElectricityCounterConverter {

    public ElectricityCounterDto fromEntityToCounterDto(ElectricityCounterEntity entity) {
        return ElectricityCounterDto.builder()
                .id(entity.getId())
                .villageName(entity.getVillageName())
                .build();
    }

    public ConsumptionReportDto.Item fromEntityToConsumptionReportItem(ElectricityCounterEntity entity) {
        return ConsumptionReportDto.Item.builder()
                .villageName(entity.getVillageName())
                .consumption(entity.getConsumption().doubleValue())
                .build();
    }

}
