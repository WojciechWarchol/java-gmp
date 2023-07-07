package com.wojto.service.core;

import com.netflix.servo.DefaultMonitorRegistry;
import com.netflix.servo.MonitorRegistry;
import com.netflix.servo.monitor.*;
import com.netflix.servo.publish.*;
import com.netflix.servo.publish.graphite.GraphiteMetricObserver;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

@Service
public class ServiceOneImpl implements ServiceOne {

    private static final String ZUUL_URL = "http://apigateway:8762/";
    private static final String API_PATH = "/api";
    private static final String PROPERTIES_PATH = "/prop";
    private static final String HELLO_MESSAGE_PARAM_PATH = "/property-hello";
    private static final String ONE_MESSAGE_PARAM_PATH = "/property-one";

    private String serviceMessage;

    private Counter basicCounter;
    private Counter peekRateCounter;
    private Timer basicTimer;

    @PostConstruct
    public void init() {
        RestTemplate restTemplate = new RestTemplate();
        basicCounter = new BasicCounter(MonitorConfig.builder("basicCounter").build());
        peekRateCounter = new PeakRateCounter(MonitorConfig.builder("peekRateCounter").build());
        basicTimer = new BasicTimer(MonitorConfig.builder("basicTimer").build(), MILLISECONDS);

        MonitorRegistry monitorRegistry = DefaultMonitorRegistry.getInstance();
        monitorRegistry.register(basicTimer);
        monitorRegistry.register(peekRateCounter);
        monitorRegistry.register(basicTimer);

        MetricObserver graphiteObserver = new GraphiteMetricObserver("servo", "graphite:2003");
        MonitorConfig.Builder configBuilder = null;

//        graphiteObserver.configure(configBuilder.build());
//        graphiteObserver.start();

        PollRunnable pollRunnable = new PollRunnable(
                new MonitorRegistryMetricPoller(monitorRegistry), new BasicMetricFilter(true), graphiteObserver);
        PollScheduler.getInstance().start();
        PollScheduler.getInstance().addPoller(pollRunnable, 1, SECONDS);

/*        // Create a ZipkinReporter
        ZipkinConfig zipkinConfig = ZipkinConfig.builder()
                .setEndpoint(System.getProperty("servo.zipkin.endpoint"))
                .build();
        ZipkinRegistry registry = new ZipkinRegistry(zipkinConfig);
        ZipkinReporter reporter = new ZipkinReporter(registry);

        // Register the Counter with ZipkinReporter
        registry.register(counter);

        // Start the ZipkinReporter
        reporter.start();*/

        Stopwatch stopwatch = basicTimer.start();
        String propertiesUri = ZUUL_URL + API_PATH + PROPERTIES_PATH;
        String helloMessageUri = propertiesUri + HELLO_MESSAGE_PARAM_PATH;
        String oneMessageUri = propertiesUri + ONE_MESSAGE_PARAM_PATH;

        String helloMessage = restTemplate.exchange(helloMessageUri, HttpMethod.GET, null, String.class).getBody();
        String oneInMessage = restTemplate.exchange(oneMessageUri, HttpMethod.GET, null, String.class).getBody();

        serviceMessage = helloMessage + " " + oneInMessage;
        stopwatch.stop();
        System.out.println("Fetched configuration for service " + oneInMessage + " in " + basicTimer.getValue() + " milliseconds.");
    }

    @PreDestroy
    public void shutdown() {
        PollScheduler.getInstance().stop();
    }

    @Override
    public String getServiceMessage() {
        basicCounter.increment();
        peekRateCounter.increment();
        return serviceMessage;
    }
}
