package com.example.newestlinen.controller;

import com.example.newestlinen.dto.ApiMessageDto;
import com.example.newestlinen.dto.ResponseListObj;
import com.example.newestlinen.dto.order.AddressDTO;
import com.example.newestlinen.exception.NotFoundException;
import com.example.newestlinen.exception.UnauthorizationException;
import com.example.newestlinen.form.order.CreateAddressForm;
import com.example.newestlinen.mapper.order.AddressMapper;
import com.example.newestlinen.storage.model.Account;
import com.example.newestlinen.storage.model.Address.Address;
import com.example.newestlinen.utils.projection.repository.AccountRepository;
import com.example.newestlinen.utils.projection.repository.Cart.ProvinceRepository;
import com.example.newestlinen.utils.projection.repository.Order.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/address")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class AddressController extends ABasicController {

    private final AddressRepository addressRepository;

    private final ProvinceRepository provinceRepository;

    private final AddressMapper addressMapper;

    private final AccountRepository accountRepository;

    @GetMapping("/list")
    public ApiMessageDto<List<AddressDTO>> listAddress() {
        if (!isCustomer()) {
            throw new UnauthorizationException("not a user");
        }
        List<Address> addressList = addressRepository.findAllByAccountId(getCurrentUserId());

        ApiMessageDto<List<AddressDTO>> apiMessageDto = new ApiMessageDto<>();

        apiMessageDto.setData(addressMapper.fromAddressListToDataList(addressList));
        apiMessageDto.setMessage("List address success");
        return apiMessageDto;
    }

    @PostMapping("/create")
    public ApiMessageDto<String> createAddress(@Valid @RequestBody CreateAddressForm createAddressForm) {
        if (!isCustomer()) {
            throw new UnauthorizationException("not a user");
        }

        Account account = accountRepository.findById(getCurrentUserId()).orElse(null);
        if (account == null) {
            throw new NotFoundException("account not found");
        }

        Address address = new Address();

        address.setCity(provinceRepository.findById(createAddressForm.getProvince_cityId()).orElse(null));
        address.setDistrict(provinceRepository.findById(createAddressForm.getProvince_districtId()).orElse(null));
        address.setWard(provinceRepository.findById(createAddressForm.getProvince_wardId()).orElse(null));
        address.setDetails(createAddressForm.getDetails());
        address.setPhone(createAddressForm.getPhone());

        address.setAccount(account);

        addressRepository.save(address);

        return new ApiMessageDto<>("Create address successfully", HttpStatus.OK);
    }
}
