package com.home.miniMall.service;

import com.github.pagehelper.PageInfo;
import com.home.miniMall.pojo.ProductInfo;
import com.home.miniMall.utils.MyException;

import java.util.List;

public interface ProductService {
    List<ProductInfo> getAll();
    PageInfo<ProductInfo> split(Integer pageNum,Integer pageSize,ProductInfo productInfo,Integer lprice,Integer hprice);
    int save(ProductInfo productInfo);
    ProductInfo getById(ProductInfo productInfo);
    int update(ProductInfo productInfo);
    String delete(ProductInfo productInfo) throws Exception;
    String deletebatch(int[] id) throws Exception;
}
