package ru.kerchik.linkShortener.service;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.kerchik.linkShortener.beanpostprocessor.LogExecutionTime;
import ru.kerchik.linkShortener.dto.ShortLinkRequest;
import ru.kerchik.linkShortener.dto.FilterLinkInfoRequest;
import ru.kerchik.linkShortener.dto.LinkInfoResponse;
import ru.kerchik.linkShortener.exception.NotFoundException;
import ru.kerchik.linkShortener.mapper.LinkInfoMapper;
import ru.kerchik.linkShortener.model.LinkInfo;
import ru.kerchik.linkShortener.property.LinkShortenerProperty;
import ru.kerchik.linkShortener.repository.LinkInfoRepository;

import java.util.List;
import java.util.Optional;
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
    public LinkInfoResponse createLinkInfo(ShortLinkRequest request) {
        LinkInfo linkInfo = linkInfoMapper.fromCreateRequest(request);
        linkInfo.setShortLink(RandomStringUtils.randomAlphanumeric(linkShortenerProperty.getShortLinkLength()));

        repository.save(linkInfo);

        return linkInfoMapper.toResponse(linkInfo);
    }

    @LogExecutionTime
    public LinkInfo getByShortLink(String shortLink) {
        LinkInfo linkInfo = repository.findByShortLinkAndActiveTrue(shortLink)
                .orElseThrow(() -> new NotFoundException("Default link by this short link not founded"));

        repository.incrementOpeningCountByShortLink(shortLink);

        return linkInfo;
    }

    public List<LinkInfo> getAll() {

        return repository.findAll();
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public List<LinkInfoResponse> findByFilter(FilterLinkInfoRequest filterRequest) {

        return repository.findByFilter(filterRequest.getLinkPart(),
                        filterRequest.getEndTimeFrom(),
                        filterRequest.getEndTimeTo(),
                        filterRequest.getDescriptionPart(),
                        filterRequest.getActive()).stream()
                .map(linkInfoMapper::toResponse)
                .toList();
    }

    public LinkInfoResponse update(ShortLinkRequest shortLinkRequest, String shortLink) {
        Optional<LinkInfo> foundedLinkInfo = repository.findByShortLinkAndActiveTrue(shortLink);

        if (foundedLinkInfo.isPresent()) {

            LinkInfo linkInfoToUpdate = new LinkInfo(foundedLinkInfo.get().getId(),
                    shortLinkRequest.getLink(),
                    shortLinkRequest.getEndTime(),
                    shortLinkRequest.getDescription(),
                    shortLinkRequest.getActive(),
                    shortLink,
                    foundedLinkInfo.get().getOpeningCount());

            return linkInfoMapper.toResponse(repository.save(linkInfoToUpdate));
        }

        throw new NotFoundException("Default link by this short link not founded");
    }
}
