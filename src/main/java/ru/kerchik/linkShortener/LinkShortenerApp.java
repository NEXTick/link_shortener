package ru.kerchik.linkShortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kerchik.linkShortener.dto.CreateShortLinkRequest;
import ru.kerchik.linkShortener.service.LinkService;

@SpringBootApplication
public class LinkShortenerApp {
    public static void main(String[] args) {
        SpringApplication.run(LinkShortenerApp.class);
    }
}
