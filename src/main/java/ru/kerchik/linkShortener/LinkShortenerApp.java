package ru.kerchik.linkShortener;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kerchik.linkShortener.service.LinkInfoService;


@SpringBootApplication
@AllArgsConstructor
public class LinkShortenerApp {

    @Autowired
    private final LinkInfoService linkInfoService;


//    @PostConstruct
//    public void pc() {
//
//        CreateShortLinkRequest lr = CreateShortLinkRequest.builder()
//                .link("https://ya.ru/")
//                .build();
//        System.out.println(linkInfoService.createLinkInfo(lr).getShortLink());
//        System.out.println(lr);
//
//    }

    public static void main(String[] args) {
        SpringApplication.run(LinkShortenerApp.class);
    }
}
