package ru.kerchik.linkShortener;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kerchik.linkShortener.dto.CreateShortLinkRequest;
import ru.kerchik.linkShortener.property.LinkShortenerProperty;
import ru.kerchik.linkShortener.service.LinkInfoService;

import javax.annotation.PostConstruct;


@SpringBootApplication
@AllArgsConstructor
public class LinkShortenerApp {

    @Autowired
    private final LinkShortenerProperty property;

    @Autowired
    private final LinkInfoService linkInfoService;


    @PostConstruct
    public void pc() {

        System.out.println(property.getSystemId());
        CreateShortLinkRequest lr = CreateShortLinkRequest.builder()
                        .link("https://ya.ru/")
                                .build();
        System.out.println(linkInfoService.createLinkInfo(lr).getShortLink());

    }

    public static void main(String[] args) {
        SpringApplication.run(LinkShortenerApp.class);
    }
}
