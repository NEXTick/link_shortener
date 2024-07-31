package ru.kerchik.linkShortener.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

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
