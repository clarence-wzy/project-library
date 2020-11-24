package com.wzz.service.impl;

import com.wzz.entity.Invitation;
import com.wzz.mapper.InvitationMapper;
import com.wzz.service.InvitationService;
import com.wzz.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Clarence1
 */
@Service
public class InvitationServiceImpl implements InvitationService {

    @Autowired
    private InvitationMapper invitationMapper;

    @Override
    public Map<Integer, Map<Integer, Object>> addInvitationInfo(MultipartFile file) {
        Map<Integer, Map<Integer,Object>> map = new HashMap<Integer, Map<Integer,Object>>(5);
        try {
            map = ExcelUtil.readExcelContentz(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //excel数据存在map里，map.get(0).get(0)为excel第1行第1列的值，此处可对数据进行处理
        return map;
    }

    @Override
    public int insertInvitationInfo(Invitation invitation) {
        return invitationMapper.insertInvitationInfo(invitation);
    }

    @Override
    public List<Invitation> findByQuestionnaireId(int questionnaireId) {
        return invitationMapper.findByQuestionnaireId(questionnaireId);
    }

    @Override
    public int findByUrl(String url) {
        return invitationMapper.findByUrl(url);
    }

    @Override
    public Invitation findRecycleStatusByUrl(String url) {
        return invitationMapper.findRecycleStatusByUrl(url);
    }

    @Override
    public int recycleTotal(int questionnaireId) {
        return invitationMapper.recycleTotal(questionnaireId);
    }

    @Override
    public int emailSuccessTotal(int questionnaireId) {
        return invitationMapper.emailSuccessTotal(questionnaireId);
    }

    @Override
    public int updateRecycleStatus(String url) {
        url = "%/" + url;
        return invitationMapper.updateRecycleStatus(url);
    }

}
