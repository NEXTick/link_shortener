package ru.kerchik.linkShortener.dto;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateShortLinkRequest {
    private String link;
    private ZonedDateTime endTime;
    private String description;
    private Boolean active;

}
