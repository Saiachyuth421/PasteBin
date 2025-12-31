package com.pastebin.controller;

import com.pastebin.dto.*;
import com.pastebin.model.Paste;
import com.pastebin.service.PasteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pastes")
public class PasteApiController {

    private final PasteService service;

    public PasteApiController(PasteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreatePasteRequest req,
                                    HttpServletRequest request) {

        if (req.getContent() == null || req.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "content is required"));
        }
        if (req.getTtl_seconds() != null && req.getTtl_seconds() < 1) {
            return ResponseEntity.badRequest().body(Map.of("error", "invalid ttl_seconds"));
        }
        if (req.getMax_views() != null && req.getMax_views() < 1) {
            return ResponseEntity.badRequest().body(Map.of("error", "invalid max_views"));
        }

        Paste paste = service.create(
                req.getContent(),
                req.getTtl_seconds(),
                req.getMax_views(),
                request
        );

        String baseUrl = request.getRequestURL().toString()
                .replace(request.getRequestURI(), "");

        return ResponseEntity.ok(
                new CreatePasteResponse(
                        paste.getId(),
                        baseUrl + "/p/" + paste.getId()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetch(@PathVariable String id,
                                   HttpServletRequest request) {

        Optional<Paste> opt = service.getAvailable(id, request);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "not found"));
        }

        Paste paste = service.incrementViews(opt.get());

        Integer remaining = paste.getMaxViews() == null
                ? null
                : Math.max(0, paste.getMaxViews() - paste.getViews());

        String expiresAt = paste.getExpiresAt() == null
                ? null
                : Instant.ofEpochMilli(paste.getExpiresAt()).toString();

        return ResponseEntity.ok(
                new PasteResponse(
                        paste.getContent(),
                        remaining,
                        expiresAt
                )
        );
    }
}
