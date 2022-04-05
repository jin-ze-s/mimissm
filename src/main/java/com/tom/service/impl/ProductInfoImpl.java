package com.tom.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tom.mapper.ProductInfoMapper;
import com.tom.pojo.ProductInfo;
import com.tom.pojo.ProductInfoExample;
import com.tom.pojo.vo.ProductInfoVo;
import com.tom.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductInfoImpl implements ProductInfoService {

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {

        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo<ProductInfo> getPage(Integer pageNo) {
        PageHelper.startPage(pageNo,5);
        ProductInfoExample example = new ProductInfoExample();
        //p_id降序排序
        example.setOrderByClause("p_id desc");
        List<ProductInfo> infos = productInfoMapper.selectByExample(example);
        return new PageInfo<>(infos,4);
    }

    @Override
    public int save(ProductInfo info) {
        int num = productInfoMapper.insertSelective(info);
        return num;
    }

    @Override
    public ProductInfo getOne(Integer pid) {
        ProductInfo info = productInfoMapper.selectByPrimaryKey(pid);
        return info;
    }

    @Override
    public int update(ProductInfo info) {
        int i = productInfoMapper.updateByPrimaryKeySelective(info);
        return i;
    }

    @Override
    public int delete(Integer pid) {
        int num = productInfoMapper.deleteByPrimaryKey(pid);
        return num;
    }

    @Override
    public int deleteBatch(Integer[] pids) {
        ProductInfoExample example = new ProductInfoExample();
        example.createCriteria().andPIdIn(Arrays.asList(pids));
        int num = productInfoMapper.deleteByExample(example);
        return num;
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo) {
        //取出集合之前，先要设置PageHelper.startPage()属性
        PageHelper.startPage(vo.getPage(),5);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        return new PageInfo<>(list);
    }
}
