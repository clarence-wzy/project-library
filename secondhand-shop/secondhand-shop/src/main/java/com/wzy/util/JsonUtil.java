package com.wzy.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package: com.wzy.util
 * @Author: Clarence1
 * @Date: 2020/2/9 20:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonUtil {

    private int code;

    private String msg;

    private int count;

    private Object data;

    public JsonUtil(int count, Object data) {
        this.code = 0;
        this.msg = "OK";
        this.count = count;
        this.data = data;
    }

    public static JsonUtil ok(int count, Object data) {
        return new JsonUtil(count, data);
    }

}
