package com.miku.minadevelop;

import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MinadevelopApplicationTests {

    @Test
    void contextLoads() {
        String s = RandomUtil.randomString(10);
        System.out.println(s);
    }

}
