package ru.kerchik.linkShortener.dto;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterLinkInfoRequest {
    private String linkPart;
    private ZonedDateTime endTimeFrom;
    private ZonedDateTime endTimeTo;
    private String descriptionPart;
    private Boolean active;

    private PageableRequest page;
}
