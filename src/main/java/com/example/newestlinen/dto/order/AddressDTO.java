package com.example.newestlinen.dto.order;

import com.example.newestlinen.storage.model.Address.Province;
import lombok.Data;

@Data
public class AddressDTO {
    private Long id;

    private Province city;

    private Province district;

    private Province ward;

    private String phone;
}
