package com.xiechur.spider.api.controller;

import com.xiechur.spider.model.Singer;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiechurong
 * @Date 2018/8/30
 */
public class Demo {
    public static String doGet(String url) {
        System.out.println("doget:" + url);
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try {
            httpClient = HttpClients.createDefault();
            httpGet = new HttpGet(url);
            //设置请求头
            httpGet.setHeader("Host", "music.163.com");
            httpGet.setHeader("Referer", "https://music.163.com/");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse != null) {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    result = EntityUtils.toString(httpEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }




}
