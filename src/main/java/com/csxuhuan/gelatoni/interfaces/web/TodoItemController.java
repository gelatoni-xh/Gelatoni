package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.service.TodoAppService;
import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.domain.query.TodoItemCreateQuery;
import com.csxuhuan.gelatoni.domain.query.TodoItemUpdateQuery;
import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.application.assembler.TodoItemAssembler;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.PermissionConstants;
import com.csxuhuan.gelatoni.interfaces.web.common.UserHolder;
import com.csxuhuan.gelatoni.application.dto.TodoItemDTO;
import com.csxuhuan.gelatoni.interfaces.web.request.TodoItemCreateRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.TodoItemUpdateRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TODO项控制器
 *
 * <p>提供 TODO 待办事项相关的 RESTful API 接口，包括：
 * <ul>
 *     <li>查询所有 TODO 项</li>
 *     <li>按标签筛选 TODO 项</li>
 *     <li>创建新 TODO 项（需要认证）</li>
 *     <li>更新 TODO 项（需要认证）</li>
 * </ul>
 *
 * <p>接口路径前缀：/api/todo/item
 *
 * @author csxuhuan
 * @see TodoAppService
 */
@RestController
@RequestMapping("/api/todo/item")
public class TodoItemController {

    private final TodoAppService todoAppService;
    private final TodoItemAssembler assembler = new TodoItemAssembler();

    /**
     * 构造函数，注入依赖服务
     *
     * @param todoAppService TODO应用服务
     */
    public TodoItemController(TodoAppService todoAppService) {
        this.todoAppService = todoAppService;
    }

    /**
     * 查询所有 TODO 项
     *
     * <p>返回所有未删除的 TODO 项列表，按创建时间倒序排列。
     * 每个 TODO 项会自动关联对应的标签名称。
     * 此接口需要 {@link PermissionConstants#PERM_TODO} 权限。
     *
     * @return TODO项列表，包含完整的 TODO 信息和关联的标签名称
     * @see AuthCheck 权限检查注解
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_TODO)
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<TodoItemDTO>> list() {
        Long userId = UserHolder.getUserId();
        if (userId == null) {
            return BaseResponse.success(Collections.emptyList());
        }
        List<TodoItem> items = todoAppService.findAllItems(userId);

        // 获取标签映射，填充标签名称
        Map<Long, TodoTag> tagMap = getTagMap(userId);

        List<TodoItemDTO> dtoList = assembler.toDTOList(items, tagMap);
        return BaseResponse.success(dtoList);
    }

    /**
     * 按标签筛选 TODO 项
     *
     * <p>根据指定的标签 ID 筛选 TODO 项，返回该标签下所有未删除的 TODO 项，
     * 按创建时间倒序排列。此接口需要 {@link PermissionConstants#PERM_TODO} 权限。
     *
     * @param tagId 标签 ID，用于筛选 TODO 项
     * @return 该标签下的 TODO 项列表
     * @see AuthCheck 权限检查注解
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_TODO)
    @GetMapping(value = "/listByTag", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<TodoItemDTO>> listByTag(@RequestParam("tagId") Long tagId) {
        Long userId = UserHolder.getUserId();
        List<TodoItem> items = todoAppService.findItemsByTagId(tagId, userId);

        // 获取标签映射，填充标签名称
        Map<Long, TodoTag> tagMap = getTagMap(userId);

        List<TodoItemDTO> dtoList = assembler.toDTOList(items, tagMap);
        return BaseResponse.success(dtoList);
    }

    /**
     * 创建 TODO 项
     *
     * <p>创建一条新的 TODO 待办事项。此接口需要 {@link PermissionConstants#PERM_TODO} 权限。
     * 新创建的 TODO 项默认为未完成状态。
     *
     * @param request 创建请求，包含 content（内容）和可选的 tagId（标签ID）
     * @return 创建结果，返回影响的行数
     * @see AuthCheck 权限检查注解
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_TODO)
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> create(@Valid @RequestBody TodoItemCreateRequest request) {
        Long userId = UserHolder.getUserId();
        TodoItemCreateQuery query = assembler.toDomainQuery(request);
        int result = todoAppService.createItem(query, userId);
        return BaseResponse.success(result);
    }

    /**
     * 更新 TODO 项
     *
     * <p>更新指定的 TODO 项信息。此接口需要 {@link PermissionConstants#PERM_TODO} 权限。
     * 支持部分更新，只更新请求中提供的字段。
     *
     * @param request 更新请求，包含 id（必填）以及可选的 content、completed、tagId
     * @return 更新结果，返回影响的行数
     * @see AuthCheck 权限检查注解
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_TODO)
    @PostMapping(value = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> update(@Valid @RequestBody TodoItemUpdateRequest request) {
        Long userId = UserHolder.getUserId();
        TodoItemUpdateQuery query = assembler.toDomainQuery(request);
        int result = todoAppService.updateItem(query, userId);
        return BaseResponse.success(result);
    }

    /**
     * 构建标签 ID 到标签对象的映射
     *
     * <p>查询所有标签并构建映射表，用于在返回 TODO 项时填充标签名称。
     * 避免了 N+1 查询问题。
     *
     * @param userId 用户ID，用于筛选标签
     * @return Map&lt;标签ID, 标签对象&gt;
     */
    private Map<Long, TodoTag> getTagMap(Long userId) {
        if (userId == null) {
            return Collections.emptyMap();
        }
        List<TodoTag> tags = todoAppService.findAllTags(userId);
        return tags.stream()
                .collect(Collectors.toMap(TodoTag::getId, Function.identity()));
    }
}
