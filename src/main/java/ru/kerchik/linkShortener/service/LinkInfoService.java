package ru.kerchik.linkShortener.service;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kerchik.linkShortener.beanpostprocessor.LogExecutionTime;
import ru.kerchik.linkShortener.dto.CreateShortLinkRequest;
import ru.kerchik.linkShortener.dto.CreateShortLinkResponse;
import ru.kerchik.linkShortener.exception.NotFoundException;
import ru.kerchik.linkShortener.model.LinkInfo;
import ru.kerchik.linkShortener.property.LinkShortenerProperty;
import ru.kerchik.linkShortener.repository.LinkInfoRepository;

@Service
public class LinkInfoService {

    @Autowired
    private LinkShortenerProperty linkShortenerProperty;

    @Autowired
    private LinkInfoRepository repository;

    public LinkInfoService() {
    }

    @LogExecutionTime
    public CreateShortLinkResponse createLinkInfo(CreateShortLinkRequest request) {
        LinkInfo linkInfo = createShortLinkRequestToLinkInfo(request);
        linkInfo.setShortLink(RandomStringUtils.randomAlphanumeric(linkShortenerProperty.getShortLinkLength()));

        repository.saveShortLink(linkInfo);

        return createShortLinkResponse(linkInfo);
    }

    @LogExecutionTime
    public LinkInfo getByShortLink(String shortLink) {
        return repository.findByShortLink(shortLink)
                .orElseThrow(()-> new NotFoundException("Default link by this short link not founded"));
    }

    private LinkInfo createShortLinkRequestToLinkInfo(CreateShortLinkRequest request) {
        LinkInfo linkInfo = new LinkInfo();
        linkInfo.setLink(request.getLink());
        linkInfo.setEndTime(request.getEndTime());
        linkInfo.setDescription(request.getDescription());
        linkInfo.setActive(request.getActive());
        return linkInfo;
    }

    private CreateShortLinkResponse createShortLinkResponse(LinkInfo linkInfo) {
        return CreateShortLinkResponse.builder()
                .id(linkInfo.getId())
                .link(linkInfo.getLink())
                .endTime(linkInfo.getEndTime())
                .description(linkInfo.getDescription())
                .active(linkInfo.getActive())
                .shortLink(linkInfo.getShortLink())
                .build();
    }

}
