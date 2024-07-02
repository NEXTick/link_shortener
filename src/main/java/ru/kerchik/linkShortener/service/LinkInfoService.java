package ru.kerchik.linkShortener.service;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kerchik.linkShortener.beanpostprocessor.LogExecutionTime;
import ru.kerchik.linkShortener.dto.CreateShortLinkRequest;
import ru.kerchik.linkShortener.dto.CreateShortLinkResponse;
import ru.kerchik.linkShortener.exception.NotFoundException;
import ru.kerchik.linkShortener.mapper.LinkInfoMapper;
import ru.kerchik.linkShortener.model.LinkInfo;
import ru.kerchik.linkShortener.property.LinkShortenerProperty;
import ru.kerchik.linkShortener.repository.LinkInfoRepository;

import java.util.List;
import java.util.UUID;

@Service
public class LinkInfoService {

    @Autowired
    private LinkShortenerProperty linkShortenerProperty;
    @Autowired
    private LinkInfoRepository repository;
    @Autowired
    private LinkInfoMapper linkInfoMapper;

    public LinkInfoService() {
    }

    @LogExecutionTime
    public CreateShortLinkResponse createLinkInfo(CreateShortLinkRequest request) {
        LinkInfo linkInfo = linkInfoMapper.fromCreateRequest(request);
        linkInfo.setShortLink(RandomStringUtils.randomAlphanumeric(linkShortenerProperty.getShortLinkLength()));

        repository.saveShortLink(linkInfo);

        return linkInfoMapper.toResponse(linkInfo);
    }

    @LogExecutionTime
    public LinkInfo getByShortLink(String shortLink) {
        return repository.findByShortLink(shortLink)
                .orElseThrow(() -> new NotFoundException("Default link by this short link not founded"));
    }

    public List<LinkInfo> getAll() {

        return repository.getAll();
    }

    public void delete(UUID id) {
        repository.delete(id);
    }

}
