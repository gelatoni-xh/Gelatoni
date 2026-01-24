package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.service.TodoTagAppService;
import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.domain.query.TodoTagCreateQuery;
import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.interfaces.web.assembler.TodoTagAssembler;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.dto.TodoTagDTO;
import com.csxuhuan.gelatoni.interfaces.web.request.TodoTagCreateRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO 标签控制器
 *
 * <p>提供 TODO 标签相关的 RESTful API 接口，包括：
 * <ul>
 *     <li>查询所有标签</li>
 *     <li>创建新标签（需要认证）</li>
 * </ul>
 *
 * <p>标签用于对 TODO 项进行分类管理，每个 TODO 项可以关联一个标签。
 *
 * <p>接口路径前缀：/api/todo/tag
 *
 * @author csxuhuan
 * @see TodoTagAppService
 */
@RestController
@RequestMapping("/api/todo/tag")
public class TodoTagController {

    private final TodoTagAppService todoTagAppService;
    private final TodoTagAssembler assembler = new TodoTagAssembler();

    /**
     * 构造函数，注入标签应用服务
     *
     * @param todoTagAppService 标签应用服务
     */
    public TodoTagController(TodoTagAppService todoTagAppService) {
        this.todoTagAppService = todoTagAppService;
    }

    /**
     * 查询所有标签
     *
     * <p>返回所有未删除的标签列表，按创建时间倒序排列。
     * 此接口不需要认证，所有用户均可访问。
     *
     * @return 标签列表
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<TodoTagDTO>> list() {
        List<TodoTag> tags = todoTagAppService.findAll();
        List<TodoTagDTO> dtoList = assembler.toDTOList(tags);
        return BaseResponse.success(dtoList);
    }

    /**
     * 创建标签
     *
     * <p>创建一个新的 TODO 标签。此接口需要认证（Bearer Token），
     * 仅授权用户可以调用。
     *
     * @param request 创建请求，包含 name（标签名称）
     * @return 创建结果，返回影响的行数
     * @see AuthCheck 认证注解
     */
    @AuthCheck
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> create(@Valid @RequestBody TodoTagCreateRequest request) {
        TodoTagCreateQuery query = assembler.toDomainQuery(request);
        int result = todoTagAppService.create(query);
        return BaseResponse.success(result);
    }
}
