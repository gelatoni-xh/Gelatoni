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

@RestController
@RequestMapping("/api/todo/tag")
public class TodoTagController {

    private final TodoTagAppService todoTagAppService;
    private final TodoTagAssembler assembler = new TodoTagAssembler();

    public TodoTagController(TodoTagAppService todoTagAppService) {
        this.todoTagAppService = todoTagAppService;
    }

    /**
     * 查询所有标签
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
     * @param request 创建请求
     * @return 创建结果
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
