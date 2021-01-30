package com.ripen.controller;

import com.ripen.dao.entity.*;
import com.ripen.service.BkBasicService;
import com.ripen.util.JsonResult;
import com.ripen.util.Page;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 书籍控制层
 *
 * @author Ripen.Y
 * @version 2021/01/17 0:57
 * @since 2021/01/17
 */
@RestController
@RequestMapping(value = "/book")
@Api(value = "书籍控制层")
public class BkController {
    public final Logger LOGGER = LoggerFactory.getLogger (this.getClass ());

    @Autowired
    private BkBasicService bkBasicService;

    /**
     * 新增书籍信息
     *
     * @param admId 管理员id
     * @param bkInfo 书籍信息
     * @return
     */
    @ApiOperation(value = "新增书籍信息")
    @PostMapping(value = "/add/{adm_id}")
    public JsonResult addBook (@PathVariable("adm_id") String admId, @RequestBody BkInfo bkInfo)
    {
        if (StringUtil.isNullOrEmpty(admId) || bkInfo == null
                || StringUtil.isNullOrEmpty(bkInfo.getBkName()) || StringUtil.isNullOrEmpty(bkInfo.getBkAuthor())
                || StringUtil.isNullOrEmpty(bkInfo.getBkPress()) || bkInfo.getBkPrice() == null
                || bkInfo.getBNum() <= 0 || bkInfo.getBkTypeList() == null || bkInfo.getBkTypeList().size() == 0)
        {
            return JsonResult.errorMsg("参数错误");
        }

        int rt = bkBasicService.addBook(bkInfo, admId, bkInfo.getBNum());
        if (rt != 0) {
            return JsonResult.errorMsg("新增书籍失败");
        }
        return JsonResult.ok("新增书籍成功");
    }

    /**
     * 修改书籍信息
     *
     * @param bkInfo 书籍信息
     * @return
     */
    @ApiOperation(value = "修改书籍信息")
    @PostMapping(value = "/modify/{bk_id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult modifyBook (@PathVariable("bk_id") String bkId, @RequestBody BkInfo bkInfo)
    {
        int rt = bkBasicService.modifyTypeInfo(bkId, bkInfo.getAddTypeList(), bkInfo.getDelTypeList());
        if (rt == 0) {
            rt = bkBasicService.modifyInfo(bkId, bkInfo);
        }

        if (rt == 0) {
            return JsonResult.ok("修改成功");
        } else if (rt == -2) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonResult.errorMsg("参数错误");
        } else {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonResult.errorMsg("系统异常");
        }
    }

    /**
     * 删除书籍信息
     *
     * @param bkId 书籍信息id
     * @return
     */
    @ApiOperation(value = "删除书籍信息")
    @DeleteMapping(value = "/delete/{bkId}")
    public JsonResult delBook (@PathVariable("bkId") String bkId)
    {
        BkInfo bkInfo = new BkInfo();
        bkInfo.setBkInfoStatus(1);
        int rt = bkBasicService.modifyInfo(bkId, bkInfo);
        if (rt == 0) {
            return JsonResult.ok("删除成功");
        } else if (rt == -2) {
            return JsonResult.errorMsg("参数错误");
        } else {
            return JsonResult.errorMsg("系统异常");
        }
    }

    /**
     * 获取书籍数据
     *
     * @param selectType 查询类型，1：书籍类型，2：书籍信息，3：书籍详情
     * @param subType 子类型
     * @param status 状态
     * @param bkId 书籍信息id
     * @param btId 书籍类型id
     * @param serId 书籍编号
     * @param page 分页信息
     * @return
     */
    @ApiOperation(value = "获取书籍数据")
    @GetMapping(value = "/get/{select_type}")
    public JsonResult getBook (@PathVariable("select_type") int selectType,
                               int subType, Integer status, String bkId, Integer btId, String serId,
                               Page page)
    {
        JsonResult result = new JsonResult();
        switch (selectType) {
            case 1 :
                if (subType != 0 && subType != 1) {
                    result.setOk("fail");
                    result.setStatus(500);
                    result.setData("selectType为1时，subType必须是0或1");
                    break;
                }
                List<BkType> bkTypeList = bkBasicService.getBkType(btId, status, subType);
                result.setOk("success");
                result.setStatus(200);
                result.setData(bkTypeList);
                break;
            case 2 :
                if (subType != 0 && subType != 1) {
                    result.setOk("fail");
                    result.setStatus(500);
                    result.setData("selectType为2时，subType必须是0或1");
                    break;
                }
                List<BkInfo> bkInfoList = bkBasicService.getBkInfo(bkId, btId, status, subType, page);
                result.setOk("success");
                result.setStatus(200);
                result.setData(bkInfoList);
                break;
            case 3 :
                if (subType != 0 && subType != 1 && subType != 2 && subType != 3) {
                    result.setOk("fail");
                    result.setStatus(500);
                    result.setData("selectType为3时，subType必须是0或1或2或3");
                    break;
                }
                List<BkDetail> bkDetailList = bkBasicService.getBkDetail(bkId, serId, subType, page);
                result.setOk("success");
                result.setStatus(200);
                result.setData(bkDetailList);
                break;
            default :
                result.setOk("fail");
                result.setStatus(500);
                result.setData("查询类型selectType必须是1或2或3");
        }
        return result;
    }

    /**
     * 新增书籍类型
     *
     * @param tName 类型名称
     * @return
     */
    @ApiOperation(value = "新增书籍类型")
    @PostMapping(value = "/type/add")
    public JsonResult addBkType (@RequestParam("tName") String tName) {
        if (StringUtil.isNullOrEmpty(tName)) {
            return JsonResult.errorMsg("参数错误");
        }
        BkType bkType = new BkType();
        bkType.setTName(tName);
        int rt = bkBasicService.addBkType(bkType);
        if (rt == -1) {
            return JsonResult.errorMsg("系统异常");
        }
        if (rt == -2) {
            return JsonResult.errorMsg("参数错误");
        }
        return JsonResult.ok("新增类型成功");
    }

    /**
     * 修改书籍类型
     *
     * @param bkType 书籍类型
     * @return
     */
    @ApiOperation(value = "修改书籍类型")
    @PostMapping(value = "/modify/type/{bt_id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult modifyBookType (@PathVariable("bt_id") Integer btId, @RequestBody BkType bkType)
    {

        int rt = bkBasicService.modifyType(btId, bkType);
        if (rt == 0) {
            return JsonResult.ok("修改成功");
        } else if (rt == -2) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonResult.errorMsg("参数错误");
        } else {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonResult.errorMsg("系统异常");
        }
    }

    /**
     * 删除书籍类型
     *
     * @param btId 书籍类型ID
     * @return
     */
    @ApiOperation(value = "删除书籍类型")
    @DeleteMapping(value = "/delete/type/{bt_Id}")
    public JsonResult delBookType (@PathVariable("bt_Id") Integer btId)
    {
        BkType bkType = new BkType();
        bkType.setBtStatus(1);
        int rt = bkBasicService.modifyType(btId, bkType);
        if (rt == 0) {
            return JsonResult.ok("删除成功");
        } else if (rt == -2) {
            return JsonResult.errorMsg("参数错误");
        } else {
            return JsonResult.errorMsg("系统异常");
        }
    }

}
