package com.home.miniMall.service;

import com.home.miniMall.mapper.ProductTypeMapper;
import com.home.miniMall.pojo.ProductInfo;
import com.home.miniMall.pojo.ProductType;
import com.home.miniMall.pojo.ProductTypeExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService{
    @Autowired
    private ProductTypeMapper mapper;

    @Override
    public List<ProductType> getAll() {
        return mapper.selectByExample(new ProductTypeExample());
    }
}
