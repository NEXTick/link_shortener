package ru.kerchik.linkShortener.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kerchik.linkShortener.dto.ShortLinkRequest;
import ru.kerchik.linkShortener.dto.FilterLinkInfoRequest;
import ru.kerchik.linkShortener.dto.LinkInfoResponse;
import ru.kerchik.linkShortener.dto.common.CommonRequest;
import ru.kerchik.linkShortener.dto.common.CommonResponse;
import ru.kerchik.linkShortener.model.LinkInfo;
import ru.kerchik.linkShortener.service.LinkInfoService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/link-infos")
public class LinkInfoController {

    private final LinkInfoService linkInfoService;

    @PostMapping
    public CommonResponse<LinkInfoResponse> postCreateShortLink(@RequestBody @Valid CommonRequest<ShortLinkRequest> request) {

        LinkInfoResponse linkInfoResponse = linkInfoService.createLinkInfo(request.getBody());

        return CommonResponse.<LinkInfoResponse>builder()
                .body(linkInfoResponse)
                .build();
    }

    @GetMapping("/all")
    public CommonResponse<List<LinkInfo>> getAllLinks() {

        return CommonResponse.<List<LinkInfo>>builder()
                .body(linkInfoService.getAll())
                .build();

    }

    @PatchMapping()
    public CommonResponse<LinkInfoResponse> updateByShortLink(@RequestBody @Valid CommonRequest<ShortLinkRequest> request) {

        return CommonResponse.<LinkInfoResponse>builder()
                .body(linkInfoService.update(request.getBody()))
                .build();
    }

    @DeleteMapping("/{shortLinkId}")
    public void deleteShortLink(@PathVariable UUID shortLinkId) {

        linkInfoService.delete(shortLinkId);
    }

    @PostMapping("/filter")
    public CommonResponse<List<LinkInfoResponse>> filter(@RequestBody @Valid CommonRequest<FilterLinkInfoRequest> request) {
        List<LinkInfoResponse> linkInfoResponses = linkInfoService.findByFilter(request.getBody());

        return CommonResponse.<List<LinkInfoResponse>>builder()
                .body(linkInfoResponses)
                .build();
    }

}
