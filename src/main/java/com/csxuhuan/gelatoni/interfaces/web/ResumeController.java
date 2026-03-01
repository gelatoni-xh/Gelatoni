package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.service.ResumeAppService;
import com.csxuhuan.gelatoni.application.dto.ResumeDTO;
import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.PermissionConstants;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 简历控制器
 *
 * <p>提供简历相关的 RESTful API 接口，包括：
 * <ul>
 *     <li>查询简历列表（按版本号倒序）</li>
 * </ul>
 *
 * <p>接口路径前缀：/api/resume
 *
 * @author csxuhuan
 * @see ResumeAppService
 */
@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeAppService resumeAppService;

    /**
     * 构造函数，注入应用服务
     *
     * @param resumeAppService 简历应用服务
     */
    public ResumeController(ResumeAppService resumeAppService) {
        this.resumeAppService = resumeAppService;
    }

    /**
     * 查询简历列表
     *
     * <p>返回所有简历版本列表，按版本号倒序排列。
     * 此接口需要 {@link PermissionConstants#PERM_RESUME} 权限。
     *
     * @return 简历列表
     * @see AuthCheck 权限检查注解
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_RESUME)
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<ResumeDTO>> list() {
        List<ResumeDTO> resumes = resumeAppService.findAllResumes();
        return BaseResponse.success(resumes);
    }
}