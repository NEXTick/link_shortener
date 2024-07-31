package ru.kerchik.linkShortener.mapper;

import org.mapstruct.Mapper;
import ru.kerchik.linkShortener.dto.LinkInfoResponse;
import ru.kerchik.linkShortener.dto.ShortLinkRequest;
import ru.kerchik.linkShortener.model.LinkInfo;

@Mapper(componentModel = "spring")
public interface LinkInfoMapper {
    LinkInfo fromCreateRequest(ShortLinkRequest request);

    LinkInfoResponse toResponse(LinkInfo linkInfo);
}
