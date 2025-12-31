package com.pastebin.controller;

import com.pastebin.model.Paste;
import com.pastebin.service.PasteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.Optional;

@RestController
public class PasteViewController {

    private final PasteService service;

    public PasteViewController(PasteService service) {
        this.service = service;
    }

    @GetMapping("/p/{id}")
    public ResponseEntity<String> view(@PathVariable String id,
                                       HttpServletRequest request) {

        Optional<Paste> opt = service.getAvailable(id, request);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body("Not Found");
        }

        Paste paste = opt.get();

        return ResponseEntity.ok(
                "<html><body><pre>" +
                HtmlUtils.htmlEscape(paste.getContent()) +
                "</pre></body></html>"
        );
    }
}
