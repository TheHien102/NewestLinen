package com.example.newestlinen.mapper.order;

import com.example.newestlinen.dto.order.OrderDetailDTO;
import com.example.newestlinen.storage.model.CartModel.CartItem;
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

    OrderDetailDTO fromOrderDetailDataToObject(OrderDetail content);

    @IterableMapping(elementTargetType = OrderDetailDTO.class)
    List<OrderDetailDTO> fromOrderDetailDataListToObjectList(List<OrderDetail> content);
}
