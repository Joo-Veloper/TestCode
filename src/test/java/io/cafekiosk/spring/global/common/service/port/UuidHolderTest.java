package io.cafekiosk.spring.global.common.service.port;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UuidHolderTest implements UuidHolder{

    private final String uuid;
    @Override
    public String random() {
        return uuid;
    }
}