package com.example.newestlinen.mapper.order;

import com.example.newestlinen.dto.order.OrderDTO;
import com.example.newestlinen.dto.order.OrderDetailDTO;
import com.example.newestlinen.storage.model.CartModel.CartItem;
import com.example.newestlinen.storage.model.OrderModel.Order;
import com.example.newestlinen.storage.model.OrderModel.OrderDetail;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    OrderDetail fromCartItemToOrderDetail(CartItem cartItem);

    @IterableMapping(elementTargetType = OrderDetail.class)
    List<OrderDetail> fromCartItemListToOrderDetailList(List<CartItem> content);

    @Mapping(source = "item.itemProduct.id", target = "productId")
    @Mapping(source = "item.variants", target = "variants")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "discount", target = "discount")
    @Mapping(source = "item.name", target = "name")
    @Mapping(source = "item.itemProduct.mainImg", target = "mainImg")
    @Mapping(source = "order.createdDate", target = "createdDate")
    @Mapping(source = "order.modifiedDate", target = "modifiedDate")
    OrderDetailDTO fromOrderDetailDataToObject(OrderDetail content);

    @IterableMapping(elementTargetType = OrderDetailDTO.class)
    List<OrderDetailDTO> fromOrderDetailDataListToObjectList(List<OrderDetail> content);

    OrderDTO fromOrderDataToObject(Order content);

    @IterableMapping(elementTargetType = OrderDetailDTO.class)
    List<OrderDTO> fromOrderDataListToObjectList(List<Order> content);
}
