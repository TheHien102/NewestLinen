package com.example.newestlinen.utils.projection.repository.Order;

import com.example.newestlinen.storage.model.Address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {
    List<Address> findAllByAccountId(Long account_id);
}
