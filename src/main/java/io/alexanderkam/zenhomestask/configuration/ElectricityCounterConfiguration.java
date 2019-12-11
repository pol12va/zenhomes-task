package io.alexanderkam.zenhomestask.configuration;

import io.alexanderkam.zenhomestask.repository.ElectricityCounterRepository;
import io.alexanderkam.zenhomestask.service.ElectricityConsumptionService;
import io.alexanderkam.zenhomestask.service.ElectricityConsumptionServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ElectricityCounterConfiguration {

    @Bean
    Clock utcClock() {
        return Clock.systemUTC();
    }

    @Bean
    ElectricityConsumptionService electricityCounterService(ElectricityCounterRepository repository,
                                                            Clock clock) {
        return new ElectricityConsumptionServiceImpl(repository, clock);
    }

}
