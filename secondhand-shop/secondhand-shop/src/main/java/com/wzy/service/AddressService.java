package com.wzy.service;

import com.wzy.entity.Address;

import java.util.List;

/**
 * @Package: com.wzy.service
 * @Author: Clarence1
 * @Date: 2019/12/19 15:01
 */
public interface AddressService {

    /**
     * @decription 根据所属人id 或所属人姓名查询收货地址信息
     * @param ownId
     * @param ownName
     * @return
     */
    List<Address> findAddressByOwn(int ownId, String ownName);

    /**
     * @decription 根据收货地址id查询收货地址信息
     * @param addressId
     * @return
     */
    Address findAddressByAddressId(int addressId);

    /**
     * @decription 插入新的收货地址
     * @param address
     * @return
     */
    int insertAddress(Address address);

    /**
     * @decription 根据收货地址id 更新收货地址信息
     * @param address
     * @return
     */
    int updateAddress(Address address);

    /**
     * @decription 根据所属人id 查询默认收货地址
     * @param ownId
     * @return
     */
    Integer findDefaultAddress(int ownId);

    /**
     * @decription 根据收货地址id更新默认收货地址
     * @param addressId
     * @param defaultAddress
     * @return
     */
    int updateDefaultAddress(int addressId, String defaultAddress);

    /**
     * @decription 更新收货地址状态
     * @param addressId
     * @param addressStatus
     * @return
     */
    int updateAddressStatus(int addressId, String addressStatus);

}
