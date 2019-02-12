package de.check24.pfm.tech1;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author jan.kopcsek
 */
@Component
public class Tech1Nats {
    @Resource
    private Connection nats;

    private Dispatcher dispatcher;

    @PostConstruct
    public void register() {
        this.dispatcher = nats.createDispatcher(this::dispatch);
        this.dispatcher.subscribe("tech.requests");
    }

    @PreDestroy
    public void unregister() {
        nats.closeDispatcher(this.dispatcher);
    }

    private void dispatch(Message message) {
        String text = new String(message.getData());
        nats.publish(message.getReplyTo(), text.toUpperCase().getBytes());
    }
}
