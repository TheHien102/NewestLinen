package com.example.newestlinen.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.dto.ResponseListObj;
import com.example.newestlinen.dto.account.AccountAdminDto;
import com.example.newestlinen.dto.product.ProductDTO;
import com.example.newestlinen.mapper.ProductMapper;
import com.example.newestlinen.storage.model.ProductModel.Product;
import com.example.newestlinen.utils.projection.repository.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController extends ABasicController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    Cloudinary cloudinary;

    @GetMapping("/get/page={page}")
    public ApiMessageDto<ResponseListObj<ProductDTO>> getProductByPage(@PathVariable String page) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page), 12);

        Page<Product> productPage = productRepository.findAll(pageable);

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

    @PostMapping("/upload")
    public ApiMessageDto<String> uploadProduct(@RequestBody Map<String, String> req) throws IOException {
        System.out.println(
                cloudinary.uploader().upload(req.get("data"), ObjectUtils.asMap(
                        "use_filename", true,
                        "unique_filename", false,
                        "overwrite", true,
                        "public_id","Picture/1"
                )));

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

        apiMessageDto.setData(null);
        apiMessageDto.setMessage("upload success");

        return apiMessageDto;
    }


}
