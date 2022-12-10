package com.example.newestlinen.mapper.cart;

import com.example.newestlinen.dto.cart.CartItemDTO;
import com.example.newestlinen.dto.product.ItemDTO;
import com.example.newestlinen.storage.model.CartModel.CartItem;
import com.example.newestlinen.storage.model.CartModel.Item;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper {

    CartItemDTO fromCartItemDataToObject(CartItem content);

    @Mapping(source = "itemProduct.mainImg",target = "proImg")
    ItemDTO fromItemDataToObject(Item content);

    @IterableMapping(elementTargetType = CartItemDTO.class)
    List<CartItemDTO> fromCartItemDataListToDtoList(List<CartItem> content);
}
