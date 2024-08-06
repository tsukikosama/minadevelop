package com.miku.minadevelop.modules.controller;

import com.miku.minadevelop.modules.pcrUtils.api.PcrApi;
import com.miku.minadevelop.modules.pcrUtils.config.pcrConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
