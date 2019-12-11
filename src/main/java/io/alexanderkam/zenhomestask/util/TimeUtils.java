package io.alexanderkam.zenhomestask.util;

import io.alexanderkam.zenhomestask.exception.DurationFormatException;
import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class TimeUtils {

    private static final String DURATION_REGEX = "(([1-9][0-9]+)([h]))";
    private static final Pattern PATTERN = Pattern.compile(DURATION_REGEX);

    public Duration convertDuration(String durationString) {
        Matcher m = PATTERN.matcher(durationString);
        if (m.matches()) {
            int amount = Integer.parseInt(m.group(2));
            return Duration.of(amount, ChronoUnit.HOURS);
        } else {
            throw new DurationFormatException(durationString);
        }
    }

}
