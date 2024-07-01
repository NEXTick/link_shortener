package ru.kerchik.linkShortener.dto;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShortLinkResponse {
    private UUID id;
    private String link;
    private ZonedDateTime endTime;
    private String description;
    private Boolean active;
    private String shortLink;

}
