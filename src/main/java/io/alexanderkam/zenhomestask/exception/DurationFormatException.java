package io.alexanderkam.zenhomestask.exception;

public class DurationFormatException extends RuntimeException {

    private static final String DURATION_FORMAT_ERROR_MSG = "Duration [%s] has invalid format";

    public DurationFormatException(String durationString) {
        super(String.format(DURATION_FORMAT_ERROR_MSG, durationString));
    }
}
