package ru.kerchik.linkShortener.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kerchik.linkShortener.dto.CreateShortLinkRequest;
import ru.kerchik.linkShortener.dto.CreateShortLinkResponse;
import ru.kerchik.linkShortener.dto.common.CommonRequest;
import ru.kerchik.linkShortener.dto.common.CommonResponse;
import ru.kerchik.linkShortener.service.LinkInfoService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/link-infos")
public class AdminController {

    private final LinkInfoService linkInfoService;

    @PostMapping
    public CommonResponse<CreateShortLinkResponse> postCreateShortLink(@RequestBody CommonRequest<CreateShortLinkRequest> request) {
        log.info("Поступил запрос на создание короткой ссылки: {}", request);

        CreateShortLinkResponse createShortLinkResponse = linkInfoService.createLinkInfo(request.getBody());


        return CommonResponse.<CreateShortLinkResponse>builder()
                .body(createShortLinkResponse)
                .build();
    }
}
