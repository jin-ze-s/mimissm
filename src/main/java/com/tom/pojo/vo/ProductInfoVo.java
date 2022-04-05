package com.tom.pojo.vo;

import lombok.Data;

/**
 * @author tom
 */
@Data
public class ProductInfoVo {

    //商品名称
    private String pname;
    //商品类型
    private Integer typeid;
    //最低价格
    private Integer lprice;
    //最高价格
    private Integer hprice;
    //设置页码(初始值为1)
    private Integer page = 1;

    public ProductInfoVo() {
    }

    public ProductInfoVo(String pname, Integer typeid, Integer lprice, Integer hprice,Integer page) {
        this.pname = pname;
        this.typeid = typeid;
        this.lprice = lprice;
        this.hprice = hprice;
        this.page = page;
    }
}
