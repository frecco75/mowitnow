package com.bbc.automower;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;
import java.util.List;


public class TestAppender extends AppenderSkeleton {

    public static final List<String> messages = new ArrayList<>();

    @Override
    protected void append(final LoggingEvent event) {
        messages.add(event.getMessage().toString());
    }

    @Override
    public void close() {
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    public static void clear() {
        messages.clear();
    }
}
