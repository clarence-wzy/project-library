package com.wzy.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;


/**
 * @Package: com.wzy.util
 * @Author: Clarence1
 * @Date: 2020/2/14 23:08
 */
public class SiseTest {

    public static void main(String[] args) throws IOException {
        Document document = Jsoup.parse(new URL("http://class.sise.com.cn:7001/sise/login.jsp"), 10000);
        Elements elements = document.getElementsByTag("input");
        System.out.println(elements.get(0).attr("name"));
        System.out.println(elements.get(0).attr("value"));
    }

}
