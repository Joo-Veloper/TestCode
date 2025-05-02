package io.cafekiosk.spring.global.common.service.infrastructure;

import io.cafekiosk.spring.global.common.service.port.ClockHolder;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class SystemClockHolder implements ClockHolder {
    @Override
    public long mills() {
        return Clock.systemUTC().millis();
    }
}
