package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.service.NoticeAppService;
import com.csxuhuan.gelatoni.domain.model.entity.Notice;
import com.csxuhuan.gelatoni.domain.query.NoticeCreateQuery;
import com.csxuhuan.gelatoni.domain.query.NoticePageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;
import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.interfaces.web.assembler.NoticeAssembler;
import com.csxuhuan.gelatoni.interfaces.web.dto.NoticeDTO;
import com.csxuhuan.gelatoni.interfaces.web.request.NoticeCreateRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.NoticePageRequest;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.PageData;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 公告控制器
 *
 * <p>提供公告相关的 RESTful API 接口，包括：
 * <ul>
 *     <li>分页查询公告列表</li>
 *     <li>创建新公告（需要认证）</li>
 * </ul>
 *
 * <p>接口路径前缀：/api/notice
 *
 * @author csxuhuan
 * @see NoticeAppService
 */
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeAppService noticeAppService;
    private final NoticeAssembler assembler = new NoticeAssembler();

    /**
     * 构造函数，注入公告应用服务
     *
     * @param noticeAppService 公告应用服务
     */
    public NoticeController(NoticeAppService noticeAppService) {
        this.noticeAppService = noticeAppService;
    }

    /**
     * 分页查询公告列表
     *
     * <p>支持分页参数配置，返回按创建时间倒序排列的公告列表。
     * 此接口不需要认证，所有用户均可访问。
     *
     * @param request 分页查询请求，包含 pageNo（页码）和 pageSize（每页大小）
     * @return 分页结果，包含公告列表和分页信息
     */
    @PostMapping(value = "/page",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<PageData<NoticeDTO>> page(@Valid @RequestBody NoticePageRequest request) {

        NoticePageQuery query = assembler.toDomainQuery(request);

        PageResult<Notice> pageResult = noticeAppService.pageQuery(query);

        PageData<NoticeDTO> pageData = assembler.toPageData(pageResult);

        return BaseResponse.success(pageData);
    }

    /**
     * 新增公告
     *
     * <p>创建一条新的公告记录。此接口需要认证（Bearer Token），
     * 仅授权用户可以调用。
     *
     * @param request 创建公告请求，包含 title（标题）和 content（内容）
     * @return 新创建的公告 ID
     * @see AuthCheck 认证注解
     */
    @AuthCheck
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> create(@Valid @RequestBody NoticeCreateRequest request) {
        NoticeCreateQuery query = assembler.toDomainQuery(request);

        int id = noticeAppService.create(query);

        return BaseResponse.success(id);
    }
}
