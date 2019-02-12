package de.check24.pfm.tech3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jan.kopcsek
 */
@RestController
public class Tech3Service {
    @GetMapping("/echo")
    public String echo(String text) {
        return text;
    }
}
