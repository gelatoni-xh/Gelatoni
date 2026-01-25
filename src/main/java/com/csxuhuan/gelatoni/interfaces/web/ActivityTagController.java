package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.service.ActivityAppService;
import com.csxuhuan.gelatoni.domain.model.entity.ActivityTag;
import com.csxuhuan.gelatoni.domain.query.ActivityTagCreateQuery;
import com.csxuhuan.gelatoni.domain.query.ActivityTagUpdateQuery;
import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.application.assembler.ActivityTagAssembler;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.PermissionConstants;
import com.csxuhuan.gelatoni.interfaces.web.common.UserHolder;
import com.csxuhuan.gelatoni.application.dto.ActivityTagDTO;
import com.csxuhuan.gelatoni.interfaces.web.request.ActivityTagCreateRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.ActivityTagUpdateRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 活动标签控制器
 *
 * <p>提供活动标签相关的 RESTful API 接口，包括：
 * <ul>
 *     <li>查询所有标签</li>
 *     <li>创建新标签（需要认证）</li>
 *     <li>更新标签（需要认证）</li>
 *     <li>删除标签（需要认证）</li>
 * </ul>
 *
 * <p>接口路径前缀：/api/activity/tag
 *
 * @author csxuhuan
 * @see ActivityAppService
 */
@RestController
@RequestMapping("/api/activity/tag")
public class ActivityTagController {

    private final ActivityAppService activityAppService;
    private final ActivityTagAssembler assembler = new ActivityTagAssembler();

    /**
     * 构造函数，注入应用服务
     *
     * @param activityAppService 活动应用服务
     */
    public ActivityTagController(ActivityAppService activityAppService) {
        this.activityAppService = activityAppService;
    }

    /**
     * 查询所有标签
     *
     * <p>返回所有未删除的标签列表，按创建时间倒序排列。
     * 此接口需要 {@link PermissionConstants#PERM_ACTIVITY} 权限。
     *
     * @return 标签列表
     * @see AuthCheck 权限检查注解
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_ACTIVITY)
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<ActivityTagDTO>> list() {
        Long userId = UserHolder.getUserId();
        List<ActivityTag> tags = activityAppService.findAllTags(userId);
        List<ActivityTagDTO> dtoList = assembler.toDTOList(tags);
        return BaseResponse.success(dtoList);
    }

    /**
     * 创建标签
     *
     * <p>创建一个新的活动标签。此接口需要 {@link PermissionConstants#PERM_ACTIVITY} 权限。
     *
     * @param request 创建请求，包含 name（标签名称）和 color（标签颜色）
     * @return 创建结果，返回影响的行数
     * @see AuthCheck 权限检查注解
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_ACTIVITY)
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> create(@Valid @RequestBody ActivityTagCreateRequest request) {
        Long userId = UserHolder.getUserId();
        ActivityTagCreateQuery query = assembler.toDomainQuery(request);
        int result = activityAppService.createTag(query, userId);
        return BaseResponse.success(result);
    }

    /**
     * 更新标签
     *
     * <p>更新指定的标签信息。此接口需要 {@link PermissionConstants#PERM_ACTIVITY} 权限。
     * 支持部分更新，只更新请求中提供的字段。
     *
     * @param request 更新请求，包含 id（必填）以及可选的 name、color
     * @return 更新结果，返回影响的行数
     * @see AuthCheck 权限检查注解
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_ACTIVITY)
    @PostMapping(value = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> update(@Valid @RequestBody ActivityTagUpdateRequest request) {
        Long userId = UserHolder.getUserId();
        ActivityTagUpdateQuery query = assembler.toDomainQuery(request);
        int result = activityAppService.updateTag(query, userId);
        return BaseResponse.success(result);
    }

    /**
     * 删除标签
     *
     * <p>删除指定的标签（软删除）。此接口需要 {@link PermissionConstants#PERM_ACTIVITY} 权限。
     *
     * @param request 删除请求，包含 id
     * @return 删除结果，返回影响的行数
     * @see AuthCheck 权限检查注解
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_ACTIVITY)
    @PostMapping(value = "/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> delete(@Valid @RequestBody com.csxuhuan.gelatoni.interfaces.web.request.ActivityDeleteRequest request) {
        Long userId = UserHolder.getUserId();
        int result = activityAppService.deleteTag(request.getId(), userId);
        return BaseResponse.success(result);
    }
}
