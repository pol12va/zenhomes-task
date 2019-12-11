package io.alexanderkam.zenhomestask.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElectricityCounterDto {

    long id;

    String villageName;

}
