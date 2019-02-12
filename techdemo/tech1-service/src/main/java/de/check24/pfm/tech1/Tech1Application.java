package de.check24.pfm.tech1;

import io.nats.client.Connection;
import io.nats.client.Nats;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
@EnableBinding
///@EnableWebFlux
public class Tech1Application {
    @Bean
    public Connection nats() throws IOException, InterruptedException {
        return Nats.connect();
    }

//    @Bean
//    public Tech1Stream tech1Stream() {
//        return new Tech1Stream();
//    }
//
//    @Bean
//    public HandlerMapping webSocketHandlerMapping() {
//        Map<String, WebSocketHandler> map = new HashMap<>();
//        map.put("/stream/subject/*", tech1Stream());
//        map.put("/stream/subject/data", tech1Stream());
//
//        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
//        handlerMapping.setOrder(1);
//        handlerMapping.setUrlMap(map);
//        return handlerMapping;
//    }
//
//    @Bean
//    public WebSocketHandlerAdapter handlerAdapter() {
//        return new WebSocketHandlerAdapter(webSocketService());
//    }
//
//    @Bean
//    public WebSocketService webSocketService() {
//        TomcatRequestUpgradeStrategy strategy = new TomcatRequestUpgradeStrategy();
//        strategy.setMaxSessionIdleTimeout(0L);
//        return new HandshakeWebSocketService(strategy);
//    }

    public static void main(String[] args) {
        SpringApplication.run(Tech1Application.class, args);
    }
}