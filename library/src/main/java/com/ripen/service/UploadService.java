package com.ripen.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 资源上传服务
 *
 * @author Ripen.Y
 * @version 2021/01/31 13:13
 * @since 2021/01/31
 */
public interface UploadService {

    /**
     * 上传资源
     *
     * @param type 类型，1：用户，2：书籍
     * @param uploadFile 文件
     * @param filePath 文件夹名
     * @return
     */
    String upload (int type, MultipartFile uploadFile, String filePath);

}
