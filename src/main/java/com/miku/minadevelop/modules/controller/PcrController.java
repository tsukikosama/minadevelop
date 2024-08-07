package com.miku.minadevelop.modules.controller;

import cn.hutool.core.bean.BeanUtil;
import com.miku.minadevelop.modules.pcrUtils.api.PcrApi;
import com.miku.minadevelop.modules.pcrUtils.config.pcrConfig;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pcr")  // 接口前缀：/pcr
public class PcrController {

    private final PcrApi pcrApi;

    private final pcrConfig config;
    @GetMapping("/getPcrRank")
    public String getPcrRank() {
        System.out.println(config.toString());
        Map<String,Object> map = new HashMap<>();
        map.put("name",config.getName());
        map.put("ts",config.getTs());
        map.put("nonce",config.getNonce());
        map.put("appkey",config.getAppkey());
        map.put("sign",config.getSign());
        String rank = pcrApi.getRank(map);
        return rank;
    }
    @PostMapping("/updateCookie")
    public String UpDetaCookie(@RequestBody String cookie) {
        String[] split = cookie.split(":");
        System.out.println(split[1]);
        config.setCookie(split[1]);

        return "更新cookie成功";
    }

    @PostMapping("/updateParam")
    public String updateParam(@RequestBody String param) {
        String[] split = param.split(":");
        Map<String,String> map = new HashMap<>();
        for (int i = 1 ; i < split.length ;i++){
            String[] split1 = split[i].split(":");
            map.put(split1[i], split1[i]);
        }
        config.setTs(map.get("ts"));
        config.setName(map.get("name"));
        config.setNonce(map.get("nonce"));
        config.setAppkey(map.get("appkey"));
        config.setSign(map.get("sign"));
        System.out.println(config);
        return "更新param成功";
    }

}
