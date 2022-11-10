package com.example.newestlinen.controller;

import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.dto.ErrorCode;
import com.example.newestlinen.dto.ResponseListObj;
import com.example.newestlinen.dto.product.ProductDTO;
import com.example.newestlinen.exception.RequestException;
import com.example.newestlinen.form.product.UpdateAssetForm;
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
import com.example.newestlinen.utils.projection.repository.Product.VariantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ProductController extends ABasicController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    UploadService uploadService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VariantRepository variantRepository;

    @GetMapping("/list")
    public ApiMessageDto<ResponseListObj<ProductDTO>> getProductByPage(Pageable pageable) {
        if (!isAdmin()) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allow to get");
        }
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

    @GetMapping("/get/{Id}")
    public ApiMessageDto<ProductDTO> getProductById(@PathVariable String Id) {
        Product p = productRepository.findProductById(Long.parseLong(Id));
        ApiMessageDto<ProductDTO> apiMessageDto = new ApiMessageDto<>();

        apiMessageDto.setData(productMapper.fromProductDataToObject(p));
        apiMessageDto.setMessage("List product success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> uploadProduct(@RequestBody UploadProductForm uploadProductForm) {
        if (!isAdmin()) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allow to upload");
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
        variantList.forEach(v -> v.setVariantItem(List.of(i)));

        i.setVariants(variantList);
        i.setItemProduct(p);

        productRepository.save(p);

        apiMessageDto.setData(null);
        apiMessageDto.setMessage("upload success");
        return apiMessageDto;
    }

    @PostMapping("/update")
    public ApiMessageDto<Product> updateProduct(@Valid @RequestBody UpdateProductForm updateProductForm) {
        if (!isAdmin()) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allow to update");
        }

        ApiMessageDto<Product> apiMessageDto = new ApiMessageDto<>();

        Product p = productRepository.findProductById(updateProductForm.getProductId());
        p.setName(updateProductForm.getName());
        p.setDiscount(updateProductForm.getDiscount());
        p.setDescription(updateProductForm.getDescription());
        p.setPrice(updateProductForm.getPrice());
        p.setProductCategory(categoryRepository.getById(updateProductForm.getProductCategoryID()));

        variantRepository.saveAll(productMapper.fromUpdateVariantListFormToData(updateProductForm.getVariants()));

        List<Asset> updateAssets = productMapper.fromUpdateAssetListFormToData(updateProductForm.getAssets());

        updateAssets.forEach(a -> a.setAssetProduct(p));

        p.setAssets(updateAssets);

        productRepository.save(p);

        apiMessageDto.setData(p);
        apiMessageDto.setMessage("update success");
        return apiMessageDto;
    }
}
