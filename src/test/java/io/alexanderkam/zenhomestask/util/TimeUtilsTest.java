package io.alexanderkam.zenhomestask.util;

import io.alexanderkam.zenhomestask.exception.DurationFormatException;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class TimeUtilsTest {

    @Test
    void shouldConvertStringToDuration() {
        //given
        String duration = "24h";

        //when
        Duration d = TimeUtils.convertDuration(duration);

        //then
        assertEquals(Duration.of(24, ChronoUnit.HOURS).toMillis(), d.toMillis());
    }

    @Test
    void shouldThrowExceptionIfDurationHasInvalidFormat() {
        //given
        String duration = "h2hDb";

        //expect
        assertThrows(DurationFormatException.class,
                () -> TimeUtils.convertDuration(duration));
    }

}