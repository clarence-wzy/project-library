package com.wzy.controller;

import com.wzy.entity.Address;
import com.wzy.service.AddressService;
import com.wzy.util.JSONResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Package: com.wzy.controller
 * @Author: Clarence1
 * @Date: 2020/1/12 16:37
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/address")
@Slf4j
public class AddressController {

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "根据所属人查询收货地址信息")
    @GetMapping(value = "/findAddressByOwn/{ownId}/{ownName}")
    public JSONResult findAddressByOwn(@PathVariable("ownId") int ownId, @PathVariable("ownName") String ownName) {
        log.info("==============查询【" + ownId + "或" + ownName + "】用户的收货地址信息==============");
        if (ownName.equals("null")) {
            ownName = null;
        }
        return JSONResult.ok(addressService.findAddressByOwn(ownId, ownName));
    }

    @ApiOperation(value = "插入新的收货地址")
    @PostMapping(value = "/insertAddress")
    public JSONResult insertAddress(@RequestBody Address address) {
        log.info("==============插入【" + address + "】收货地址==============");
        Integer defaultAddressId = addressService.findDefaultAddress(address.getOwnId());
        if (defaultAddressId != null) {
            addressService.updateDefaultAddress(defaultAddressId, "false");
        }
        if (addressService.findDefaultAddress(address.getOwnId()) == null) {
            address.setDefaultAddress("true");
        }
        return addressService.insertAddress(address) == 1? JSONResult.ok("插入收货地址成功"):JSONResult.errorMsg("插入收货地址失败");
    }

    @ApiOperation(value = "根据所属人id更新收货地址")
    @PostMapping(value = "/updateAddress/{own_id}")
    public JSONResult updateAddress(@RequestBody Address address, @PathVariable("own_id") int ownId) {
        log.info("==============更新【" + ownId + "】的【" + address + "】收货地址==============");
        int defaultAddressId = addressService.findDefaultAddress(ownId);
        if (address.getDefaultAddress().equals("true") && address.getAddressId() != defaultAddressId) {
            addressService.updateDefaultAddress(defaultAddressId, "false");
        }
        return addressService.updateAddress(address) == 1? JSONResult.ok("更新收货地址成功"):JSONResult.errorMsg("更新收货地址失败");
    }

    @ApiOperation(value = "更新默认收货地址")
    @PostMapping(value = "/updateDefaultAddress/{address_id}/{default_address}")
    public JSONResult insertAddress(@PathVariable("address_id") int addressId, @PathVariable("default_address") String defaultAddress) {
        log.info("==============更新【" + addressId + "】地址的默认收货地址" + defaultAddress + "==============");
        return addressService.updateDefaultAddress(addressId, defaultAddress) == 1? JSONResult.ok("更新默认收货地址成功"):JSONResult.errorMsg("更新默认收货地址失败");
    }

    @ApiOperation(value = "更新收货地址状态")
    @PostMapping(value = "/updateAddressStatus/{address_id}/{address_status}")
    public JSONResult updateAddressStatus(@PathVariable("address_id") int addressId, @PathVariable("address_status") String addressStatus) {
        log.info("==============更新【" + addressId + "】地址的收货地址状态" + addressStatus + "==============");
        return addressService.updateAddressStatus(addressId, addressStatus) == 1? JSONResult.ok("更新收货地址状态成功"):JSONResult.errorMsg("更新收货地址状态失败");
    }

    @ApiOperation(value = "更新默认地址升级版")
    @PostMapping(value = "/updateDefaultAddressByOwnId/{address_id}/{own_id}")
    public JSONResult updateDefaultAddressByOwnId(@PathVariable("address_id") int addressId, @PathVariable("own_id") int ownId) {
        log.info("==============更新【" + addressId + "】地址的默认收货地址，用户id" + ownId + "==============");
        Integer defaultAddressId = addressService.findDefaultAddress(ownId);
        if (defaultAddressId != null) {
            addressService.updateDefaultAddress(defaultAddressId, "false");
        }
        return  addressService.updateDefaultAddress(addressId, "true") == 1? JSONResult.ok("更新默认收货地址成功"):JSONResult.errorMsg("更新默认收货地址失败");
    }

}
