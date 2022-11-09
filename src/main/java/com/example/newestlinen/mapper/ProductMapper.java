package com.example.newestlinen.mapper;

import com.example.newestlinen.dto.product.ProductDTO;
import com.example.newestlinen.form.product.UpdateAssetForm;
import com.example.newestlinen.form.product.UpdateVariantForm;
import com.example.newestlinen.form.product.UploadAssetForm;
import com.example.newestlinen.form.product.UploadVariantForm;
import com.example.newestlinen.storage.model.ProductModel.Asset;
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @IterableMapping(elementTargetType = Variant.class)
    List<Variant> fromUpdateVariantListFormToData(List<UpdateVariantForm> updateVariantForm);

    @IterableMapping(elementTargetType = Asset.class)
    List<Asset> fromUpdateAssetListFormToData(List<UpdateAssetForm> updateVariantForm);

    @Mapping(source = "id",target = "id")

    ProductDTO fromProductDataToObject(Product p);

    @IterableMapping(elementTargetType = Variant.class)
    List<Variant> fromVariantFormListToDataList(List<UploadVariantForm> uploadVariantForms);

    @IterableMapping(elementTargetType = Asset.class)
    List<Asset> fromAssetFormListToDataList(List<UploadAssetForm> uploadAssetForms);

    @IterableMapping(elementTargetType = ProductDTO.class)
    List<ProductDTO> fromProductDataListToDtoList(List<Product> content);
}
