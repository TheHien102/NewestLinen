package com.example.newestlinen.mapper.cart;

import com.example.newestlinen.dto.cart.AddressManagementDTO;
import com.example.newestlinen.form.cart.AddNewAddressManageForm;
import com.example.newestlinen.storage.model.Address.AddressManagement;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressManagementMapper {
    @Mapping(source = "parent.id",target = "parentId")
    AddressManagementDTO fromAddressManagementDataToObject(AddressManagement content);

    @IterableMapping(elementTargetType = AddressManagementDTO.class)
    List<AddressManagementDTO> fromAddressManagementDataListToDtoList(List<AddressManagement> content);

    @IterableMapping(elementTargetType = AddressManagement.class)
    List<AddressManagement> fromAddressManagementAddFormListToDataList(List<AddNewAddressManageForm> content);
}
