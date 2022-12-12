package com.example.newestlinen.controller;

import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.dto.ResponseListObj;
import com.example.newestlinen.dto.cart.ProvinceDTO;
import com.example.newestlinen.exception.NotFoundException;
import com.example.newestlinen.exception.RequestException;
import com.example.newestlinen.exception.UnauthorizationException;
import com.example.newestlinen.form.province.AddAllProvinceForm;
import com.example.newestlinen.form.province.AddProvinceForm;
import com.example.newestlinen.form.province.UpdateProvinceForm;
import com.example.newestlinen.mapper.cart.ProvinceMapper;
import com.example.newestlinen.storage.criteria.ProvinceCriteria;
import com.example.newestlinen.storage.model.Address.Province;
import com.example.newestlinen.utils.projection.repository.Cart.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/province")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class ProvinceController extends ABasicController {

    private final ProvinceRepository provinceRepository;

    private final ProvinceMapper provinceMapper;

    @GetMapping("/list")
    public ApiMessageDto<ResponseListObj<ProvinceDTO>> listProvince(ProvinceCriteria provinceCriteria, Pageable pageable) {

        Page<Province> provinces = provinceRepository.findAll(provinceCriteria.getSpecification(), pageable);

        ResponseListObj<ProvinceDTO> responseListObj = new ResponseListObj<>();

        responseListObj.setData(provinceMapper.fromProvinceManagementDataListToDtoList(provinces.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(provinces.getTotalPages());
        responseListObj.setTotalElements(provinces.getTotalElements());

        ApiMessageDto<ResponseListObj<ProvinceDTO>> apiMessageDto = new ApiMessageDto<>();

        apiMessageDto.setMessage("get list Province success");
        apiMessageDto.setStatus(HttpStatus.OK);
        apiMessageDto.setResult(true);
        apiMessageDto.setData(responseListObj);

        return apiMessageDto;
    }

    @PostMapping("/add")
    public ApiMessageDto<String> addProvince(@Valid @RequestBody AddProvinceForm addProvinceForm) {
        if (!isAdmin()) {
            throw new UnauthorizationException("not allow to add");
        }
        Province province = new Province();
        province.setName(addProvinceForm.getName());
        province.setParent(provinceRepository.findById(addProvinceForm.getParentId()).orElse(null));
        provinceRepository.save(province);

        return new ApiMessageDto<>("Add Province Success", HttpStatus.OK);
    }

    @PostMapping("/addAll")
    public ApiMessageDto<String> addAllProvince(@Valid @RequestBody AddAllProvinceForm addAllProvinceForm) {
        if (!isAdmin()) {
            throw new RequestException("not allow to add");
        }

        Province parent = provinceRepository.findById(addAllProvinceForm.getParentId()).orElse(null);

        List<Province> provinceList = addAllProvinceForm.getNames().stream().map(name -> {
            Province p = new Province();
            p.setName(name);
            p.setLevel(addAllProvinceForm.getLevel());
            p.setParent(parent);
            return p;
        }).collect(Collectors.toList());

        provinceRepository.saveAll(provinceList);

        return new ApiMessageDto<>("Add All Provinces Success", HttpStatus.OK);
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
}
