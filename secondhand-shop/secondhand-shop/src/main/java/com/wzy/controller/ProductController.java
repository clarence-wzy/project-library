package com.wzy.controller;

import com.alibaba.fastjson.JSONObject;
import com.wzy.entity.Collect;
import com.wzy.entity.Product;
import com.wzy.entity.ProductType;
import com.wzy.service.CollectService;
import com.wzy.service.ProductService;
import com.wzy.service.UserService;
import com.wzy.util.ApprovalUtil;
import com.wzy.util.JSONResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: com.wzy.controller
 * @Author: Clarence1
 * @Date: 2019/11/27 9:44
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CollectService collectService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "查询全部商品信息")
    @GetMapping(value = "/findAllProduct")
    public JSONResult getAllProduct(int startIndex, int pageSize) {
        log.info("==============查询【" + startIndex + "-" + pageSize + "】的全部商品信息==============");
        return JSONResult.ok(productService.findAllProduct(startIndex, pageSize));
    }

    @ApiOperation(value = "根据商品类型或商品名称查询商品")
    @GetMapping(value = "/findProductWithCondition")
    public JSONResult getProductWithCondition(String typeName, String productName, Integer startIndex, Integer pageSize) {
        log.info("==============查询【" + typeName + "-" + productName + "】【" + startIndex + "-" + pageSize + "】的全部商品信息==============");
        return JSONResult.ok(productService.findProductWithCondition(typeName, productName, startIndex, pageSize));
    }

    @ApiOperation(value = "查询全部商品总数")
    @GetMapping(value = "/countAllProduct")
    public JSONResult getCountAllProduct() {
        log.info("==============查询全部商品总数==============");
        return JSONResult.ok(productService.countAllProduct());
    }

    @ApiOperation(value = "根据商品名查询商品信息")
    @GetMapping(value = "/findProductByProductName/{product_name}")
    public JSONResult getProductByProductName(@PathVariable(value = "product_name") String productName) {
        log.info("==============查询【" + productName + "】商品信息==============");
        List<Product> products = productService.findProductByProductName(productName);
        for (Product product : products) {
            product.setTypeName(productService.findTypeNameByTypeId(product.getTypeId()).getTypeName());
        }
        return JSONResult.ok(products);
    }

    @ApiOperation(value = "根据拥有者用户id查询商品信息")
    @GetMapping(value = "/findProductByUserId/{user_id}")
    public JSONResult getProductByProductName(@PathVariable(value = "user_id") int userId) {
        log.info("==============查询用户【" + userId + "】的商品信息==============");
        return JSONResult.ok(productService.findProductByUserId(userId));
    }

    @ApiOperation(value = "根据商品id查询商品信息")
    @GetMapping(value = "/findProductByProductId/{product_id}/{user_id}")
    public JSONResult getProductByProductName(@PathVariable(value = "product_id") int productId, @PathVariable(value = "user_id") int userId) {
        log.info("==============用户【" + userId +"】查询【" + productId + "】商品信息==============");
        Product product = productService.findProductByProductId(productId);
        product.setTypeName(productService.findTypeNameByTypeId(product.getTypeId()).getTypeName());

        List<Collect> collect = collectService.findCollectByUserIdAndProductId(userId, product.getProductId());
        product.setCollect(collect);

        product.setUser(userService.findUserByUserId(product.getOwnId()));

        return JSONResult.ok(product);
    }

    @ApiOperation(value = "查询全部商品类型信息")
    @GetMapping(value = "/findAllType")
    public JSONResult getAllType() {
        log.info("==============查询全部商品类型信息==============");
        return JSONResult.ok(productService.findAllType());
    }

    @ApiOperation(value = "查询商品类型信息")
    @GetMapping(value = "/findTypeByTypeName/{type_name}")
    public JSONResult getTypeByTypeName(@PathVariable(value = "type_name") String typeName) {
        log.info("==============查询【" + typeName + "】商品类型信息==============");
        return JSONResult.ok(productService.findTypeByTypeName(typeName));
    }

    @ApiOperation(value = "插入商品信息")
    @PostMapping(value = "/insertProduct")
    public JSONResult insertProduct(@RequestParam(value = "productJson") String productJson, @RequestParam(value = "image") MultipartFile[] images,
                                    @RequestParam(value = "detail_image") MultipartFile[] detailImages) {
        Product product = JSONObject.parseObject(productJson, Product.class);
        product.setProductCreateTime(LocalDateTime.now());
        product.setTypeId(productService.findTypeByTypeName(product.getTypeName()).get(0).getTypeId());
        log.info("==============插入商品信息【" + product + "】==============");

        //  检查商品图片、商品详情图片
        int count = 0, count2 = 0;
        Map<Object, Object> map1 = new HashMap<>(10);
        Map<Object, Object> map2 = new HashMap<>(10);
//        boolean approval = true, approval2 = true;
//        approval = CommentaryController.isApproval(images, count, map1, approval, log);
//        for (MultipartFile file : detailImages) {
//            count2++;
//            try {
//                if(!ApprovalUtil.pictureApprovalUtil(file).equals("合规")) {
//                    log.error("第" + count2 + "张详情照片不合规");
//                    map2.put(count2, "第" + count2 + "张详情照片不合规");
//                    approval2 = false;
//                }
//            } catch (Exception e) {
//                log.error("检查详情图片出现异常，异常信息--》" + e.getMessage());
//            }
//        }
//
//        Map<Object, Object> map = new HashMap<>(2);
//        if (!approval || !approval2) {
//            map.put("展示图片不合规", map1);
//            map.put("详情图片不合规", map2);
//            return JSONResult.errorMap(map);
//        }

        //  上传商品图片
//        if (approval) {
            count = 0;
            for (MultipartFile file : images) {
                Object result = productService.uploadPicture(file, "product/" + product.getProductName() + "/");
                map1.put(count, result);
                count++;
            }
//        }
        product.setImage(subImageStr(map1));

        //  上传商品详情图片
//        if (approval2) {
            count = 0;
            for (MultipartFile file : detailImages) {
                Object result = productService.uploadPicture(file, "product/" + product.getProductName() + "/detail/");
                map2.put(count, result);
                count++;
            }
//        }
        product.setDetailImage(subImageStr(map2));

        //数据库插入商品信息
        log.info("==============插入商品" + product + "==============");
        int i = productService.insertProduct(product);
//        try {
            return i == 1? JSONResult.ok("插入商品成功") : JSONResult.errorMsg("插入商品失败");
//        } catch (Exception e) {
//            log.error("插入商品失败，异常信息：" + e.getMessage());
//            return JSONResult.errorMsg("插入商品时出现系统异常");
//        }
    }

    @ApiOperation(value = "查询全部成色信息")
    @GetMapping(value = "/findAllQuality")
    public JSONResult getAllQuality() {
        log.info("==============查询全部成色信息==============");
        return JSONResult.ok(productService.findAllQuality());
    }

    @ApiOperation(value = "查询全部交易信息")
    @GetMapping(value = "/findAllTrade")
    public JSONResult getAllTrade() {
        log.info("==============查询全部交易信息==============");
        return JSONResult.ok(productService.findAllTrade());
    }

    @ApiOperation(value = "删除已发布商品的图片")
    @PostMapping(value = "/deleteImage")
    public JSONResult deleteImage(@RequestParam("url") String url) {
        log.info("==============delete>>>" + url);
//        productService.detelePicture(url);
        return JSONResult.ok("删除成功");
    }

    @ApiOperation(value = "根据商品id更新商品信息")
    @PostMapping(value = "/updateProduct")
    public JSONResult updateProduct(@RequestParam(value = "productJson") String productJson, @RequestParam(value = "image") MultipartFile[] images,
                                    @RequestParam(value = "detail_image") MultipartFile[] detailImages) {
        Product product = JSONObject.parseObject(productJson, Product.class);
        product.setTypeId(productService.findTypeByTypeName(product.getTypeName()).get(0).getTypeId());
        log.info("==============根据商品id更新商品信息【" + product + "】==============");

        //  检查商品图片、商品详情图片
        int count = 0, count2 = 0;
        Map<Object, Object> map1 = new HashMap<>(10);
        Map<Object, Object> map2 = new HashMap<>(10);
        boolean approval = true, approval2 = true;
        approval = CommentaryController.isApproval(images, count, map1, approval, log);
        for (MultipartFile file : detailImages) {
            count2++;
            try {
                if(!ApprovalUtil.pictureApprovalUtil(file).equals("合规")) {
                    log.error("第" + count2 + "张详情照片不合规");
                    map2.put(count2, "第" + count2 + "张详情照片不合规");
                    approval2 = false;
                }
            } catch (Exception e) {
                log.error("检查详情图片出现异常，异常信息--》" + e.getMessage());
            }
        }

        Map<Object, Object> map = new HashMap<>(2);
        if (!approval || !approval2) {
            map.put("展示图片不合规", map1);
            map.put("详情图片不合规", map2);
            return JSONResult.errorMap(map);
        }

        //  上传商品图片
        if (images.length > 0) {
            if (approval) {
                count = 0;
                for (MultipartFile file : images) {
                    Object result = productService.uploadPicture(file, "product/" + product.getProductName() + "/");
                    map1.put(count, result);
                    count++;
                }
            }
            product.setImage(product.getImage() + "、" + subImageStr(map1));
        }

        //  上传商品详情图片
        if (detailImages.length > 0) {
            if (approval2) {
                count = 0;
                for (MultipartFile file : detailImages) {
                    Object result = productService.uploadPicture(file, "product/" + product.getProductName() + "/detail/");
                    map2.put(count, result);
                    count++;
                }
            }
            product.setDetailImage(product.getDetailImage() + "、" + subImageStr(map2));
        }

        //数据库插入商品信息
        log.info("==============更新商品" + product + "==============");
        try {
            return productService.updateProductInfo(product) == 1? JSONResult.ok("更新商品成功") : JSONResult.errorMsg("更新商品失败");
        } catch (Exception e) {
            log.error("更新商品失败，异常信息：" + e.getMessage());
            return JSONResult.errorMsg("更新商品时出现系统异常");
        }
    }

    private String subImageStr(Map<Object, Object> map2) {
        String imageStr = "";
        for (Object image : map2.values()) {
            imageStr = imageStr + image + "、";
        }
        if (!imageStr.equals("") && imageStr.length() > 0) {
            imageStr = imageStr.substring(0, imageStr.length() - 1);
        }
        return imageStr;
    }

    @ApiOperation(value = "/随机查询商品信息")
    @GetMapping(value = "/findRandProduct/{count}/{type_name}")
    public JSONResult findRandProduct(@PathVariable("type_name") String typeName, @PathVariable("count") int count) {
        log.info("==============随机查询" + count + "条【" + typeName + "】的商品信息==============");
        List<ProductType> productTypes = productService.findTypeByTypeName(typeName);

        String typeStr = "";
        for (ProductType productType : productTypes) {
            typeStr = typeStr + productType.getTypeId() + ",";
        }
        String[] typeIdString = typeStr.split(",");
        int[] typeIds = new int[typeIdString.length];
        for (int i=0; i < typeIdString.length; i++) {
            typeIds[i] = Integer.parseInt(typeIdString[i]);
        }
        return JSONResult.ok(productService.findRandProduct(typeIds, count));
    }

}
