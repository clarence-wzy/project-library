package com.wzy.service.impl;

import com.wzy.entity.Address;
import com.wzy.mapper.AddressMapper;
import com.wzy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Package: com.wzy.service.impl
 * @Author: Clarence1
 * @Date: 2019/12/19 17:03
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> findAddressByOwn(int ownId, String ownName) {
        return addressMapper.findAddressByOwn(ownId, ownName);
    }

    @Override
    public Address findAddressByAddressId(int addressId) {
        return addressMapper.findAddressByAddressId(addressId);
    }

    @Override
    public int insertAddress(Address address) {
        return addressMapper.insertAddress(address);
    }

    @Override
    public int updateAddress(Address address) {
        return addressMapper.updateAddress(address);
    }

    @Override
    public Integer findDefaultAddress(int ownId) {
        return addressMapper.findDefaultAddress(ownId);
    }

    @Override
    public int updateDefaultAddress(int addressId, String defaultAddress) {
        return addressMapper.updateDefaultAddress(addressId, defaultAddress);
    }

    @Override
    public int updateAddressStatus(int addressId, String addressStatus) {
        return addressMapper.updateAddressStatus(addressId, addressStatus);
    }
}
