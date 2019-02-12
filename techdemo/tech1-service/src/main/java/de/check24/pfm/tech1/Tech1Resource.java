package de.check24.pfm.tech1;

import io.nats.client.Connection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 * @author jan.kopcsek
 */
@RestController
public class Tech1Resource {
    @Resource
    private Connection nats;

    @GetMapping("/subject/{subject}/drain")
    public List<MessageTO> drain(@PathVariable("subject") String subject) throws InterruptedException {
        List<MessageTO> messages = new ArrayList<>();
        nats.createDispatcher(msg -> messages.add(MessageTO.fromNutsMessage(msg)))
                .subscribe(subject)
                .drain(Duration.ofSeconds(1));
        return messages;
    }

    @PostMapping(value = "/subject/{subject}/publish")
    public void publish(@PathVariable("subject") String subject,
                       @RequestBody String text) {
        nats.publish(subject, text.getBytes());
    }

    @PostMapping(value = "/subject/{subject}/request", produces = "application/json")
    public MessageTO request(@PathVariable("subject") String subject,
                        @RequestBody String text) throws InterruptedException {
        return MessageTO.fromNutsMessage(nats.request(subject, text.getBytes(), Duration.ofMinutes(1)));
    }


}
