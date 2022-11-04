package com.example.newestlinen.mapper;

import com.example.newestlinen.dto.product.ProductDTO;
import com.example.newestlinen.form.product.UploadAssetForm;
import com.example.newestlinen.form.product.UploadVariantForm;
import com.example.newestlinen.storage.model.ProductModel.Asset;
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    ProductDTO fromProductDataToObject(Product p);

    @IterableMapping(elementTargetType = Variant.class)
    List<Variant> fromVariantFormListToDataList(List<UploadVariantForm> uploadVariantForms);

    @IterableMapping(elementTargetType = Asset.class)
    List<Asset> fromAssetFormListtoDataList(List<UploadAssetForm> uploadAssetForms);

    @IterableMapping(elementTargetType = ProductDTO.class)
    List<ProductDTO>fromProductDataListToDtoList(List<Product> content);
}
