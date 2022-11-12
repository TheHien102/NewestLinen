package com.example.newestlinen.mapper;

import com.example.newestlinen.dto.product.ItemDTO;
import com.example.newestlinen.dto.product.ProductDTO;
import com.example.newestlinen.form.product.UpdateAssetForm;
import com.example.newestlinen.form.product.UpdateVariantForm;
import com.example.newestlinen.form.product.UploadAssetForm;
import com.example.newestlinen.form.product.UploadVariantForm;
import com.example.newestlinen.storage.model.ProductModel.Asset;
import com.example.newestlinen.storage.model.ProductModel.Item;
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(source = "variantId", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "property", target = "property")
    @Mapping(source = "addPrice", target = "addPrice")
    @Mapping(source = "status", target = "status")
    Variant fromVariantFormToData(UpdateVariantForm updateVariantForm);

    @IterableMapping(elementTargetType = Variant.class)
    List<Variant> fromUpdateVariantListFormToData(List<UpdateVariantForm> updateVariantFormList);

    @Mapping(source = "assetId", target = "id")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "link", target = "link")
    @Mapping(source = "status", target = "status")
    Asset fromAssetFormToData(UpdateAssetForm updateAssetForm);

    @IterableMapping(elementTargetType = Asset.class)
    List<Asset> fromUpdateAssetListFormToData(List<UpdateAssetForm> updateAssetFormList);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "mainImg", target = "mainImg")
    @Mapping(source = "discount", target = "discount")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "productCategory.name",target = "categoryName")
    ProductDTO fromProductDataToObject(Product p);

    @Mapping(source = "id", target = "itemId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "itemProduct", target = "itemProduct")
    @Mapping(source = "variants", target = "variantList")
    ItemDTO fromItemDataToObject(Item i);

    @IterableMapping(elementTargetType = Variant.class)
    List<Variant> fromVariantFormListToDataList(List<UploadVariantForm> uploadVariantForms);

    @IterableMapping(elementTargetType = Asset.class)
    List<Asset> fromAssetFormListToDataList(List<UploadAssetForm> uploadAssetForms);

    @IterableMapping(elementTargetType = ProductDTO.class)
    List<ProductDTO> fromProductDataListToDtoList(List<Product> content);
}
