package com.xavierbouclet.ot;

import io.micrometer.observation.annotation.ObservationKeyValue;
import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @Observed(name = "simulate.work", contextualName = "simulate-work")
    public void simulateWork(@ObservationKeyValue("time") long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
