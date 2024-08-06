package com.miku.minadevelop.modules.pcrUtils.api;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.google.gson.Gson;
import com.miku.minadevelop.modules.pcrUtils.BaseResp;
import com.miku.minadevelop.modules.pcrUtils.config.pcrConfig;
import com.miku.minadevelop.modules.pcrUtils.entity.Rank;

import org.openqa.selenium.json.TypeToken;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.util.*;

@Configuration
public class PcrApi {

    private static final pcrConfig pcrConfig = new pcrConfig();

    public static String getRank(Map<String, Object> map) {

        HttpResponse res = HttpRequest.get("https://api.game.bilibili.com/game/player/tools/pcr/search_clan")
                .form(map)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36 Edg/127.0.0.0")
                .header("Connection", "keep-alive")
                .header("Cookie", "SESSDATA=319e5dce%2C1738400231%2C56351%2A81CjAnUuFMIECEgHodX-upHeHVz89LnN0pSNSrSwaKuFdqz72JazQXoSvZd9cMT4oZrw8SVlhwT0pwam9yUE8xd0FUOTllNC1BWVNUeUlEYVMzY3ExS1B2dHRTTEllUEcyUW5lQVllcndvWFJydDE2Z2h2aW00VEY5X09SZUhIN0wtaGd5bVhtODV3IIEC")
                .header("Bili-Status-Code", "0")
                .header("X-Bili-Trace-Id", "1b72650df6ab5d64211db1174066b185")
                .header("X-Ticket-Status", "1")
                .header("X-Cache-Webcdn", "BYPASS from blzone01")
                .header("Content-Encoding", "br")
                .execute();
        System.out.println(res.body());

        BaseResp o = new Gson().fromJson(res.body(), BaseResp.class);
//        String data = o.getData();.
        System.out.println(o.getData());
        if (o.getData() == null){
            return "查询对象为空,稍后再试";
        }
        System.out.println(o.getData());
        StringBuilder sb = new StringBuilder();
        if (o.getData().toString().split(",").length > 0){
            Type listType = new TypeToken<List<Rank>>(){}.getType();
            List<Rank> rank = new Gson().fromJson(o.getData().toString(), listType);
            rank.stream().forEach(item -> {
                sb.append("\n" +
                                "公会名：" + item.getClan_name()+"\n")
                        .append("会长名:"+item.getLeader_name()+"\n")
                        .append("公会排名:"+item.getRank()+"\n")
                        .append("总伤害:"+item.getDamage()+"\n\n");
            });
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<String,Object>();
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
        map.put("name","咖啡馆");
        map.put("ts","1722927650330");
        map.put("nonce","50f58840-e86b-4ce0-9071-bc2e974e5da6");
        map.put("appkey","f07288b7ef7645c7a3997baf3d208b62");
        map.put("sign","d7e2b613abecf64ce12f48ba704f5198");
//        System.out.println(map);
//        String rank = getRank(map);

//        System.out.println(rank);
    }
}
