package ru.kerchik.linkShortener.repository.impl;

import org.springframework.stereotype.Service;
import ru.kerchik.linkShortener.model.LinkInfo;
import ru.kerchik.linkShortener.repository.LinkInfoRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LinkInfoRepositoryImpl implements LinkInfoRepository {
    private final Map<String, LinkInfo> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<LinkInfo> findByShortLink(String shortLink) {
        return Optional.ofNullable(storage.get(shortLink));
    }

    @Override
    public LinkInfo saveShortLink(LinkInfo linkInfo) {
        linkInfo.setId(UUID.randomUUID());
        storage.put(linkInfo.getShortLink(), linkInfo);

        return linkInfo;
    }

    @Override
    public List<LinkInfo> getAll() {
        List<LinkInfo> allLinks = new ArrayList<>();

        for (var entry : storage.entrySet()) {
            allLinks.add(entry.getValue());
        }

        return allLinks;
    }

    @Override
    public void delete(UUID id) {
        for (LinkInfo linkInfo : storage.values()) {
            if (linkInfo.getId().equals(id)) {
                storage.remove(linkInfo.getShortLink());
            }
        }
    }
}
