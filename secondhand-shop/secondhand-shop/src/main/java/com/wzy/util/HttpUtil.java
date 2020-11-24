package com.wzy.util;

import javax.servlet.http.HttpServletResponse;

/**
 * @Package: com.wzy.util
 * @Author: Clarence1
 * @Date: 2019/9/25 15:03
 */
public class HttpUtil {

    public static void alterResponse(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        //跨域请求，*代表允许全部类型
        response.setHeader("Access-Control-Allow-Origin", "*");
        //允许请求方式
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        //用来指定本次预检请求的有效期，单位为秒，在此期间不用发出另一条预检请求
        response.setHeader("Access-Control-Max-Age", "3600");
        //请求包含的字段内容，如有多个可用哪个逗号分隔如下
        response.setHeader("Access-Control-Allow-Headers", "content-type, x-requested-with, Authorization, x-ui-request,lang");
        //访问控制允许凭据，true为允许
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

}
