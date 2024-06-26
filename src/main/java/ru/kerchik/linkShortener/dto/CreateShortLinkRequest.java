package ru.kerchik.linkShortener.dto;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShortLinkRequest {
    private String link;
    private ZonedDateTime endTime;
    private String description;
    private Boolean active;

}
