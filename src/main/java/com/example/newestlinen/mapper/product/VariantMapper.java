package com.example.newestlinen.mapper.product;

import com.example.newestlinen.form.product.UpdateVariantForm;
import com.example.newestlinen.form.product.UploadVariantForm;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VariantMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "property", target = "property")
    @Mapping(source = "addPrice", target = "addPrice")
    @Mapping(source = "status", target = "status")
    Variant fromVariantFormToData(UpdateVariantForm updateVariantForm);

    @IterableMapping(elementTargetType = Variant.class)
    List<Variant> fromUpdateVariantListFormToData(List<UpdateVariantForm> updateVariantFormList);

    @IterableMapping(elementTargetType = Variant.class)
    List<Variant> fromVariantFormListToDataList(List<UploadVariantForm> uploadVariantForms);
}
