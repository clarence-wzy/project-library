package com.ripen.service.impl;

import com.ripen.service.UploadService;
import com.ripen.util.FtpUtil;
import com.ripen.util.SnowFlakeUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源上传服务实现类
 *
 * @author Ripen.Y
 * @version 2021/01/31 13:15
 * @since 2021/01/31
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Override
    public String upload (int type, MultipartFile uploadFile, String filePath)
    {
        if ((type != 1 && type != 2) || uploadFile.isEmpty() || filePath == null) {
            return "";
        }

        String oldName = uploadFile.getOriginalFilename();
        String newName;
        String fileType;
        switch (type) {
            case 1 :
                newName = "U" + SnowFlakeUtil.getId();
                fileType = "user";
                break;
            case 2 :
                newName = "B" + SnowFlakeUtil.getId();
                fileType = "book";
                break;
            default :
                newName = "other";
                fileType = "other";
        }
        assert oldName != null;
        newName = newName + oldName.substring(oldName.lastIndexOf("."));

        filePath = fileType + "/" + filePath + "/";

        InputStream input = null;
        try {
            input = uploadFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return FtpUtil.putImages(input, filePath, newName);
    }

}
