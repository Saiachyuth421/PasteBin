package com.pastebin.service;

import com.pastebin.model.Paste;
import com.pastebin.Repository.PasteRepository;
import com.pastebin.util.TimeProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PasteService {

    private final PasteRepository repository;
    private final TimeProvider timeProvider;

    public PasteService(PasteRepository repository, TimeProvider timeProvider) {
        this.repository = repository;
        this.timeProvider = timeProvider;
    }

    public Paste create(String content, Integer ttl, Integer maxViews, HttpServletRequest request) {
        long now = timeProvider.now(request);

        String id = UUID.randomUUID().toString().substring(0, 8);
        Long expiresAt = ttl == null ? null : now + ttl * 1000L;

        Paste paste = new Paste(id, content, expiresAt, maxViews, 0, now);
        return repository.save(paste);
    }

    public Optional<Paste> getAvailable(String id, HttpServletRequest request) {
        long now = timeProvider.now(request);

        Optional<Paste> opt = repository.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        Paste paste = opt.get();

        if (paste.getExpiresAt() != null && now >= paste.getExpiresAt()) {
            return Optional.empty();
        }

        if (paste.getMaxViews() != null && paste.getViews() >= paste.getMaxViews()) {
            return Optional.empty();
        }

        return Optional.of(paste);
    }

    public Paste incrementViews(Paste paste) {
        paste.setViews(paste.getViews() + 1);
        return repository.save(paste);
    }
}
