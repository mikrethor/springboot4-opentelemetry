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
public class HelloRouter {

    private static final Logger log = LoggerFactory.getLogger(HelloRouter.class);

    private final HelloMetrics helloMetrics;
    private final HelloService helloService;

    public HelloRouter(HelloMetrics helloMetrics, HelloService helloService) {
        this.helloMetrics = helloMetrics;
        this.helloService = helloService;
    }

    @Bean
    RouterFunction<ServerResponse> routes() {
        return route(GET("/hello"), request -> {
            log.info("Hello endpoint called");
            helloMetrics.incHello();
            return ServerResponse.ok().body("Hello World!");
        })
                .andRoute(GET("/greet/{name}"), request -> {
                            String name = request.pathVariable("name");
                            log.info("Greeting user: {}", name);
                            helloService.simulateWork(50);
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



}