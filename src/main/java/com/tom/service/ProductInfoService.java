package com.tom.service;

import com.github.pagehelper.PageInfo;
import com.tom.pojo.ProductInfo;
import com.tom.pojo.vo.ProductInfoVo;

import java.util.List;

/**
 * @author tom
 */
public interface ProductInfoService {

    //显示全部商品，不分页
    List<ProductInfo> getAll();

    PageInfo<ProductInfo> getPage(Integer pageNo);

    int save(ProductInfo info);

    ProductInfo getOne(Integer pid);

    int update(ProductInfo info);

    int delete(Integer pid);

    int deleteBatch(Integer[] pids);

    List<ProductInfo> selectCondition(ProductInfoVo vo);

    //多条件查询分页
    PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo);
}
