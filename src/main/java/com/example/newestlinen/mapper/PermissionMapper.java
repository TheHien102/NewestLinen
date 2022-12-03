package com.example.newestlinen.mapper;

import com.example.newestlinen.dto.permission.PermissionAdminDto;
import com.example.newestlinen.dto.permission.PermissionDto;
import com.example.newestlinen.storage.model.Permission;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "showMenu", target = "showMenu")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "nameGroup", target = "nameGroup")
    @Mapping(source = "status", target = "status")
    PermissionDto fromEntityToDto(Permission permission);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "showMenu", target = "showMenu")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "nameGroup", target = "nameGroup")
    PermissionAdminDto fromEntityToAdminDto(Permission permission);

    @IterableMapping(elementTargetType = PermissionAdminDto.class)
    List<PermissionAdminDto> fromEntityListToAdminDtoList(List<Permission> content);

    @IterableMapping(elementTargetType = PermissionDto.class)
    List<PermissionDto> fromEntityToDtoList(List<Permission> list);

    @IterableMapping(elementTargetType = PermissionDto.class)
    List<PermissionDto> fromEntityListToDtoList(List<Permission> content);

    @IterableMapping(elementTargetType = Permission.class)
    List<Permission> fromUpdateFormListToEntityList(List<PermissionDto> content);
}
