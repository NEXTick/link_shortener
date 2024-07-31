package ru.kerchik.linkShortener;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kerchik.linkShortener.service.LinkInfoService;


@SpringBootApplication
@AllArgsConstructor
public class LinkShortenerApp {

    public static void main(String[] args) {
        SpringApplication.run(LinkShortenerApp.class);
    }
}
