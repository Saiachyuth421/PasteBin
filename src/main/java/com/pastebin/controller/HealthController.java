package com.pastebin.controller;

import com.pastebin.Repository.PasteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    private final PasteRepository repository;

    public HealthController(PasteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/healthz")
    public ResponseEntity<Map<String, Boolean>> health() {
        repository.count();
        return ResponseEntity.ok(Map.of("ok", true));
    }
}
