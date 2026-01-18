package com.csxuhuan.gelatoni.interfaces.web.assembler;

import com.csxuhuan.gelatoni.domain.model.entity.Notice;
import com.csxuhuan.gelatoni.domain.query.NoticePageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;
import com.csxuhuan.gelatoni.interfaces.web.dto.NoticeDTO;
import com.csxuhuan.gelatoni.interfaces.web.request.NoticePageRequest;
import com.csxuhuan.gelatoni.interfaces.web.response.BasePageResponse;

import java.util.stream.Collectors;

public class NoticePageAssembler {

    /**
     * 将前端分页请求转换为领域查询对象
     */
    public NoticePageQuery toDomainQuery(NoticePageRequest request) {
        return new NoticePageQuery(
                request.getPageNo(),
                request.getPageSize()
        );
    }

    /**
     * 将领域分页结果转换为前端响应
     */
    public BasePageResponse<NoticeDTO> toResponse(PageResult<Notice> pageResult) {
        return new BasePageResponse<>(
                pageResult.getRecords().stream().map(this::toDTO).collect(Collectors.toList()),
                pageResult.getTotal(),
                pageResult.getPageNo(),
                pageResult.getPageSize()
        );
    }


    private NoticeDTO toDTO(Notice notice) {
        NoticeDTO dto = new NoticeDTO();
        dto.setContent(notice.getContent());
        dto.setCreateTime(notice.getCreateTime());
        return dto;
    }
}

