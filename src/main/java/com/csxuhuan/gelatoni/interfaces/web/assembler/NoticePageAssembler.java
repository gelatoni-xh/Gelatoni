package com.csxuhuan.gelatoni.interfaces.web.assembler;

import com.csxuhuan.gelatoni.domain.model.entity.Notice;
import com.csxuhuan.gelatoni.domain.query.NoticePageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.PageData;
import com.csxuhuan.gelatoni.interfaces.web.dto.NoticeDTO;
import com.csxuhuan.gelatoni.interfaces.web.request.NoticePageRequest;

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
     * 将领域层分页结果转换为前端分页数据响应
     *
     * @param pageResult 领域层分页结果
     * @return 前端分页数据响应
     */
    public PageData<NoticeDTO> toPageData(PageResult<Notice> pageResult) {
        return new PageData<>(
                pageResult.getRecords().stream().map(this::toDTO).collect(Collectors.toList()),
                pageResult.getTotal(),
                pageResult.getPageNo(),
                pageResult.getPageSize()
        );
    }


    /**
     * 将领域层实体转换为前端展示用 DTO
     *
     * @param notice 领域层实体
     * @return 前端展示用 DTO
     */
    private NoticeDTO toDTO(Notice notice) {
        NoticeDTO dto = new NoticeDTO();
        dto.setContent(notice.getContent());
        dto.setCreateTime(notice.getCreateTime());
        return dto;
    }
}

