package com.miku.minadevelop;

import cn.hutool.core.util.RandomUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MinadevelopApplicationTests {

    @Test
    void contextLoads() {
        String s = RandomUtil.randomString(10);
        System.out.println(s);
    }

    @Test
    void test(){
        System.out.println(1^1);
    }


    @Test
    void test2(){
        Integer i = ObjectUtils.defaultIfNull(8787, 66);
        System.out.println(i);
    }

    @Test
    void test3(){
        String[] bytes = new String[1];
        System.out.println(bytes.length);
        String[] s = ArrayUtils.addFirst(bytes, "s");
        System.out.println(s.length);
    }
}
