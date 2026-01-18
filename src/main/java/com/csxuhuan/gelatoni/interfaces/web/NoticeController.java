package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.service.NoticeAppService;
import com.csxuhuan.gelatoni.domain.model.entity.Notice;
import com.csxuhuan.gelatoni.domain.query.NoticePageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;
import com.csxuhuan.gelatoni.interfaces.web.assembler.NoticePageAssembler;
import com.csxuhuan.gelatoni.interfaces.web.dto.NoticeDTO;
import com.csxuhuan.gelatoni.interfaces.web.request.NoticePageRequest;
import com.csxuhuan.gelatoni.interfaces.web.response.BasePageResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeAppService noticeAppService;
    private final NoticePageAssembler assembler = new NoticePageAssembler();

    public NoticeController(NoticeAppService noticeAppService) {
        this.noticeAppService = noticeAppService;
    }

    @PostMapping("/page")
    public BasePageResponse<NoticeDTO> page(@RequestBody NoticePageRequest request) {

        NoticePageQuery query = assembler.toDomainQuery(request);

        PageResult<Notice> pageResult = noticeAppService.pageQuery(query);

        return assembler.toResponse(pageResult);
    }
}
