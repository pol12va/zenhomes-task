package io.alexanderkam.zenhomestask.controller;

import io.alexanderkam.zenhomestask.dto.ConsumptionReportDto;
import io.alexanderkam.zenhomestask.service.ElectricityConsumptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ElectricityConterController.class)
class ElectricityConterControllerTest {

    private static final String DURATION = "24h";
    private static final String VILLAGE_NAME = "Villarriba";
    private static final double CONSUMPTION = 12345.123;
    private static final String VILLAGE_NAME_2 = "Villabajo";
    private static final double CONSUMPTION_2 = 23456.123;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ElectricityConsumptionService consumptionService;

    @Test
    void shouldReturnElectricityConsumptionReport() throws Exception {
        //given
        when(consumptionService.buildReport(DURATION))
                .thenReturn(ConsumptionReportDto.builder()
                        .villages(Arrays.asList(
                                createItem(VILLAGE_NAME, CONSUMPTION),
                                createItem(VILLAGE_NAME_2, CONSUMPTION_2)))
                        .build());

        //expect
        mockMvc.perform(get("/consumption_report")
                .param("duration", "24h")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.villages").exists())
                .andExpect(jsonPath("$.villages[0].village_name").value(VILLAGE_NAME))
                .andExpect(jsonPath("$.villages[0].consumption").value(CONSUMPTION))
                .andExpect(jsonPath("$.villages[1].village_name").value(VILLAGE_NAME_2))
                .andExpect(jsonPath("$.villages[1].consumption").value(CONSUMPTION_2));

        //and
        verify(consumptionService).buildReport(DURATION);
    }

    static ConsumptionReportDto.Item createItem(String villageName, double consumption) {
        return ConsumptionReportDto.Item.builder()
                .villageName(villageName)
                .consumption(consumption)
                .build();
    }

}