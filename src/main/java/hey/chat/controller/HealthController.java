package hey.chat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping(value = "/health")
    public Map<String, String>  health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        return health;
    }
}
