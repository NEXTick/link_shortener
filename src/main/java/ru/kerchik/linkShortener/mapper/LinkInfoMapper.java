package ru.kerchik.linkShortener.mapper;

import org.mapstruct.Mapper;
import ru.kerchik.linkShortener.dto.CreateShortLinkRequest;
import ru.kerchik.linkShortener.dto.CreateShortLinkResponse;
import ru.kerchik.linkShortener.model.LinkInfo;

@Mapper(componentModel = "spring")
public interface LinkInfoMapper {
    LinkInfo fromCreateRequest(CreateShortLinkRequest request);

    CreateShortLinkResponse toResponse(LinkInfo linkInfo);
}
