package com.example.newestlinen.mapper.order;

import com.example.newestlinen.dto.order.AddressDTO;
import com.example.newestlinen.storage.model.Address.Address;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {
    AddressDTO fromAddressToOrderDetail(Address content);

    @IterableMapping(elementTargetType = AddressDTO.class)
    List<AddressDTO> fromAddressListToDataList(List<Address> content);
}
