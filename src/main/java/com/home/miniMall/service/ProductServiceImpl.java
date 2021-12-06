package com.home.miniMall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.home.miniMall.mapper.ProductInfoMapper;
import com.home.miniMall.pojo.AdminExample;
import com.home.miniMall.pojo.ProductInfo;
import com.home.miniMall.pojo.ProductInfoExample;
import com.home.miniMall.utils.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductInfoMapper mapper;

    @Override
    public List<ProductInfo> getAll() {
        return mapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo<ProductInfo> split(Integer pageNum,Integer pageSize,ProductInfo productInfo,Integer lprice,Integer hprice) {
        if (pageNum==null){
            pageNum=1;
        }

        //设置分页工具的pageNum,pageSize
        PageHelper.startPage(pageNum,pageSize);

        ProductInfoExample example=new ProductInfoExample();
        ProductInfoExample.Criteria criteria=example.createCriteria();
        if (productInfo!=null&&productInfo.getTypeId()!=null){
            if (productInfo.getpName()!=null&&!productInfo.getpName().equals("")){
                criteria.andPNameLike("%"+productInfo.getpName()+"%");
            }

            if (productInfo.getTypeId()!=-1){
                criteria.andTypeIdEqualTo(productInfo.getTypeId());
            }
        }

        if (lprice!=null||hprice!=null){
            if (lprice!=null&&hprice==null){
                criteria.andPPriceGreaterThanOrEqualTo(lprice);
            }else if (lprice == null){
                criteria.andPPriceLessThan(hprice);
            }else {
                criteria.andPPriceBetween(lprice,hprice);
            }
        }
        example.setOrderByClause("p_id desc");
        List<ProductInfo>productInfos=mapper.selectByExample(example);

        return new PageInfo<>(productInfos);
    }

    @Override
    public int save(ProductInfo productInfo) {
        return mapper.insert(productInfo);
    }

    @Override
    public ProductInfo getById(ProductInfo productInfo) {
        return mapper.selectByPrimaryKey(productInfo.getpId());
    }

    @Override
    public int update(ProductInfo productInfo) {
        return mapper.updateByPrimaryKey(productInfo);
    }

    @Override
    public String delete(ProductInfo productInfo) throws Exception {

        mapper.deleteByPrimaryKey(productInfo.getpId());
        return "删除成功";
    }

    @Override
    public String deletebatch(int[] id) throws Exception {

        for (int i : id) {
            mapper.deleteByPrimaryKey(i);
        }

        return "删除成功";
    }
}
