package com.am.challenge.mapper;

import com.am.challenge.domain.PastProject;
import com.am.challenge.dto.PastProjectDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PastProjectMapperMapper {

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "application", ignore = true)
    PastProject toEntity(PastProjectDto dto);

    PastProjectDto toDto(PastProject pastProject);
}
