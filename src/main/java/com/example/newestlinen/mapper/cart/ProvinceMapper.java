package com.example.newestlinen.mapper.cart;

import com.example.newestlinen.dto.cart.ProvinceManagementDTO;
import com.example.newestlinen.form.cart.AddProvinceForm;
import com.example.newestlinen.storage.model.Address.ProvinceManagement;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProvinceMapper {
    @Mapping(source = "parent.id",target = "parentId")
    ProvinceManagementDTO fromProvinceManagementDataToObject(ProvinceManagement content);

    @IterableMapping(elementTargetType = ProvinceManagementDTO.class)
    List<ProvinceManagementDTO> fromProvinceManagementDataListToDtoList(List<ProvinceManagement> content);

    @IterableMapping(elementTargetType = ProvinceManagement.class)
    List<ProvinceManagement> fromProvinceManagementAddFormListToDataList(List<AddProvinceForm> content);
}
