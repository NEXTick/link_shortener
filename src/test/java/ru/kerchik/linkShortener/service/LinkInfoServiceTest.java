package ru.kerchik.linkShortener.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.kerchik.linkShortener.dto.CreateShortLinkRequest;
import ru.kerchik.linkShortener.exception.NotFoundException;
import ru.kerchik.linkShortener.model.LinkInfo;

import static org.assertj.core.api.Assertions.assertThat;


class LinkInfoServiceTest {

    static LinkInfoService linkInfoService;
    static CreateShortLinkRequest dto;

    static LinkInfo linkInfo;

    @BeforeAll
    static void filling() {
        linkInfoService = new LinkInfoService();

        dto = new CreateShortLinkRequest();
        dto.setLink("https://pomofocus.io/app");
        dto.setActive(true);
        dto.setDescription("Time to focus!");


    }

    @Test
    void createLinkInfo() {


        assertThat(linkInfo).as("LinkInfo based on {} has been created successfully", dto)
                .satisfies(linkInfo -> {
                    assertThat(linkInfo.getId()).isNotNull();
                    assertThat(linkInfo.getLink()).isEqualTo(dto.getLink());
                    assertThat(linkInfo.getDescription()).isEqualTo(dto.getDescription());
                    assertThat(linkInfo.getActive()).isEqualTo(dto.getActive());
                    //assertThat(linkInfo.getShortLink().length()).isEqualTo(Constants.SHORT_LINK_LENGTH);
                });
    }

    @Test
    void getByShortLink() {
        assertThat(linkInfoService.getByShortLink(linkInfo.getShortLink())).isEqualTo(linkInfo);
        Assertions.assertThrows(NotFoundException.class, () -> linkInfoService.getByShortLink("qweqweqwe"));
    }
}