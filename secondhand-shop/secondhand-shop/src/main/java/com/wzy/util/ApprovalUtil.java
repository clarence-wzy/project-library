package com.wzy.util;

import com.baidu.aip.contentcensor.AipContentCensor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Package: com.wzy.util
 * @Author: Clarence1
 * @Date: 2019/10/11 9:18
 */
@Slf4j
public class ApprovalUtil {
    static final String APP_ID = "xxx";
    static final String API_KEY = "xxx";
    static final String SECRET_KEY = "xxx";

    static final AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);

    public static String pictureApprovalUtil(MultipartFile file) throws Exception {
        // 设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 参数为二进制数组
        byte[] buf = file.getBytes();
        JSONObject res = client.imageCensorUserDefined(buf, null);
//        log.info(res.toString(2));

        Map map = JsonChange.json2map(res.toString());
        return (String) map.get("conclusion");
    }

}
