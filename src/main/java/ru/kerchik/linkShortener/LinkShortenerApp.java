package ru.kerchik.linkShortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kerchik.linkShortener.dto.CreateShortLinkRequest;
import ru.kerchik.linkShortener.repository.impl.LinkInfoRepositoryImpl;
import ru.kerchik.linkShortener.service.LogExecutionTimeLinkInfoServiceProxy;
import ru.kerchik.linkShortener.service.impl.LinkInfoServiceImpl;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;


@SpringBootApplication
public class LinkShortenerApp {

    @PostConstruct
    public void pc() {
        CreateShortLinkRequest createShortLinkRequest = new CreateShortLinkRequest(
                "zaza.zoom",
                ZonedDateTime.now(),
                "Some description",
                true
        );
        LogExecutionTimeLinkInfoServiceProxy proxy =
                new LogExecutionTimeLinkInfoServiceProxy(new LinkInfoServiceImpl(new LinkInfoRepositoryImpl()));
        System.out.println(proxy.getByShortLink("abc"));
        System.out.println(proxy.createLinkInfo(createShortLinkRequest));
    }

    public static void main(String[] args) {
        SpringApplication.run(LinkShortenerApp.class);
    }
}
