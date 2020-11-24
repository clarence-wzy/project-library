package com.wzy.service;

import com.wzy.entity.Product;
import com.wzy.entity.ProductType;
import com.wzy.entity.Quality;
import com.wzy.entity.Trade;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Package: com.wzy.service
 * @Author: Clarence1
 * @Date: 2019/11/26 20:28
 */
public interface ProductService {

    /**
     * 查询全部商品信息
     * @return
     */
    List<Product> findAllProduct(int startIndex, int pageSize);

    /**
     * @decription 根据商品类型或商品名称查询商品（允许分页）
     * @param typeName
     * @param productName
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<Product> findProductWithCondition(String typeName, String productName, Integer startIndex, Integer pageSize);

    /**
     * @decription 查询全部商品总数
     * @return
     */
    int countAllProduct();

    /**
     * @decription 插入商品信息
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * @decription 根据商品名称查询商品信息
     * @param productName
     * @return
     */
    List<Product> findProductByProductName(String productName);

    /**
     * @decription 根据用户id查询商品信息
     * @param userId
     * @return
     */
    List<Product> findProductByUserId(int userId);

    /**
     * @decription 根据商品Id查询商品信息
     * @param productId
     * @return
     */
    Product findProductByProductId(int productId);

    /**
     * @decription 查询全部的商品类型
     * @return
     */
    List<ProductType> findAllType();

    /**
     * @decription 根据商品类型id查询商品类型名
     * @param typeId
     * @return
     */
    ProductType findTypeNameByTypeId(int typeId);

    /**
     * @decription 根据商品类型名查询商品类型（支持模糊查询）
     * @param typeName
     * @return
     */
    List<ProductType> findTypeByTypeName(String typeName);

    /**
     * @decription 查询全部成色信息
     * @return
     */
    List<Quality> findAllQuality();

    /**
     * @decription 查询全部交易信息
     * @return
     */
    List<Trade> findAllTrade();

    /**
     * @decription 根据商品id更新商品数量
     * @param cartNumber
     * @param productStatus
     * @param productId
     * @return
     */
    int updateProductNumber(int cartNumber, String productStatus, int productId);

    /**
     * @decription 根据商品id更新商品信息
     * @param product
     * @return
     */
    int updateProductInfo(Product product);

    /**
     * @decription 随机查询出 count 条记录
     * @param typeIds
     * @param count
     * @return
     */
    List<Product> findRandProduct(int[] typeIds, int count);

    /**
     * @decription 上传图片
     * @param uploadFile
     * @param filePath
     * @return
     */
    Object uploadPicture(MultipartFile uploadFile, String filePath);

    /**
     * @decription 删除图片
     * @param filePath
     */
    void detelePicture(String filePath);

}
