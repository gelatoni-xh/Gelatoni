package com.csxuhuan.gelatoni.application.assembler;

import com.csxuhuan.gelatoni.domain.model.entity.Notice;
import com.csxuhuan.gelatoni.domain.query.NoticeCreateQuery;
import com.csxuhuan.gelatoni.domain.query.NoticePageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;
import com.csxuhuan.gelatoni.interfaces.web.common.PageData;
import com.csxuhuan.gelatoni.application.dto.NoticeDTO;
import com.csxuhuan.gelatoni.interfaces.web.request.NoticeCreateRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.NoticePageRequest;

import java.util.stream.Collectors;

/**
 * 公告 Assembler（装配器）
 *
 * <p>负责 interfaces 层与 domain 层之间的对象转换：
 * <ul>
 *     <li>Request → Domain Query：将前端请求转换为领域查询对象</li>
 *     <li>Domain Entity → DTO：将领域实体转换为前端展示对象</li>
 *     <li>PageResult → PageData：将领域分页结果转换为前端分页响应</li>
 * </ul>
 *
 * <p>设计说明：
 * Assembler 模式用于解耦各层之间的数据结构，避免领域对象直接暴露给前端，
 * 同时也避免前端数据结构影响领域模型的设计。
 *
 * @author csxuhuan
 */
public class NoticeAssembler {

    /**
     * 将前端分页请求转换为领域查询对象
     *
     * @param request 前端分页请求
     * @return 领域层分页查询对象
     */
    public NoticePageQuery toDomainQuery(NoticePageRequest request) {
        return new NoticePageQuery(
                request.getPageNo(),
                request.getPageSize()
        );
    }

    /**
     * 将前端创建公告请求转换为领域层创建对象
     *
     * @param request 前端分页请求
     * @return 领域层创建对象
     */
    public NoticeCreateQuery toDomainQuery(NoticeCreateRequest request) {
        return new NoticeCreateQuery(
                request.getTitle(),
                request.getContent()
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
        dto.setId(notice.getId());
        dto.setTitle(notice.getTitle());
        dto.setContent(notice.getContent());
        dto.setCreateTime(notice.getCreateTime());
        return dto;
    }
}

