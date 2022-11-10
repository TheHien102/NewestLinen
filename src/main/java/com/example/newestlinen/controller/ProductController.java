package com.example.newestlinen.controller;

import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.dto.ErrorCode;
import com.example.newestlinen.dto.ResponseListObj;
import com.example.newestlinen.dto.product.ItemDTO;
import com.example.newestlinen.dto.product.ProductDTO;
import com.example.newestlinen.exception.RequestException;
import com.example.newestlinen.form.product.UpdateProductForm;
import com.example.newestlinen.form.product.UploadProductForm;
import com.example.newestlinen.mapper.ProductMapper;
import com.example.newestlinen.service.UploadService;
import com.example.newestlinen.storage.model.ProductModel.Asset;
import com.example.newestlinen.storage.model.ProductModel.Item;
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import com.example.newestlinen.utils.projection.repository.CategoryRepository;
import com.example.newestlinen.utils.projection.repository.Product.ItemRepository;
import com.example.newestlinen.utils.projection.repository.Product.ProductRepository;
import com.example.newestlinen.utils.projection.repository.Product.VariantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class ProductController extends ABasicController {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final UploadService uploadService;

    private final CategoryRepository categoryRepository;

    private final VariantRepository variantRepository;

    private final ItemRepository itemRepository;

    @GetMapping("/list")
    public ApiMessageDto<ResponseListObj<ProductDTO>> getProductByPage(Pageable pageable) {
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
    public ApiMessageDto<ItemDTO> getProductById(@PathVariable("Id") Long Id) {
        Item i = itemRepository.findByItemProductId(Id);
        ApiMessageDto<ItemDTO> apiMessageDto = new ApiMessageDto<>();

        ItemDTO data = productMapper.fromItemDataToObject(i);

        apiMessageDto.setData(data);
        apiMessageDto.setMessage("get product success");
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
    public ApiMessageDto<String> updateProduct(@Valid @RequestBody UpdateProductForm updateProductForm) {
        if (!isAdmin()) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allow to update");
        }

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

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

        apiMessageDto.setMessage("update success");
        return apiMessageDto;
    }
}
