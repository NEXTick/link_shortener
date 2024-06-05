package ru.kerchik.linkShortener.service;

import org.apache.commons.lang3.RandomStringUtils;
import ru.kerchik.linkShortener.dto.CreateShortLinkRequest;
import ru.kerchik.linkShortener.exception.NotFoundException;
import ru.kerchik.linkShortener.model.LinkInfo;
import ru.kerchik.linkShortener.repository.LinkInfoRepository;
import ru.kerchik.linkShortener.repository.impl.LinkInfoRepositoryImpl;
import ru.kerchik.linkShortener.util.Constants;

public class LinkInfoService {

    private final LinkInfoRepository repository = new LinkInfoRepositoryImpl();
    public LinkInfo createLinkInfo(CreateShortLinkRequest request) {
        LinkInfo linkInfo = createShortLinkRequestToLinkInfo(request);
        linkInfo.setShortLink(RandomStringUtils.randomAlphanumeric(Constants.SHORT_LINK_LENGTH));
        return repository.saveShortLink(linkInfo);
    }

    public LinkInfo getByShortLink(String shortLink) {
        return repository.findByShortLink(shortLink).orElseThrow(
                ()-> new NotFoundException("Default link by this short link not founded"));
    }

    private LinkInfo createShortLinkRequestToLinkInfo(CreateShortLinkRequest request) {
        LinkInfo linkInfo = new LinkInfo();
        linkInfo.setLink(request.getLink());
        linkInfo.setEndTime(request.getEndTime());
        linkInfo.setDescription(request.getDescription());
        linkInfo.setActive(request.getActive());
        return linkInfo;
    }

}