package com.example.newestlinen.mapper.cart;

import com.example.newestlinen.dto.cart.CartItemDTO;
import com.example.newestlinen.dto.cart.ProvinceManagementDTO;
import com.example.newestlinen.storage.model.Address.Province;
import com.example.newestlinen.storage.model.CartModel.CartItem;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper {
    CartItemDTO fromCartItemDataToObject(CartItem content);

    @IterableMapping(elementTargetType = CartItemDTO.class)
    List<CartItemDTO> fromCartItemDataListToDtoList(List<CartItem> content);
}
