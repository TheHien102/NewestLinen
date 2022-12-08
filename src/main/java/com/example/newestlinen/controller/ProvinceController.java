package com.example.newestlinen.controller;

import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.dto.ResponseListObj;
import com.example.newestlinen.dto.cart.ProvinceManagementDTO;
import com.example.newestlinen.exception.NotFoundException;
import com.example.newestlinen.exception.RequestException;
import com.example.newestlinen.form.cart.AddProvinceForm;
import com.example.newestlinen.form.cart.UpdateProvinceForm;
import com.example.newestlinen.mapper.cart.ProvinceMapper;
import com.example.newestlinen.storage.model.Address.Province;
import com.example.newestlinen.utils.projection.repository.Cart.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/province")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class ProvinceController extends ABasicController {

    private final ProvinceRepository provinceRepository;

    private final ProvinceMapper provinceMapper;

    @GetMapping("/list")
    public ApiMessageDto<ResponseListObj<ProvinceManagementDTO>> listProvince(Pageable pageable) {
        Page<Province> provinces = provinceRepository.findAll(pageable);

        ResponseListObj<ProvinceManagementDTO> responseListObj = new ResponseListObj<>();

        responseListObj.setData(provinceMapper.fromProvinceManagementDataListToDtoList(provinces.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(provinces.getTotalPages());
        responseListObj.setTotalElements(provinces.getTotalElements());

        ApiMessageDto<ResponseListObj<ProvinceManagementDTO>> apiMessageDto = new ApiMessageDto<>();

        apiMessageDto.setMessage("get list Province success");
        apiMessageDto.setStatus(HttpStatus.OK);
        apiMessageDto.setResult(true);
        apiMessageDto.setData(responseListObj);

        return apiMessageDto;
    }

    @PostMapping("/add")
    public ApiMessageDto<String> addProvince(@Valid @RequestBody AddProvinceForm addProvinceForm) {
        if (!isAdmin()) {
            throw new RequestException("not allow to add");
        }
        Province province = new Province();
        province.setName(addProvinceForm.getName());
        province.setParent(provinceRepository.findById(addProvinceForm.getParentId()).orElse(null));
        provinceRepository.save(province);

        return new ApiMessageDto<>("Add Province Success", HttpStatus.OK);
    }

    @PutMapping("/update")
    public ApiMessageDto<String> updateProvince(@Valid @RequestBody UpdateProvinceForm updateProvinceForm) {
        if (!isAdmin()) {
            throw new RequestException("not allow to add");
        }
        Province province = provinceRepository.findById(updateProvinceForm.getId()).orElse(null);

        if (province == null) {
            throw new NotFoundException("not found");
        }

        province.setName(updateProvinceForm.getName());
        province.setParent(provinceRepository.findById(updateProvinceForm.getParentId()).orElse(null));
        provinceRepository.save(province);

        return new ApiMessageDto<>("Add Province Success", HttpStatus.OK);
    }

    @GetMapping("/getChild")
    public ApiMessageDto<ResponseListObj<ProvinceManagementDTO>> listProvince(Long id, Pageable pageable) {
        if (!isAdmin()) {
            throw new RequestException("not allow to add");
        }
        ResponseListObj<ProvinceManagementDTO> responseListObj = new ResponseListObj<>();

        Page<Province> addressManagements = provinceRepository.findAllByParentId(id, pageable);

        responseListObj.setData(provinceMapper.fromProvinceManagementDataListToDtoList(addressManagements.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(addressManagements.getTotalPages());
        responseListObj.setTotalElements(addressManagements.getTotalElements());

        ApiMessageDto<ResponseListObj<ProvinceManagementDTO>> apiMessageDto = new ApiMessageDto<>();

        apiMessageDto.setMessage("get list Province success");
        apiMessageDto.setStatus(HttpStatus.OK);
        apiMessageDto.setResult(true);
        apiMessageDto.setData(responseListObj);

        return apiMessageDto;
    }

}
