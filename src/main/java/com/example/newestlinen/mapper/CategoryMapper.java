package com.example.newestlinen.mapper;

import com.example.newestlinen.dto.category.CategoryDto;
import com.example.newestlinen.dto.product.CategorySelectDTO;
import com.example.newestlinen.form.category.CreateCategoryForm;
import com.example.newestlinen.form.category.UpdateCategoryForm;
import com.example.newestlinen.storage.model.Category;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(source = "categoryName", target = "name")
    @Mapping(source = "categoryDescription", target = "description")
    @Mapping(source = "categoryImage", target = "image")
    @Mapping(source = "categoryOrdering", target = "ordering")
    @Mapping(source = "categoryKind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminCreateMapping")
    Category fromCreateCategoryFormToEntity(CreateCategoryForm createCategoryForm);

    @Mapping(source = "categoryName", target = "name")
    @Mapping(source = "categoryDescription", target = "description")
    @Mapping(source = "categoryImage", target = "image")
    @Mapping(source = "categoryOrdering", target = "ordering")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminUpdateMapping")
    void fromUpdateCategoryFormToEntity(UpdateCategoryForm updateCategoryForm, @MappingTarget Category category);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "categoryName")
    @Mapping(source = "description", target = "categoryDescription")
    @Mapping(source = "image", target = "categoryImage")
    @Mapping(source = "ordering", target = "categoryOrdering")
    @Mapping(source = "kind", target = "categoryKind")
    @Mapping(source = "parentCategory.id", target = "parentId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedBy", target = "modifiedBy")
    @Mapping(source = "createdBy", target = "createdBy")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminGetMapping")
    CategoryDto fromEntityToAdminDto(Category category);

    @IterableMapping(elementTargetType = CategoryDto.class, qualifiedByName = "adminGetMapping")
    List<CategoryDto> fromEntityListToCategoryDtoList(List<Category> categories);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "categoryName")
    @Mapping(source = "image", target = "categoryImage")
    @BeanMapping(ignoreByDefault = true)
    @Named("adminAutoCompleteMapping")
    CategoryDto fromEntityToAdminDtoAutoComplete(Category category);

    @IterableMapping(elementTargetType = CategoryDto.class, qualifiedByName = "adminAutoCompleteMapping")
    List<CategoryDto> fromEntityListToCategoryDtoAutoComplete(List<Category> categories);

    @Mapping(source = "parentCategory.id",target = "parentId")
    CategorySelectDTO fromCategoryDataToSelectObject(Category category);

    @IterableMapping(elementTargetType = CategorySelectDTO.class)
    List<CategorySelectDTO> fromCategoryListDataToSelectObject(List<Category> categories);
}
