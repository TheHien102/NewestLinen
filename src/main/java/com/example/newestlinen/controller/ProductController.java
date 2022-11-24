package com.example.newestlinen.controller;

import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.dto.ErrorCode;
import com.example.newestlinen.dto.ResponseListObj;
import com.example.newestlinen.dto.product.ItemDTO;
import com.example.newestlinen.dto.product.ProductAdminDTO;
import com.example.newestlinen.dto.product.ProductUserDTO;
import com.example.newestlinen.exception.RequestException;
import com.example.newestlinen.form.UpdateStateForm;
import com.example.newestlinen.form.product.UpdateProductForm;
import com.example.newestlinen.form.product.UploadProductForm;
import com.example.newestlinen.mapper.product.AssetMapper;
import com.example.newestlinen.mapper.product.ProductMapper;
import com.example.newestlinen.mapper.product.VariantMapper;
import com.example.newestlinen.service.UploadService;
import com.example.newestlinen.storage.criteria.ProductCriteria;
import com.example.newestlinen.storage.model.ProductModel.Asset;
import com.example.newestlinen.storage.model.ProductModel.Item;
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import com.example.newestlinen.utils.projection.repository.CategoryRepository;
import com.example.newestlinen.utils.projection.repository.Product.AssetRepository;
import com.example.newestlinen.utils.projection.repository.Product.ItemRepository;
import com.example.newestlinen.utils.projection.repository.Product.ProductRepository;
import com.example.newestlinen.utils.projection.repository.Product.VariantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    private final AssetMapper assetMapper;

    private final VariantMapper variantMapper;

    private final UploadService uploadService;

    private final CategoryRepository categoryRepository;

    private final VariantRepository variantRepository;

    private final AssetRepository assetRepository;

    private final ItemRepository itemRepository;

    @GetMapping("/list_product_for_admin")
    public ApiMessageDto<ResponseListObj<ProductAdminDTO>> getProductByPageAdmin(ProductCriteria productCriteria, Pageable pageable) {
        if (!isAdmin()) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allow to list");
        }

        Page<Product> productPage = productRepository.findAll(productCriteria.getSpecification(), pageable);

        ApiMessageDto<ResponseListObj<ProductAdminDTO>> apiMessageDto = new ApiMessageDto<>();

        ResponseListObj<ProductAdminDTO> responseListObj = new ResponseListObj<>();

        responseListObj.setData(productMapper.fromProductAdminDataListToDtoList(productPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(productPage.getTotalPages());
        responseListObj.setTotalElements(productPage.getTotalElements());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List product success");
        return apiMessageDto;
    }

    @GetMapping("/list_product_for_user")
    public ApiMessageDto<ResponseListObj<ProductUserDTO>> getProductByPageUser(ProductCriteria productCriteria, Pageable pageable) {

        Page<Product> productPage = productRepository.findAll(productCriteria.getSpecification(), pageable);

        ApiMessageDto<ResponseListObj<ProductUserDTO>> apiMessageDto = new ApiMessageDto<>();

        ResponseListObj<ProductUserDTO> responseListObj = new ResponseListObj<>();

        responseListObj.setData(productMapper.fromProductUserDataListToDtoList(productPage.getContent()));
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
        if (i == null) {
            return new ApiMessageDto<>("Product not Found", HttpStatus.NOT_FOUND);
        }
        ApiMessageDto<ItemDTO> apiMessageDto = new ApiMessageDto<>();

        ItemDTO data = productMapper.fromItemDataToObject(i);

        apiMessageDto.setData(data);
        apiMessageDto.setMessage("get product success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> uploadProduct(@Valid @RequestBody UploadProductForm uploadProductForm) throws IOException {
        if (!isAdmin()) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allow to upload");
        }
        // declare new Product
        Product p = new Product();
        p.setName(uploadProductForm.getName());
        p.setMainImg(uploadService.uploadImg(uploadProductForm.getMainImg()));
        p.setDiscount(uploadProductForm.getDiscount());
        p.setDescription(uploadProductForm.getDescription());
        p.setPrice(uploadProductForm.getPrice());
        p.setProductCategory(categoryRepository.getById(uploadProductForm.getProductCategoryID()));

        // declare new Item
        Item i = new Item();
        i.setName("defaultProduct");

        // declare variants
        List<Variant> variantList = variantMapper.fromVariantFormListToDataList(uploadProductForm.getVariants());

        // upload and declare asset
        List<Asset> assetList =
                uploadProductForm.getAssets().stream().map(asset -> {
                            Asset a = new Asset();
                            try {
                                a.setLink(uploadService.uploadImg(asset.getData()));
                                a.setType(asset.getType());
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

        return new ApiMessageDto<>("Upload Successfully", HttpStatus.OK);
    }

    @PostMapping("/changeState")
    public ApiMessageDto<String> disableProduct(@RequestBody UpdateStateForm updateStateForm) {
        if (!isAdmin()) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allow to change state");
        }
        Product p = productRepository.findProductById(updateStateForm.getId());
        if (p == null) {
            return new ApiMessageDto<>("Product not Found", HttpStatus.NOT_FOUND);
        }
        p.setStatus(updateStateForm.getStatus());
        productRepository.save(p);
        return new ApiMessageDto<>("disable Product successfully", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ApiMessageDto<String> updateProduct(@Valid @RequestBody UpdateProductForm updateProductForm) throws IOException {
        if (!isAdmin()) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allow to update");
        }

        Product p = productRepository.findProductById(updateProductForm.getId());
        if (p == null) {
            return new ApiMessageDto<>("Product not Found", HttpStatus.NOT_FOUND);
        }
        p.setName(updateProductForm.getName());
        if(updateProductForm.getMainImgNew()!=null){
            p.setMainImg(uploadService.uploadImg(updateProductForm.getMainImgNew()));
        }
        p.setDiscount(updateProductForm.getDiscount());
        p.setDescription(updateProductForm.getDescription());
        p.setPrice(updateProductForm.getPrice());
        p.setProductCategory(categoryRepository.getById(updateProductForm.getProductCategoryID()));

        variantRepository.deleteAll(variantMapper.fromUpdateVariantListFormToData(updateProductForm.getVariantsDelete()));

        List<Variant> variants=variantMapper.fromUpdateVariantListFormToData(updateProductForm.getVariants());

        variants.addAll(variantMapper.fromUpdateVariantListFormToData(updateProductForm.getVariants()));

        variantRepository.saveAll(variants);

        assetRepository.deleteAll(assetMapper.fromUpdateAssetListFormToData(updateProductForm.getAssetsDelete()));

        List<Asset> updateAssets = assetMapper.fromUpdateAssetListFormToData(updateProductForm.getAssets());

        // in case upload new Img
        updateProductForm.getAssetsNew().forEach(newAsset -> {
            Asset a = new Asset();
            try {
                a.setLink(uploadService.uploadImg(newAsset.getData()));
                a.setType(newAsset.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateAssets.add(a);
        });

        updateAssets.forEach(a -> a.setAssetProduct(p));

        p.setAssets(updateAssets);

        productRepository.save(p);

        return new ApiMessageDto<>("update product successfully", HttpStatus.OK);
    }
}
