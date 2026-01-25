package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.service.ActivityAppService;
import com.csxuhuan.gelatoni.domain.model.entity.ActivityBlock;
import com.csxuhuan.gelatoni.domain.query.ActivityBlockCreateOrUpdateQuery;
import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.application.assembler.ActivityBlockAssembler;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.PermissionConstants;
import com.csxuhuan.gelatoni.interfaces.web.common.ResultCode;
import com.csxuhuan.gelatoni.interfaces.web.common.UserHolder;
import com.csxuhuan.gelatoni.application.dto.ActivityBlockDTO;
import com.csxuhuan.gelatoni.interfaces.web.request.ActivityBlockCreateOrUpdateRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * 活动记录控制器
 *
 * <p>提供活动记录相关的 RESTful API 接口，包括：
 * <ul>
 *     <li>查询某一天的活动记录</li>
 *     <li>创建或更新活动记录（需要认证，幂等）</li>
 *     <li>删除活动记录（需要认证）</li>
 * </ul>
 *
 * <p>接口路径前缀：/api/activity/block
 *
 * @author csxuhuan
 * @see ActivityAppService
 */
@RestController
@RequestMapping("/api/activity/block")
public class ActivityBlockController {

    private final ActivityAppService activityAppService;
    private final ActivityBlockAssembler assembler = new ActivityBlockAssembler();

    /**
     * 构造函数，注入应用服务
     *
     * @param activityAppService 活动应用服务
     */
    public ActivityBlockController(ActivityAppService activityAppService) {
        this.activityAppService = activityAppService;
    }

    /**
     * 查询某一天的活动记录
     *
     * <p>返回指定日期的所有未删除的活动记录，按开始时间升序排列。
     * 此接口需要 {@link PermissionConstants#PERM_ACTIVITY} 权限。
     *
     * @param date 活动日期，格式：yyyy-MM-dd
     * @return 活动记录列表
     * @see AuthCheck 权限检查注解
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_ACTIVITY)
    @GetMapping(value = "/listByDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<ActivityBlockDTO>> listByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Long userId = UserHolder.getUserId();
        List<ActivityBlock> blocks = activityAppService.findBlocksByDate(userId, date);
        List<ActivityBlockDTO> dtoList = assembler.toDTOList(blocks);
        return BaseResponse.success(dtoList);
    }

    /**
     * 创建或更新活动记录（幂等）
     *
     * <p>幂等规则：以 (user_id, date, startTime) 作为幂等键。
     * 如果存在则更新，不存在则创建。
     * 此接口需要 {@link PermissionConstants#PERM_ACTIVITY} 权限。
     *
     * @param request 创建或更新请求
     * @return 创建或更新结果，返回影响的行数
     * @see AuthCheck 权限检查注解
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_ACTIVITY)
    @PostMapping(value = "/createOrUpdate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> createOrUpdate(@Valid @RequestBody ActivityBlockCreateOrUpdateRequest request) {
        Long userId = UserHolder.getUserId();
        if (userId == null) {
            return BaseResponse.error(ResultCode.UNAUTHORIZED, "用户信息不存在，请重新登录");
        }
        ActivityBlockCreateOrUpdateQuery query = assembler.toDomainQuery(request);
        int result = activityAppService.createOrUpdateBlock(query, userId);
        return BaseResponse.success(result);
    }

    /**
     * 删除活动记录
     *
     * <p>删除指定的活动记录（软删除）。此接口需要 {@link PermissionConstants#PERM_ACTIVITY} 权限。
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
        int result = activityAppService.deleteBlock(request.getId(), userId);
        return BaseResponse.success(result);
    }
}
