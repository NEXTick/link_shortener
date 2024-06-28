package ru.kerchik.linkShortener.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import ru.kerchik.linkShortener.dto.CreateShortLinkRequest;
import ru.kerchik.linkShortener.model.LinkInfo;
import ru.kerchik.linkShortener.service.impl.LinkInfoServiceImpl;

@Slf4j
@AllArgsConstructor
public class LogExecutionTimeLinkInfoServiceProxy implements LinkInfoService {

    private final LinkInfoServiceImpl linkInfoService;


    @Override
    public LinkInfo createLinkInfo(CreateShortLinkRequest request) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return linkInfoService.createLinkInfo(request);
        } finally {
            stopWatch.stop();
            log.info("Время выполнения метода getByShortLink: {} мс", stopWatch.getTotalTimeMillis());
        }
    }

    @Override
    public LinkInfo getByShortLink(String shortLink) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return linkInfoService.getByShortLink(shortLink);
        } finally {
            stopWatch.stop();
            log.info("Время выполнения метода getByShortLink: {} мс", stopWatch.getTotalTimeMillis());
        }

    }
}
