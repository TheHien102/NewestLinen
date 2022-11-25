package com.example.newestlinen.mapper.product;

import com.example.newestlinen.dto.product.ItemDTO;
import com.example.newestlinen.dto.product.ProductAdminDTO;
import com.example.newestlinen.dto.product.ProductDetailDTO;
import com.example.newestlinen.dto.product.ProductUserDTO;
import com.example.newestlinen.form.product.UpdateVariantForm;
import com.example.newestlinen.form.product.UploadVariantForm;
import com.example.newestlinen.storage.model.ProductModel.Item;
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "mainImg", target = "mainImg")
    @Mapping(source = "discount", target = "discount")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "productCategory.name",target = "categoryName")
    ProductAdminDTO fromProductAdminDataToObject(Product p);

    @IterableMapping(elementTargetType = ProductAdminDTO.class)
    List<ProductAdminDTO> fromProductAdminDataListToDtoList(List<Product> content);

    @Mapping(source = "productCategory.description",target = "categoryDescription")
    ProductDetailDTO fromProductDetailDataToObject(Product p);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "itemProduct", target = "itemProduct")
    @Mapping(source = "variants", target = "variants")
    ItemDTO fromItemDataToObject(Item i);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "mainImg", target = "mainImg")
    @Mapping(source = "discount", target = "discount")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "productCategory.name",target = "categoryName")
    ProductUserDTO fromProductUserDataToObject(Product p);

    @IterableMapping(elementTargetType = ProductAdminDTO.class)
    List<ProductUserDTO> fromProductUserDataListToDtoList(List<Product> content);
}
