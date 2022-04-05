package com.tom.test;

import com.tom.utils.MD5Util;
import org.junit.Test;

public class TestMD5 {

    @Test
    public void testMD5(){
        String mi = MD5Util.getMD5("000000");
        System.out.println(mi);
    }

}
