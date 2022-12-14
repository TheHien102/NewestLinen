package com.example.newestlinen.mapper.product;

import com.example.newestlinen.dto.product.VariantDTO;
import com.example.newestlinen.form.product.UpdateVariantForm;
import com.example.newestlinen.form.product.UploadVariantForm;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VariantMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "property", target = "property")
    @Mapping(source = "addPrice", target = "addPrice")
    Variant fromVariantUploadFormListToDataList(UpdateVariantForm content);

    @IterableMapping(elementTargetType = Variant.class)
    List<Variant> fromUpdateVariantListFormToData(List<UpdateVariantForm> content);

    @IterableMapping(elementTargetType = Variant.class)
    List<Variant> fromVariantUploadFormListToDataList(List<UploadVariantForm> content);

    @IterableMapping(elementTargetType = Variant.class)
    List<Variant> fromVariantDTOListToDataList(List<VariantDTO> content);
}
