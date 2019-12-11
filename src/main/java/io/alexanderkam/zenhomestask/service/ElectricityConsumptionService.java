package io.alexanderkam.zenhomestask.service;

import io.alexanderkam.zenhomestask.dto.ConsumptionReportDto;
import io.alexanderkam.zenhomestask.dto.ElectricityCounterCallbackDto;
import io.alexanderkam.zenhomestask.dto.ElectricityCounterDto;

public interface ElectricityConsumptionService {

    void counterCallback(ElectricityCounterCallbackDto callbackDto);

    ElectricityCounterDto getCounter(long id);

    ConsumptionReportDto buildReport(String duration);

}
