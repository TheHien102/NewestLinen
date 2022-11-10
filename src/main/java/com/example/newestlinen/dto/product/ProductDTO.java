package com.example.newestlinen.dto.product;

import com.example.newestlinen.dto.ABasicAdminDto;
import com.example.newestlinen.storage.model.ProductModel.Asset;
import com.example.newestlinen.storage.model.Category;
import com.example.newestlinen.storage.model.ProductModel.Item;
import com.example.newestlinen.storage.model.ProductModel.Review;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDTO extends ABasicAdminDto {
    private Long id;

    private String name;

    private int discount;

    private String description;

    private int price;

    private Category productCategory;

    private List<Asset> assetList;
}
