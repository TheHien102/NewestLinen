package com.example.newestlinen.controller;

import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.dto.ResponseListObj;
import com.example.newestlinen.dto.cart.AddressManagementDTO;
import com.example.newestlinen.exception.NotFoundException;
import com.example.newestlinen.exception.RequestException;
import com.example.newestlinen.form.cart.AddNewAddressManageForm;
import com.example.newestlinen.form.cart.UpdateAddressManageForm;
import com.example.newestlinen.mapper.cart.AddressManagementMapper;
import com.example.newestlinen.storage.model.Address.AddressManagement;
import com.example.newestlinen.utils.projection.repository.Cart.AddressManagementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/addressManagement")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class AddressManagementController extends ABasicController {

    private final AddressManagementRepository addressManagementRepository;

    private final AddressManagementMapper addressManagementMapper;

    @GetMapping("/list")
    public ApiMessageDto<ResponseListObj<AddressManagementDTO>> listAddress(Pageable pageable) {
        Page<AddressManagement> addressManagements = addressManagementRepository.findAll(pageable);

        ResponseListObj<AddressManagementDTO> responseListObj = new ResponseListObj<>();

        responseListObj.setData(addressManagementMapper.fromAddressManagementDataListToDtoList(addressManagements.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(addressManagements.getTotalPages());
        responseListObj.setTotalElements(addressManagements.getTotalElements());

        ApiMessageDto<ResponseListObj<AddressManagementDTO>> apiMessageDto = new ApiMessageDto<>();

        apiMessageDto.setMessage("get list address success");
        apiMessageDto.setStatus(HttpStatus.OK);
        apiMessageDto.setResult(true);
        apiMessageDto.setData(responseListObj);

        return apiMessageDto;
    }

    @PostMapping("/add")
    public ApiMessageDto<String> addAddress(@Valid @RequestBody AddNewAddressManageForm addNewAddressForm) {
        if (!isAdmin()) {
            throw new RequestException("not allow to add");
        }
        AddressManagement address = new AddressManagement();
        address.setName(addNewAddressForm.getName());
        address.setParent(addressManagementRepository.findById(addNewAddressForm.getParentId()).orElse(null));
        addressManagementRepository.save(address);

        return new ApiMessageDto<>("Add address Success", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ApiMessageDto<String> updateAddress(@Valid @RequestBody UpdateAddressManageForm updateAddressManageForm) {
        if (!isAdmin()) {
            throw new RequestException("not allow to add");
        }
        AddressManagement address = addressManagementRepository.findById(updateAddressManageForm.getId()).orElse(null);

        if(address==null){
            throw new NotFoundException("not found");
        }

        address.setName(updateAddressManageForm.getName());
        address.setParent(addressManagementRepository.findById(updateAddressManageForm.getParentId()).orElse(null));
        addressManagementRepository.save(address);

        return new ApiMessageDto<>("Add address Success", HttpStatus.OK);
    }
}
