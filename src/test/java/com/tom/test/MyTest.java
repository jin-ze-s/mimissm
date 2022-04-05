package com.tom.test;

import com.tom.mapper.ProductInfoMapper;
import com.tom.pojo.ProductInfo;
import com.tom.pojo.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class MyTest {

    @Autowired
    ProductInfoMapper mapper;

    @Test
    public void testSelectCondition(){
        ProductInfoVo vo = new ProductInfoVo();
        vo.setPname("4");
        vo.setTypeid(3);
        vo.setLprice(2000);
        vo.setHprice(3000);
        List<ProductInfo> infos = mapper.selectCondition(vo);
        if (infos != null){
            infos.forEach(System.out::println);
        }
    }

}
