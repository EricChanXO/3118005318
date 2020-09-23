package com.cyq.papercheck.service;

import com.cyq.papercheck.service.impl.SimHashServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimHashServiceTest {

    @Test
    public void getSimliar() {
        SimHashService simHashService = new SimHashServiceImpl();
        Assertions.assertEquals(simHashService.getSimliar(1), 0.9973557010035817);
        Assertions.assertEquals(simHashService.getSimliar(500), 0);
    }
}