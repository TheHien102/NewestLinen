package com.example.newestlinen.controller;

import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.dto.ErrorCode;
import com.example.newestlinen.dto.ResponseListObj;
import com.example.newestlinen.dto.product.ProductAdminDTO;
import com.example.newestlinen.dto.product.ProductDetailDTO;
import com.example.newestlinen.dto.product.ProductUserDTO;
import com.example.newestlinen.exception.RequestException;
import com.example.newestlinen.form.UpdateStateForm;
import com.example.newestlinen.form.product.UpdateAssetForm;
import com.example.newestlinen.form.product.UpdateProductForm;
import com.example.newestlinen.form.product.UploadProductForm;
import com.example.newestlinen.mapper.product.AssetMapper;
import com.example.newestlinen.mapper.product.ProductMapper;
import com.example.newestlinen.mapper.product.VariantMapper;
import com.example.newestlinen.service.UploadService;
import com.example.newestlinen.storage.criteria.ProductCriteria;
import com.example.newestlinen.storage.model.ProductModel.Asset;
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.storage.model.ProductModel.Variant;
import com.example.newestlinen.utils.projection.repository.CategoryRepository;
import com.example.newestlinen.utils.projection.repository.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/list")
    public Object getProductByPageAdmin(ProductCriteria productCriteria, Pageable pageable) {
        if (isAdmin()) {
            // list for admin
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
        } else {
            // list for users and guests
            Page<Product> productPage = productRepository.findAll(productCriteria.getSpecification(), pageable);

            ApiMessageDto<ResponseListObj<ProductUserDTO>> apiMessageDto = new ApiMessageDto<>();

            productPage.getContent().forEach(product -> product.setVariants(product.getVariants().stream().filter(variant -> variant.getName().equalsIgnoreCase("color")).collect(Collectors.toList())));

            ResponseListObj<ProductUserDTO> responseListObj = new ResponseListObj<>();

            responseListObj.setData(productMapper.fromProductUserDataListToDtoList(productPage.getContent()));
            responseListObj.setPage(pageable.getPageNumber());
            responseListObj.setTotalPage(productPage.getTotalPages());
            responseListObj.setTotalElements(productPage.getTotalElements());

            apiMessageDto.setData(responseListObj);
            apiMessageDto.setMessage("List product success");

            return apiMessageDto;
        }
    }

    @GetMapping("/get/{Id}")
    public ApiMessageDto<ProductDetailDTO> getProductById(@PathVariable("Id") Long Id) {
        Product p = productRepository.findProductById(Id);
        if (p == null) {
            return new ApiMessageDto<>("Product not Found", HttpStatus.NOT_FOUND);
        }
        ApiMessageDto<ProductDetailDTO> apiMessageDto = new ApiMessageDto<>();

        ProductDetailDTO data = productMapper.fromProductDetailDataToObject(p);

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
        p.setProductCategory(categoryRepository.getById(uploadProductForm.getProductCategoryId()));

        // declare variants
        List<Variant> variantList = variantMapper.fromVariantUploadFormListToDataList(uploadProductForm.getVariants());

        // upload and declare asset
        List<Asset> assetList =
                uploadProductForm.getAssets().stream().map(asset -> {
                            Asset a = new Asset();
                            try {
                                a.setLink(uploadService.uploadImg(asset.getLink()));
                                a.setType(asset.getType());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return a;
                        }
                ).collect(Collectors.toList());

        // Map together
        variantList.forEach(v -> v.setVariantProduct(p));

        p.setVariants(variantList);

        assetList.forEach(a -> a.setAssetProduct(p));

        p.setAssets(assetList);

        productRepository.save(p);

        return new ApiMessageDto<>("Upload Successfully", HttpStatus.OK);
    }

    @PutMapping("/changeState")
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

    @PutMapping("/update")
    public ApiMessageDto<String> updateProduct(@Valid @RequestBody UpdateProductForm updateProductForm) throws IOException {
        if (!isAdmin()) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allow to update");
        }

        Product p = productRepository.findProductById(updateProductForm.getId());
        if (p == null) {
            return new ApiMessageDto<>("Product not Found", HttpStatus.NOT_FOUND);
        }
        p.setName(updateProductForm.getName());
        if (!updateProductForm.getMainImg().equalsIgnoreCase(p.getMainImg())) {
            uploadService.deleteImg(p.getMainImg());
            p.setMainImg(uploadService.uploadImg(updateProductForm.getMainImg()));
        }
        p.setDiscount(updateProductForm.getDiscount());
        p.setDescription(updateProductForm.getDescription());
        p.setPrice(updateProductForm.getPrice());
        p.setProductCategory(categoryRepository.getById(updateProductForm.getProductCategoryId()));

        // upload new IMG
        updateProductForm.getAssets().forEach(updateAssetForm -> {
            if (updateAssetForm.getId() < 0) {
                try {
                    updateAssetForm.setLink(uploadService.uploadImg(updateAssetForm.getLink()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        List<Variant> updateVariants = variantMapper.fromUpdateVariantListFormToData(updateProductForm.getVariants());

        List<Asset> updateAssets = assetMapper.fromUpdateAssetListFormToData(updateProductForm.getAssets());

        // delete IMG not contain in upload form
        p.getAssets().forEach(asset -> {
            if (!updateAssets.contains(asset)) {
                try {
                    uploadService.deleteImg(asset.getLink());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        updateAssets.forEach(a -> a.setAssetProduct(p));
        p.getAssets().addAll(updateAssets);

        updateVariants.forEach(v -> v.setVariantProduct(p));
        p.getVariants().addAll(updateVariants);

        productRepository.save(p);

        return new ApiMessageDto<>("update product successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Transactional
    public ApiMessageDto<String> deleteProduct(@RequestBody Map<String, String> req) {
        productRepository.deleteById(Long.parseLong(req.get("id")));
        return new ApiMessageDto<>("deleted product id: " + req.get("id"), HttpStatus.OK);
    }
}
