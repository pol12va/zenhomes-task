package io.alexanderkam.zenhomestask.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Collection;

@Value
@Builder
public class ConsumptionReportDto {

    Collection<Item> villages;

    @Value
    @Builder
    public static class Item {
        String villageName;
        double consumption;
    }

}
