package io.alexanderkam.zenhomestask.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElectricityCounterCallbackDto {

    long counterId;

    double amount;

}
