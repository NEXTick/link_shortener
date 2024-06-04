package ru.kerchik.linkShortener.service;

import org.apache.commons.lang3.RandomStringUtils;
import ru.kerchik.linkShortener.dto.CreateShortLinkRequest;
import ru.kerchik.linkShortener.util.Constants;

public class LinkService {
    public String generateShortLink(CreateShortLinkRequest request) {
        return RandomStringUtils.randomAlphanumeric(Constants.SHORT_LINK_LENGTH);
    }
}
