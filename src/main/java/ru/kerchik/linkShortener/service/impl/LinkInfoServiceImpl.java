package ru.kerchik.linkShortener.service.impl;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kerchik.linkShortener.dto.CreateShortLinkRequest;
import ru.kerchik.linkShortener.exception.NotFoundException;
import ru.kerchik.linkShortener.model.LinkInfo;
import ru.kerchik.linkShortener.repository.LinkInfoRepository;
import ru.kerchik.linkShortener.service.LinkInfoService;

@Service
@RequiredArgsConstructor
public class LinkInfoServiceImpl implements LinkInfoService {

    @Value("${link-shortener.short-link-length}")
    private int shortLinkLength;

    private final LinkInfoRepository repository;


    public LinkInfo createLinkInfo(CreateShortLinkRequest request) {
        LinkInfo linkInfo = createShortLinkRequestToLinkInfo(request);
        linkInfo.setShortLink(RandomStringUtils.randomAlphanumeric(shortLinkLength));
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
