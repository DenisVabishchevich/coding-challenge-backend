package com.am.challenge.mapper;

import com.am.challenge.domain.Application;
import com.am.challenge.dto.ApplicationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = PastProjectMapperMapper.class)
public interface ApplicationMapper {

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    Application toEntity(ApplicationDto dto);

    @Mapping(target = "email", source = "email")
    ApplicationDto toDto(Application application);

    List<ApplicationDto> toDto(Iterable<Application> applications);
}
