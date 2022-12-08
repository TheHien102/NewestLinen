package com.example.newestlinen.mapper.cart;

import com.example.newestlinen.dto.cart.ProvinceManagementDTO;
import com.example.newestlinen.form.province.AddProvinceForm;
import com.example.newestlinen.storage.model.Address.Province;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProvinceMapper {
    @Mapping(source = "parent.id",target = "parentId")
    ProvinceManagementDTO fromProvinceManagementDataToObject(Province content);

    @IterableMapping(elementTargetType = ProvinceManagementDTO.class)
    List<ProvinceManagementDTO> fromProvinceManagementDataListToDtoList(List<Province> content);

    @IterableMapping(elementTargetType = Province.class)
    List<Province> fromProvinceManagementAddFormListToDataList(List<AddProvinceForm> content);
}
