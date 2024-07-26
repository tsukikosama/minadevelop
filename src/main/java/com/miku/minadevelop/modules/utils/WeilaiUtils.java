package com.miku.minadevelop.modules.utils;

import cn.hutool.Hutool;
import cn.hutool.core.util.IdUtil;

/**
 * 项目中需要使用的一些工具类
 */

public class WeilaiUtils {

    public static String getMessageId(){
        return IdUtil.simpleUUID();
    }


    public static Long generateId(){
        return IdUtil.getSnowflakeNextId();
    }
}
