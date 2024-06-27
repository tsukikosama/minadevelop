package com.miku.minadevelop.modules.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 *用来生成和id相关的一个工具类
 */
public class IdUtils {

    public static Long randomId(Integer size){
        String s = DateUtil.current() + RandomUtil.randomNumbers(size);
        return Long.getLong(s);
    }
}
