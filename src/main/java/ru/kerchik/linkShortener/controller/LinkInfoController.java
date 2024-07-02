package ru.kerchik.linkShortener.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.kerchik.linkShortener.dto.CreateShortLinkRequest;
import ru.kerchik.linkShortener.dto.CreateShortLinkResponse;
import ru.kerchik.linkShortener.dto.common.CommonRequest;
import ru.kerchik.linkShortener.dto.common.CommonResponse;
import ru.kerchik.linkShortener.model.LinkInfo;
import ru.kerchik.linkShortener.service.LinkInfoService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/link-infos")
public class LinkInfoController {

    private final LinkInfoService linkInfoService;

    @PostMapping
    public CommonResponse<CreateShortLinkResponse> postCreateShortLink(@RequestBody @Valid CommonRequest<CreateShortLinkRequest> request) {
        log.info("Поступил запрос на создание короткой ссылки: {}", request);

        CreateShortLinkResponse createShortLinkResponse = linkInfoService.createLinkInfo(request.getBody());

        return CommonResponse.<CreateShortLinkResponse>builder()
                .body(createShortLinkResponse)
                .build();
    }

    @GetMapping("/all")
    public CommonResponse<List<LinkInfo>> getAllLinks() {
        log.info("Поступил запрос на получение всех имеющихся в памяти коротких ссылок");

        return CommonResponse.<List<LinkInfo>>builder()
                .body(linkInfoService.getAll())
                .build();

    }

    @DeleteMapping("/{shortLinkId}")
    public void deleteShortLink(@PathVariable UUID shortLinkId) {
        log.info("Поступил запрос на удаление короткой ссылки {}", shortLinkId);

        linkInfoService.delete(shortLinkId);
    }

}
