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

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeAppService noticeAppService;
    private final NoticeAssembler assembler = new NoticeAssembler();

    public NoticeController(NoticeAppService noticeAppService) {
        this.noticeAppService = noticeAppService;
    }

    /**
     * 分页查询
     * @param request 请求参数
     * @return 分页结果
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
