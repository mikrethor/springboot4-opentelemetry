package com.xavierbouclet.ot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class HomeRouter {

    private static final Logger log = LoggerFactory.getLogger(HomeRouter.class);

    @Bean
    RouterFunction<ServerResponse> routes() {
        return route(GET("/hello"), request -> {
            log.info("Hello endpoint called");
            return ServerResponse.ok().body("Hello World!");
        })
                .andRoute(GET("/greet/{name}"), request -> {
                            String name = request.pathVariable("name");
                            log.info("Greeting user: {}", name);
                            simulateWork();
                            return ServerResponse.ok().body("Hello, " + name + "!");
                        }
                )
                .andRoute(GET("/slow"), request -> {
                    try {
                        log.info("Starting slow operation");
                        Thread.sleep(500);
                        log.info("Slow operation completed");
                        return ServerResponse.ok().body("Done!");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return ServerResponse.status(500).body("Interrupted");
                    }
                });
    }


    private void simulateWork() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}