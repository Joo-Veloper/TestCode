package io.cafekiosk.spring.global.common.service.port;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public class ClockHolderTest implements ClockHolder{
    private final long mills;
    @Override
    public long mills() {
        return mills;
    }
}