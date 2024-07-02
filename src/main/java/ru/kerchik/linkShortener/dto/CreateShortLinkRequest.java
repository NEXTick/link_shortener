package ru.kerchik.linkShortener.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShortLinkRequest {

    @NotEmpty(message = "Ссылка не может быть пустой")
    @Size(min = 10, max = 4096, message = "Длина ссылки не может быть меньше 10")
    @Pattern(regexp = "https?://.+\\..*", message = "Ссылка не соответствует паттерну url")
    private String link;
    @Future(message = "Дата окончания действия ссылки должна быть в будущем")
    private ZonedDateTime endTime;
    @NotEmpty(message = "Описание не может быть пустым")
    private String description;
    @NotNull(message = "Признак активности не может быть пустым")
    private Boolean active;

}
