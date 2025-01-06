package com.miku.minadevelop.common.pixivApi;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class RandomImg {
    public static void main(String[] args) {
        try {
            testRandom();
        }catch (IOException e){
            System.out.println(e);
        }
    }



    public static void testRandom() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //2. 创建HttpGet请求，并进行相关设置
        HttpGet httpGet = new HttpGet("https://www.pixiv.net/artworks/125904543");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Mobile Safari/537.36 Edg/85.0.564.68");

        //3.发起请求
        CloseableHttpResponse response = httpClient.execute(httpGet);

        //4.判断响应状态码并获取响应数据
        if (response.getStatusLine().getStatusCode() == 200) { //200表示响应成功
            String html = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(html);
        }

        //5.关闭资源
        httpClient.close();
        response.close();

    }
}
