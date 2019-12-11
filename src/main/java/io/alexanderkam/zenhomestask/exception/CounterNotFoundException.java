package io.alexanderkam.zenhomestask.exception;

public class CounterNotFoundException extends RuntimeException {

    private static final String COUNTER_NOT_FOUND_MSG = "Counter with id=[%d] not found";

    public CounterNotFoundException(long id) {
        super(String.format(COUNTER_NOT_FOUND_MSG, id));
    }
}
