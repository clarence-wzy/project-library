package com.wzz.service;

import com.wzz.entity.Invitation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author Clarence1
 */
public interface InvitationService {

    /**
     * 提取excel中数据
     * @param file
     * @return
     */
    public Map<Integer, Map<Integer, Object>> addInvitationInfo(MultipartFile file);

    /**
     * 将excel提取的信息存储进数据库
     * @param invitation
     * @return
     */
    public int insertInvitationInfo(Invitation invitation);

    /**
     * 根据问卷id查询全部数据
     * @param questionnaireId
     * @return
     */
    public List<Invitation> findByQuestionnaireId(int questionnaireId);

    /**
     * 查询url的记录数
     * @param url
     * @return
     */
    public int findByUrl(String url);

    /**
     * 根据url查询邀请人的RecycleStatus
     * @param url
     * @return
     */
    public Invitation findRecycleStatusByUrl(String url);

    /**
     * 根据问卷id统计回收总数
     * @param questionnaireId
     * @return
     */
    public int recycleTotal(int questionnaireId);

    /**
     * 根据问卷id查询邮件成功发送总数
     * @param questionnaireId
     * @return
     */
    public int emailSuccessTotal(int questionnaireId);

    /**
     * 根据url更新回收状态
     * @param url
     * @return
     */
    public int updateRecycleStatus(String url);

}
