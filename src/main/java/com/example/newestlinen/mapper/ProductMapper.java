package com.example.newestlinen.mapper;

import com.example.newestlinen.dto.account.AccountAdminDto;
import com.example.newestlinen.dto.product.ProductDTO;
import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.ProductModel.Product;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    ProductDTO fromProductDataToObject(Product p);

    @IterableMapping(elementTargetType = ProductDTO.class)
    List<ProductDTO>fromProductDataListToDtoList(List<Product> content);
}
