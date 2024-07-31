package ru.kerchik.linkShortener;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@AllArgsConstructor
public class LinkShortenerApp {

    public static void main(String[] args) {
        SpringApplication.run(LinkShortenerApp.class);
    }
}
