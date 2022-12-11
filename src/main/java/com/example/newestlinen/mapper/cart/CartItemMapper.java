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

    @Mapping(source = "item.itemProduct.id",target = "productId")
    @Mapping(source = "item.variants",target = "variants")
    @Mapping(source = "quantity",target = "quantity")
    @Mapping(source = "price",target = "price")
    @Mapping(source = "discount",target = "discount")
    @Mapping(source = "item.name",target = "name")
    @Mapping(source = "item.itemProduct.mainImg",target = "mainImg")
    CartItemDTO fromCartItemDataToObject(CartItem content);

    @Mapping(source = "itemProduct.mainImg",target = "mainImg")
    ItemDTO fromItemDataToObject(Item content);

    @IterableMapping(elementTargetType = CartItemDTO.class)
    List<CartItemDTO> fromCartItemDataListToDtoList(List<CartItem> content);
}
