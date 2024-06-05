package ru.kerchik.linkShortener.repository;

import ru.kerchik.linkShortener.model.LinkInfo;

import java.util.Optional;

public interface LinkInfoRepository {
    Optional<LinkInfo> findByShortLink(String shortLink);

    LinkInfo saveShortLink(LinkInfo linkInfo);
}
