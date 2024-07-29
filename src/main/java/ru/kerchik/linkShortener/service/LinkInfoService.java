package ru.kerchik.linkShortener.service;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.kerchik.linkShortener.beanpostprocessor.LogExecutionTime;
import ru.kerchik.linkShortener.dto.PageableRequest;
import ru.kerchik.linkShortener.dto.ShortLinkRequest;
import ru.kerchik.linkShortener.dto.FilterLinkInfoRequest;
import ru.kerchik.linkShortener.dto.LinkInfoResponse;
import ru.kerchik.linkShortener.exception.NotFoundException;
import ru.kerchik.linkShortener.exception.NotFoundPageException;
import ru.kerchik.linkShortener.mapper.LinkInfoMapper;
import ru.kerchik.linkShortener.model.LinkInfo;
import ru.kerchik.linkShortener.property.LinkShortenerProperty;
import ru.kerchik.linkShortener.repository.LinkInfoRepository;

import java.time.ZonedDateTime;
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
        LinkInfo linkInfo = repository.findByShortLinkAndActiveTrueAndEndTimeIsAfter(shortLink, ZonedDateTime.now())
                .orElseThrow(() -> new NotFoundPageException("Default link by this short link not founded"));

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

        Pageable pageable = createPage(filterRequest);

        

        return repository.findByFilter(filterRequest.getLinkPart(),
                        filterRequest.getEndTimeFrom(),
                        filterRequest.getEndTimeTo(),
                        filterRequest.getDescriptionPart(),
                        filterRequest.getActive(),
                        pageable).stream()
                .map(linkInfoMapper::toResponse)
                .toList();
    }

    private static Pageable createPage(FilterLinkInfoRequest filterRequest) {
        PageableRequest page = filterRequest.getPage();

        List<Sort.Order> orders = page.getSorts().stream()
                .map(sort -> new Sort.Order(Sort.Direction.fromString(sort.getDirection()), sort.getField()))
                .toList();

        Sort sort = CollectionUtils.isEmpty(orders)
                ? Sort.unsorted()
                : Sort.by(orders);

        return PageRequest.of(page.getNumber()-1, page.getSize(), sort);
    }

    private record Result(PageableRequest page, Sort sort) {
    }

    public LinkInfoResponse update(ShortLinkRequest shortLinkRequest, String shortLink) {
        Optional<LinkInfo> foundedLinkInfo = repository.findByShortLinkAndActiveTrueAndEndTimeIsAfter(shortLink, ZonedDateTime.now());

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
