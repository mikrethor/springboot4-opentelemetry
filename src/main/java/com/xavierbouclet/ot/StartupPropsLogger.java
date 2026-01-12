package com.xavierbouclet.ot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
class StartupPropsLogger implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(StartupPropsLogger.class);
    private final Environment env;

    StartupPropsLogger(Environment env) {
        this.env = env;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Active profiles: {}", String.join(",", env.getActiveProfiles()));
        log.info("Default profiles: {}", String.join(",", env.getDefaultProfiles()));

        logProp("server.port");
        logProp("management.server.port");
        logProp("spring.application.name");
        logProp("spring.otlp.metrics.export.url");
        logProp("spring.opentelemetry.tracing.export.otlp.endpoint");
        logProp("spring.opentelemetry.logging.export.otlp.endpoint");
    }

    private void logProp(String key) {
        String value = env.getProperty(key);
        log.info("{}={}", key, value);
    }
}
