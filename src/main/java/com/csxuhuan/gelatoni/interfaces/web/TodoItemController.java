package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.service.TodoItemAppService;
import com.csxuhuan.gelatoni.application.service.TodoTagAppService;
import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.domain.query.TodoItemCreateQuery;
import com.csxuhuan.gelatoni.domain.query.TodoItemUpdateQuery;
import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.interfaces.web.assembler.TodoItemAssembler;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.dto.TodoItemDTO;
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
 * @see TodoItemAppService
 * @see TodoTagAppService
 */
@RestController
@RequestMapping("/api/todo/item")
public class TodoItemController {

    private final TodoItemAppService todoItemAppService;
    private final TodoTagAppService todoTagAppService;
    private final TodoItemAssembler assembler = new TodoItemAssembler();

    /**
     * 构造函数，注入依赖服务
     *
     * @param todoItemAppService TODO项应用服务
     * @param todoTagAppService  标签应用服务（用于获取标签名称）
     */
    public TodoItemController(TodoItemAppService todoItemAppService, TodoTagAppService todoTagAppService) {
        this.todoItemAppService = todoItemAppService;
        this.todoTagAppService = todoTagAppService;
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
        List<TodoItem> items = todoItemAppService.findAll();

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
        List<TodoItem> items = todoItemAppService.findByTagId(tagId);

        // 获取标签映射，填充标签名称
        Map<Long, TodoTag> tagMap = getTagMap();

        List<TodoItemDTO> dtoList = assembler.toDTOList(items, tagMap);
        return BaseResponse.success(dtoList);
    }

    /**
     * 创建TODO项
     *
     * @param request 创建请求
     * @return 创建结果
     */
    @AuthCheck
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> create(@Valid @RequestBody TodoItemCreateRequest request) {
        TodoItemCreateQuery query = assembler.toDomainQuery(request);
        int result = todoItemAppService.create(query);
        return BaseResponse.success(result);
    }

    /**
     * 更新TODO项
     *
     * @param request 更新请求
     * @return 更新结果
     */
    @AuthCheck
    @PostMapping(value = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> update(@Valid @RequestBody TodoItemUpdateRequest request) {
        TodoItemUpdateQuery query = assembler.toDomainQuery(request);
        int result = todoItemAppService.update(query);
        return BaseResponse.success(result);
    }

    /**
     * 获取标签ID到标签的映射
     *
     * @return 标签映射
     */
    private Map<Long, TodoTag> getTagMap() {
        List<TodoTag> tags = todoTagAppService.findAll();
        return tags.stream()
                .collect(Collectors.toMap(TodoTag::getId, Function.identity()));
    }
}
