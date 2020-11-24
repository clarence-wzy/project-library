package com.wzy.service.impl;

import com.wzy.entity.Product;
import com.wzy.entity.ProductType;
import com.wzy.entity.Quality;
import com.wzy.entity.Trade;
import com.wzy.mapper.ProductMapper;
import com.wzy.service.ProductService;
import com.wzy.util.FtpUtil;
import com.wzy.util.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Package: com.wzy.service.impl
 * @Author: Clarence1
 * @Date: 2019/11/27 9:43
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findAllProduct(int startIndex, int pageSize) {
        return productMapper.findAllProduct(startIndex, pageSize);
    }

    @Override
    public List<Product> findProductWithCondition(String typeName, String productName, Integer startIndex, Integer pageSize) {
        return productMapper.findProductWithCondition(typeName, productName, startIndex, pageSize);
    }

    @Override
    public int countAllProduct() {
        return productMapper.countAllProduct();
    }

    @Override
    public int insertProduct(Product product) {
        return productMapper.insertProduct(product);
    }

    @Override
    public List<Product> findProductByProductName(String productName) {
        return productMapper.findProductByProductName(productName);
    }

    @Override
    public List<Product> findProductByUserId(int userId) {
        return productMapper.findProductByUserId(userId);
    }

    @Override
    public Product findProductByProductId(int productId) {
        return productMapper.findProductByProductId(productId);
    }

    @Override
    public List<ProductType> findAllType() {
        return productMapper.findAllType();
    }

    @Override
    public ProductType findTypeNameByTypeId(int typeId) {
        return productMapper.findTypeNameByTypeId(typeId);
    }

    @Override
    public List<ProductType> findTypeByTypeName(String typeName) {
        return productMapper.findTypeByTypeName(typeName);
    }

    @Override
    public List<Quality> findAllQuality() {
        return productMapper.findAllQuality();
    }

    @Override
    public List<Trade> findAllTrade() {
        return productMapper.findAllTrade();
    }

    @Override
    public int updateProductNumber(int cartNumber, String productStatus, int productId) {
        return productMapper.updateProductNumber(cartNumber, productStatus, productId);
    }

    @Override
    public int updateProductInfo(Product product) {
        return productMapper.updateProductInfo(product);
    }

    @Override
    public List<Product> findRandProduct(int[] typeIds, int count) {
        return productMapper.findRandProduct(typeIds, count);
    }

    @Override
    public Object uploadPicture(MultipartFile uploadFile, String filePath) {
        //1、给上传的图片生成新的文件名
        //1.1获取原始文件名
        String oldName = uploadFile.getOriginalFilename();
        //1.2使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
        String newName = IDUtils.genImageName();
        assert oldName != null;
        newName = newName + oldName.substring(oldName.lastIndexOf("."));
        //1.3生成文件在服务器端存储的子目录
//        String filePath = fileType+ "/" + IDUtils.genImageName();

        //2、把图片上传到图片服务器
        //2.1获取上传的io流
        InputStream input = null;
        try {
            input = uploadFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //2.2调用FtpUtil工具类进行上传
        return FtpUtil.putImages(input, filePath, newName);
    }

    @Override
    public void detelePicture(String filePath) {
        FtpUtil.delImages(filePath);
    }

}
