package ru.kerchik.linkShortener.repository;

import ru.kerchik.linkShortener.model.LinkInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LinkInfoRepository {
    Optional<LinkInfo> findByShortLink(String shortLink);

    LinkInfo saveShortLink(LinkInfo linkInfo);

    List<LinkInfo> getAll();

    void delete(UUID id);
}
