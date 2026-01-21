package com.xavierbouclet.ot;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class HelloMetrics {

    private  final Counter greetCounter;

    public HelloMetrics(MeterRegistry registry) {
        this.greetCounter = Counter.builder("app.greet.calls")
                .description("Number of calls to greet endpoint")
                .register(registry);
    }

    public void incHello() {
        greetCounter.increment();
    }
}
