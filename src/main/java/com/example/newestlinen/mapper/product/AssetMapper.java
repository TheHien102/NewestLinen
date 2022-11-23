package com.example.newestlinen.mapper.product;

import com.example.newestlinen.dto.product.AssetDTO;
import com.example.newestlinen.form.product.UpdateAssetForm;
import com.example.newestlinen.form.product.UploadAssetForm;
import com.example.newestlinen.storage.model.ProductModel.Asset;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AssetMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "link", target = "link")
    @Mapping(source = "status", target = "status")
    Asset fromAssetFormToData(UpdateAssetForm updateAssetForm);

    @IterableMapping(elementTargetType = Asset.class)
    List<Asset> fromUpdateAssetListFormToData(List<UpdateAssetForm> updateAssetFormList);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "link", target = "link")
    AssetDTO fromAssetDataToObject(Asset a);
}
