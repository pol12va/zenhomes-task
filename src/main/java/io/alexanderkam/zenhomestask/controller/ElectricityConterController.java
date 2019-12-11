package io.alexanderkam.zenhomestask.controller;

import io.alexanderkam.zenhomestask.dto.ConsumptionReportDto;
import io.alexanderkam.zenhomestask.dto.ElectricityCounterCallbackDto;
import io.alexanderkam.zenhomestask.dto.ElectricityCounterDto;
import io.alexanderkam.zenhomestask.service.ElectricityConsumptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ElectricityConterController {

    private final ElectricityConsumptionService electricityConsumptionService;

    @PostMapping(path = "/counter_callback")
    public void counterCallback(@RequestBody @Valid ElectricityCounterCallbackDto dto) {
        log.info("Calling counter callback for [{}]", dto);
        electricityConsumptionService.counterCallback(dto);
    }

    @GetMapping(path = "counter")
    public ElectricityCounterDto getById(@RequestParam("id") int id) {
        log.info("Get counter with id=[{}]", id);
        return electricityConsumptionService.getCounter(id);
    }

    @GetMapping(path = "consumption_report")
    public ConsumptionReportDto buildConsumptionReport(@RequestParam("duration") String durationString) {
        log.info("Building report for last [{}]", durationString);
        return electricityConsumptionService.buildReport(durationString);
    }

}
