package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.service.TodoAppService;
import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.domain.query.TodoItemCreateQuery;
import com.csxuhuan.gelatoni.domain.query.TodoItemUpdateQuery;
import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.application.assembler.TodoItemAssembler;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.application.dto.TodoItemDTO;
import com.csxuhuan.gelatoni.interfaces.web.request.TodoItemCreateRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.TodoItemUpdateRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
     * 此接口不需要认证。
     *
     * @return TODO项列表，包含完整的 TODO 信息和关联的标签名称
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<TodoItemDTO>> list() {
        List<TodoItem> items = todoAppService.findAllItems();

        // 获取标签映射，填充标签名称
        Map<Long, TodoTag> tagMap = getTagMap();

        List<TodoItemDTO> dtoList = assembler.toDTOList(items, tagMap);
        return BaseResponse.success(dtoList);
    }

    /**
     * 按标签筛选 TODO 项
     *
     * <p>根据指定的标签 ID 筛选 TODO 项，返回该标签下所有未删除的 TODO 项，
     * 按创建时间倒序排列。此接口不需要认证。
     *
     * @param tagId 标签 ID，用于筛选 TODO 项
     * @return 该标签下的 TODO 项列表
     */
    @GetMapping(value = "/listByTag", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<TodoItemDTO>> listByTag(@RequestParam("tagId") Long tagId) {
        List<TodoItem> items = todoAppService.findItemsByTagId(tagId);

        // 获取标签映射，填充标签名称
        Map<Long, TodoTag> tagMap = getTagMap();

        List<TodoItemDTO> dtoList = assembler.toDTOList(items, tagMap);
        return BaseResponse.success(dtoList);
    }

    /**
     * 创建 TODO 项
     *
     * <p>创建一条新的 TODO 待办事项。此接口需要认证（Bearer Token），
     * 仅授权用户可以调用。新创建的 TODO 项默认为未完成状态。
     *
     * @param request 创建请求，包含 content（内容）和可选的 tagId（标签ID）
     * @return 创建结果，返回影响的行数
     * @see AuthCheck 认证注解
     */
    @AuthCheck
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> create(@Valid @RequestBody TodoItemCreateRequest request) {
        TodoItemCreateQuery query = assembler.toDomainQuery(request);
        int result = todoAppService.createItem(query);
        return BaseResponse.success(result);
    }

    /**
     * 更新 TODO 项
     *
     * <p>更新指定的 TODO 项信息。此接口需要认证（Bearer Token），
     * 仅授权用户可以调用。支持部分更新，只更新请求中提供的字段。
     *
     * @param request 更新请求，包含 id（必填）以及可选的 content、completed、tagId
     * @return 更新结果，返回影响的行数
     * @see AuthCheck 认证注解
     */
    @AuthCheck
    @PostMapping(value = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> update(@Valid @RequestBody TodoItemUpdateRequest request) {
        TodoItemUpdateQuery query = assembler.toDomainQuery(request);
        int result = todoAppService.updateItem(query);
        return BaseResponse.success(result);
    }

    /**
     * 构建标签 ID 到标签对象的映射
     *
     * <p>查询所有标签并构建映射表，用于在返回 TODO 项时填充标签名称。
     * 避免了 N+1 查询问题。
     *
     * @return Map&lt;标签ID, 标签对象&gt;
     */
    private Map<Long, TodoTag> getTagMap() {
        List<TodoTag> tags = todoAppService.findAllTags();
        return tags.stream()
                .collect(Collectors.toMap(TodoTag::getId, Function.identity()));
    }
}
