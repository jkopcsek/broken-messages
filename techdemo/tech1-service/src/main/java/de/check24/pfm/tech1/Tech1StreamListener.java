package de.check24.pfm.tech1;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author jan.kopcsek
 */
@Component
public class Tech1StreamListener {
    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public Flux<String> convert(Flux<String> input) {
        return input.map(String::toUpperCase);
    }
}
