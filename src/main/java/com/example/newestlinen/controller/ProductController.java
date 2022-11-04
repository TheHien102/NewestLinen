package com.example.newestlinen.controller;

import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.dto.ResponseListObj;
import com.example.newestlinen.dto.product.ProductDTO;
import com.example.newestlinen.form.product.UpdateProductForm;
import com.example.newestlinen.form.product.UploadProductForm;
import com.example.newestlinen.mapper.ProductMapper;
import com.example.newestlinen.service.UploadService;
import com.example.newestlinen.storage.model.ProductModel.Asset;
import com.example.newestlinen.storage.model.ProductModel.Item;
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import com.example.newestlinen.utils.projection.repository.CategoryRepository;
import com.example.newestlinen.utils.projection.repository.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/v1/product")
public class ProductController extends ABasicController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    UploadService uploadService;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/get/page={page}")
    public ApiMessageDto<ResponseListObj<ProductDTO>> getProductByPage(@PathVariable String page) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page) - 1, 12);

        Page<Product> productPage = productRepository.getAllByOrderById(pageable);

        ApiMessageDto<ResponseListObj<ProductDTO>> apiMessageDto = new ApiMessageDto<>();

        ResponseListObj<ProductDTO> responseListObj = new ResponseListObj<>();

        responseListObj.setData(productMapper.fromProductDataListToDtoList(productPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(productPage.getTotalPages());
        responseListObj.setTotalElements(productPage.getTotalElements());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List product success");
        return apiMessageDto;
    }

    @GetMapping("/get/Id={Id}")
    public ApiMessageDto<ProductDTO> getProductById(@PathVariable String Id) {
        Product p = productRepository.getById(Long.parseLong(Id));
        ApiMessageDto<ProductDTO> apiMessageDto = new ApiMessageDto<>();

        apiMessageDto.setData(productMapper.fromProductDataToObject(p));
        apiMessageDto.setMessage("List product success");
        return apiMessageDto;
    }

    @PostMapping("/upload")
    public ApiMessageDto<String> uploadProduct(@RequestBody UploadProductForm uploadProductForm) {
        if (!isAdmin()) {
            System.out.println("not admin");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

        // declare new Product
        Product p = new Product();
        p.setName(uploadProductForm.getName());
        p.setDiscount(uploadProductForm.getDiscount());
        p.setDescription(uploadProductForm.getDescription());
        p.setPrice(uploadProductForm.getPrice());
        p.setProductCategory(categoryRepository.getById(uploadProductForm.getProductCategoryID()));

        // declare new Item
        Item i = new Item();
        i.setName("defaultProduct");

        // declare variants
        List<Variant> variantList = productMapper.fromVariantFormListToDataList(uploadProductForm.getVariants());

        // upload and declare asset
        List<Asset> assetList =
                uploadProductForm.getAssets().stream().map(asset -> {
                            Asset a = new Asset();
                            try {
                                a.setLink(uploadService.uploadImg(asset.getData()));
                                a.setType(asset.getType());
                                a.setIsMain(asset.getIsMain());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return a;
                        }
                ).collect(Collectors.toList());

        // Map together
        p.setProductItem(List.of(i));
        assetList.forEach(a -> a.setAssetProduct(p));

        p.setAssets(assetList);
        variantList.forEach(v -> v.setVarriantItem(i));

        i.setVarriants(variantList);
        i.setItemProduct(p);

        productRepository.save(p);

        apiMessageDto.setData(null);
        apiMessageDto.setMessage("upload success");
        return apiMessageDto;
    }

    @PostMapping("/update")
    public ApiMessageDto<Product> updateProduct(@RequestBody UpdateProductForm updateProductForm) {
        System.out.println(updateProductForm);
        Product p = productRepository.getProductById(updateProductForm.getProductId());

        p.setName(updateProductForm.getName());
        p.setDiscount(updateProductForm.getDiscount());
        p.setDescription(updateProductForm.getDescription());
        p.setPrice(updateProductForm.getPrice());
        p.setProductCategory(categoryRepository.getById(updateProductForm.getProductCategoryID()));

        // Map together
        p.setAssets(productMapper.fromUpdateAssetListFormToData(updateProductForm.getAssets()));

        p.getProductItem().get(0).setVarriants(productMapper.fromUpdateVariantListFormToData(updateProductForm.getVariants()));

        productRepository.save(p);

        ApiMessageDto<Product> apiMessageDto = new ApiMessageDto<>();

        apiMessageDto.setData(p);
        apiMessageDto.setMessage("upload success");
        return apiMessageDto;
    }
}
